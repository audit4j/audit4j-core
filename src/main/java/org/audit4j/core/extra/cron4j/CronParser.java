/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.audit4j.core.extra.cron4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

/**
 * <p>
 * A parser for crontab-like formatted files and streams.
 * </p>
 * <p>
 * If you want to schedule a list of tasks declared in a crontab-like file you
 * don't need the CronParser, since you can do it by adding the file to the
 * scheduler, with the {@link Scheduler#scheduleFile(File)} method.
 * </p>
 * <p>
 * Consider to use the CronParser if the {@link Scheduler#scheduleFile(File)}
 * method is not enough for you. In example, you may need to fetch the task list
 * from a remote source which is not representable as a {@link File} object (a
 * document on a remote server, a DBMS result set and so on). To solve the
 * problem you can implement your own {@link TaskCollector}, getting the
 * advantage of the CronParser to parse easily any crontab-like content.
 * </p>
 * <p>
 * You can parse a whole file/stream, but you can also parse a single line.
 * </p>
 * <p>
 * A line can be empty, can contain a comment or it can be a scheduling line.
 * </p>
 * <p>
 * A line containing no characters or a line with only space characters is
 * considered an empty line.
 * </p>
 * <p>
 * A line whose first non-space character is a number sign (#) is considered a
 * comment.
 * </p>
 * <p>
 * Empty lines and comment lines are ignored by the parser.
 * </p>
 * <p>
 * Any other kind of line is parsed as a scheduling line.
 * </p>
 * <p>
 * A valid scheduling line respects the following structure:
 * </p>
 * 
 * <pre>
 * scheduling-pattern [options] command [args]
 * </pre>
 * 
 * <ul>
 * <li><em>scheduling-pattern</em> is a valid scheduling pattern, according with
 * the definition given by the {@link SchedulingPattern} class.</li>
 * <li><em>options</em> is a list of optional informations used by cron4j to
 * prepare the task execution environment. See below for a more detailed
 * description.</li>
 * <li><em>command</em> is a system valid command, such an executable call.</li>
 * <li><em>args</em> is a list of optional arguments for the command.</li>
 * </ul>
 * <p>
 * After the scheduling pattern item, other tokens in each line are space
 * separated or delimited with double quotation marks (&quot;).
 * </p>
 * <p>
 * Double quotation marks delimited items can take advantage of the following
 * escape sequences:
 * </p>
 * <ul>
 * <li>\&quot; - quotation mark</li>
 * <li>\\ - back slash</li>
 * <li>\/ - slash</li>
 * <li>\b - back space</li>
 * <li>\f - form feed</li>
 * <li>\n - new line</li>
 * <li>\r - carriage return</li>
 * <li>\t - horizontal tab</li>
 * <li>\u005c\u0075<em>four-hex-digits</em> - the character at the given unicode
 * index</li>
 * </ul>
 * <p>
 * The <em>options</em> token collection can include one or more of the
 * following elements:
 * </p>
 * <ul>
 * <li>IN:<em>file-path</em> - Redirects the command standard input channel to
 * the specified file.</li>
 * <li>OUT:<em>file-path</em> - Redirects the command standard output channel to
 * the specified file.</li>
 * <li>ERR:<em>file-path</em> - Redirects the command standard error channel to
 * the specified file.</li>
 * <li>ENV:<em>name</em>=<em>value</em> - Defines an environment variable in the
 * scope of the command.</li>
 * <li>DIR:<em>directory-path</em> - Sets the path of the working directory for
 * the command. This feature is not supported if the executing JVM is less than
 * 1.3.</li>
 * </ul>
 * <p>
 * It is also possible to schedule the invocation of a method of a Java class in
 * the scope of the parser ClassLoader. The method has to be static and it must
 * accept an array of strings as its sole argument. To invoke a method of this
 * kind the syntax is:
 * </p>
 * 
 * <pre>
 * scheduling-pattern java:className#methodName [args]
 * </pre>
 * <p>
 * The <em>#methodName</em> part can be omitted: in this case the
 * <em>main(String[])</em> method will be assumed.
 * </p>
 * <p>
 * Please note that static methods are invoked within the scheduler same JVM,
 * without spawning any external process. Thus IN, OUT, ERR, ENV and DIR options
 * can't be applied.
 * </p>
 * <p>
 * Invalid scheduling lines are discarded without blocking the parsing
 * procedure, but an error message is printed in the application standard error
 * channel.
 * </p>
 * <p>
 * Valid examples:
 * </p>
 * 
 * <pre>
 * 0 5 * * * sol.exe
 * 0,30 * * * * OUT:C:\ping.txt ping 10.9.43.55
 * 0,30 4 * * * &quot;OUT:C:\Documents and Settings\Carlo\ping.txt&quot; ping 10.9.43.55
 * 0 3 * * * ENV:JAVA_HOME=C:\jdks\1.4.2_15 DIR:C:\myproject OUT:C:\myproject\build.log C:\myproject\build.bat &quot;Nightly Build&quot;
 * 0 4 * * * java:mypackage.MyClass#startApplication myOption1 myOption2
 * </pre>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
public class CronParser {

	/**
	 * Instantiation prohibited.
	 */
	private CronParser() {
	}

	/**
	 * <p>
	 * Builds a task list reading it from a file.
	 * </p>
	 * 
	 * <p>
	 * The file is treated as UTF-8. If your source file is not UTF-8 encoded
	 * establish by yourself a {@link Reader} using the right charset and pass
	 * it to the {@link CronParser#parse(Reader)} method.
	 * </p>
	 * 
	 * <p>
	 * Syntax and semantics errors in the source file are not blocking. Invalid
	 * lines are discarded, and they cause just a stack trace to be printed in
	 * the standard error channel as a notification.
	 * </p>
	 * 
	 * @param file
	 *            The file.
	 * @return The task table parsed from the file.
	 * @throws IOException
	 *             I/O error.
	 */
	public static TaskTable parse(File file) throws IOException {
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
			return parse(stream);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * <p>
	 * Builds a task list reading it from an URL.
	 * </p>
	 * 
	 * <p>
	 * Contents fetched from the URL are treated as UTF-8. If your source is not
	 * UTF-8 encoded establish by yourself a {@link Reader} using the right
	 * charset and pass it to the {@link CronParser#parse(Reader)} method.
	 * </p>
	 * 
	 * <p>
	 * Syntax and semantics errors in the retrieved document are not blocking.
	 * Invalid lines are discarded, and they cause just a stack trace to be
	 * printed in the standard error channel as a notification.
	 * </p>
	 * 
	 * @param url
	 *            The URL.
	 * @return The task table parsed from the contents fetched from the given
	 *         URL.
	 * @throws IOException
	 *             I/O error.
	 */
	public static TaskTable parse(URL url) throws IOException {
		InputStream stream = null;
		try {
			stream = url.openStream();
			return parse(stream);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (Throwable t) {
					;
				}
			}
		}
	}

	/**
	 * <p>
	 * Builds a task list reading it from an input stream.
	 * </p>
	 * 
	 * <p>
	 * The stream is treated as UTF-8. If your source is not UTF-8 encoded
	 * establish by yourself a {@link Reader} using the right charset and pass
	 * it to the {@link CronParser#parse(Reader)} method.
	 * </p>
	 * 
	 * <p>
	 * Syntax and semantics errors in the source stream are not blocking.
	 * Invalid lines are discarded, and they cause just a stack trace to be
	 * printed in the standard error channel as a notification.
	 * </p>
	 * 
	 * @param stream
	 *            The input stream.
	 * @return The task table parsed from the stream contents.
	 * @throws IOException
	 *             I/O error.
	 */
	public static TaskTable parse(InputStream stream) throws IOException {
		return parse(new InputStreamReader(stream, "UTF-8"));
	}

	/**
	 * <p>
	 * Builds a task list reading it from a reader.
	 * </p>
	 * 
	 * <p>
	 * Syntax and semantics errors in the source reader are not blocking.
	 * Invalid lines are discarded, and they cause just a stack trace to be
	 * printed in the standard error channel as a notification.
	 * </p>
	 * 
	 * @param reader
	 *            The reader.
	 * @return The task table parsed from the contents in the reader.
	 * @throws IOException
	 *             I/O error.
	 */
	public static TaskTable parse(Reader reader) throws IOException {
		TaskTable table = new TaskTable();
		BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				try {
					parseLine(table, line);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} finally {
			reader.close();
		}
		return table;
	}

	/**
	 * Parses a crontab-like line.
	 * 
	 * @param table
	 *            The table on which the parsed task will be stored, by
	 *            side-effect.
	 * @param line
	 *            The crontab-like line.
	 * @throws Exception
	 *             The supplied line doesn't represent a valid task line.
	 */
	public static void parseLine(TaskTable table, String line) throws Exception {
		line = line.trim();
		if (line.length() == 0 || line.charAt(0) == '#') {
			return;
		}
		// Detecting the pattern.
		int size = line.length();
		String pattern = null;
		for (int i = size; i >= 0; i--) {
			String aux = line.substring(0, i);
			if (SchedulingPattern.validate(aux)) {
				pattern = aux;
				break;
			}
		}
		if (pattern == null) {
			throw new Exception("Invalid cron line: " + line);
		}
		line = line.substring(pattern.length());
		size = line.length();
		// Splitting the line
		ArrayList splitted = new ArrayList();
		StringBuffer current = null;
		boolean quotes = false;
		for (int i = 0; i < size; i++) {
			char c = line.charAt(i);
			if (current == null) {
				if (c == '"') {
					current = new StringBuffer();
					quotes = true;
				} else if (c > ' ') {
					current = new StringBuffer();
					current.append(c);
					quotes = false;
				}
			} else {
				boolean closeCurrent;
				if (quotes) {
					closeCurrent = (c == '"');
				} else {
					closeCurrent = (c <= ' ');
				}
				if (closeCurrent) {
					if (current != null && current.length() > 0) {
						String str = current.toString();
						if (quotes) {
							str = escape(str);
						}
						splitted.add(str);
					}
					current = null;
				} else {
					current.append(c);
				}
			}
		}
		if (current != null && current.length() > 0) {
			String str = current.toString();
			if (quotes) {
				str = escape(str);
			}
			splitted.add(str);
			current = null;
		}
		// Analyzing
		size = splitted.size();
		int status = 0;
		// Status values:
		// 0 -> fetching environment variables, working directory and channels
		// 1 -> fetching the command and its arguments
		String dirString = null;
		File stdinFile = null;
		File stdoutFile = null;
		File stderrFile = null;
		ArrayList envsList = new ArrayList();
		String command = null;
		ArrayList argsList = new ArrayList();
		for (int i = 0; i < size; i++) {
			String tk = (String) splitted.get(i);
			// Check the local status.
			if (status == 0) {
				// Environment variables, working directory and channels
				if (tk.startsWith("ENV:")) {
					envsList.add(tk.substring(4));
					continue;
				} else if (tk.startsWith("DIR:")) {
					dirString = tk.substring(4);
					continue;
				} else if (tk.startsWith("IN:")) {
					stdinFile = new File(tk.substring(3));
					continue;
				} else if (tk.startsWith("OUT:")) {
					stdoutFile = new File(tk.substring(4));
					continue;
				} else if (tk.startsWith("ERR:")) {
					stderrFile = new File(tk.substring(4));
					continue;
				} else {
					status = 1;
				}
			}
			if (status == 1) {
				// Command or argument?
				if (command == null) {
					command = tk;
				} else {
					argsList.add(tk);
				}
			}
		}
		// Task preparing.
		Task task;
		// Command evaluation.
		if (command == null) {
			// No command!
			throw new Exception("Invalid cron line: " + line);
		} else if (command.startsWith("java:")) {
			// Java inner-process.
			String className = command.substring(5);
			if (className.length() == 0) {
				throw new Exception("Invalid Java class name on line: " + line);
			}
			String methodName;
			int sep = className.indexOf('#');
			if (sep == -1) {
				methodName = "main";
			} else {
				methodName = className.substring(sep + 1);
				className = className.substring(0, sep);
				if (methodName.length() == 0) {
					throw new Exception("Invalid Java method name on line: "
							+ line);
				}
			}
			String[] args = new String[argsList.size()];
			for (int i = 0; i < argsList.size(); i++) {
				args[i] = (String) argsList.get(i);
			}
			task = new StaticMethodTask(className, methodName, args);
		} else {
			// External command.
			String[] cmdarray = new String[1 + argsList.size()];
			cmdarray[0] = command;
			for (int i = 0; i < argsList.size(); i++) {
				cmdarray[i + 1] = (String) argsList.get(i);
			}
			// Environments.
			String[] envs = null;
			size = envsList.size();
			if (size > 0) {
				envs = new String[size];
				for (int i = 0; i < size; i++) {
					envs[i] = (String) envsList.get(i);
				}
			}
			// Working directory.
			File dir = null;
			if (dirString != null) {
				dir = new File(dirString);
				if (!dir.exists() || !dir.isDirectory()) {
					throw new Exception(
							"Invalid cron working directory parameter at line: "
									+ line,
							new FileNotFoundException(dirString
									+ " doesn't exist or it is not a directory"));
				}
			}
			// Builds the task.
			ProcessTask process = new ProcessTask(cmdarray, envs, dir);
			// Channels.
			if (stdinFile != null) {
				process.setStdinFile(stdinFile);
			}
			if (stdoutFile != null) {
				process.setStdoutFile(stdoutFile);
			}
			if (stderrFile != null) {
				process.setStderrFile(stderrFile);
			}
			task = process;
		}
		// End.
		table.add(new SchedulingPattern(pattern), task);
	}

	/**
	 * Escapes special chars occurrences.
	 * 
	 * @param str
	 *            The input stream.
	 * @return The decoded output stream.
	 */
	private static String escape(String str) {
		int size = str.length();
		StringBuffer b = new StringBuffer();
		for (int i = 0; i < size; i++) {
			int skip = 0;
			char c = str.charAt(i);
			if (c == '\\') {
				if (i < size - 1) {
					char d = str.charAt(i + 1);
					if (d == '"') {
						b.append('"');
						skip = 2;
					} else if (d == '\\') {
						b.append('\\');
						skip = 2;
					} else if (d == '/') {
						b.append('/');
						skip = 2;
					} else if (d == 'b') {
						b.append('\b');
						skip = 2;
					} else if (d == 'f') {
						b.append('\f');
						skip = 2;
					} else if (d == 'n') {
						b.append('\n');
						skip = 2;
					} else if (d == 'r') {
						b.append('\r');
						skip = 2;
					} else if (d == 't') {
						b.append('\t');
						skip = 2;
					} else if (d == 'u') {
						if (i < size - 5) {
							String hex = str.substring(i + 2, i + 6);
							try {
								int code = Integer.parseInt(hex, 16);
								if (code >= 0) {
									b.append((char) code);
									skip = 6;
								}
							} catch (NumberFormatException e) {
								;
							}
						}
					}
				}
			}
			if (skip == 0) {
				b.append(c);
			} else {
				i += (skip - 1);
			}
		}
		return b.toString();
	}
}
