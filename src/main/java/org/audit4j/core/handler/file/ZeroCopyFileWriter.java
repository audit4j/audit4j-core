/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core.handler.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;
import java.util.Date;

import org.audit4j.core.AuditUtil;
import org.audit4j.core.Configuration;
import org.audit4j.core.CoreConstants;
import org.audit4j.core.Layout;
import org.audit4j.core.TroubleshootManager;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.io.AuditOutputStream;

/**
 * The Class ZeroCopyFileWriter.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public final class ZeroCopyFileWriter implements AuditOutputStream {

	/** The layout. */
	private final Layout layout;

	/** The path. */
	private final String path;

	/**
	 * Instantiates a new zero copy file writer.
	 * 
	 * @param conf
	 *            the conf
	 */
	public ZeroCopyFileWriter(Configuration conf) {
		this.layout = (Layout) conf.getValue("layout");
		this.path = (String) conf.getValue("log.file.location");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
	 * )
	 */
	@Override
	public AuditOutputStream write(AuditEvent event) {
		String str2 = layout.format(event) + CoreConstants.NEW_LINE;
		RandomAccessFile randomAccessFile;
		String realPath = generateOutputFilePath();

		long numBytes = str2.getBytes().length;

		InputStream inputStream = new ByteArrayInputStream(str2.getBytes(Charset.forName("UTF-8")));

		try {
			if (TroubleshootManager.isFileAlreadyExists(realPath)) {
				randomAccessFile = new RandomAccessFile(realPath, CoreConstants.READ_WRITE);
			} else {
				randomAccessFile = new RandomAccessFile(new File(realPath), CoreConstants.READ_WRITE);
			}
			// move the cursor to the end of the file
			randomAccessFile.seek(randomAccessFile.length());

			// obtain the a file channel from the RandomAccessFile
			try (FileChannel fileChannel = randomAccessFile.getChannel();
					ReadableByteChannel inputChannel = Channels.newChannel(inputStream);

			) {
				fileChannel.transferFrom(inputChannel, randomAccessFile.length(), numBytes);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Generate output file path.
	 * 
	 * @return the string
	 */
	public String generateOutputFilePath() {
		String tempPath = separatorsToSystem(path);
		tempPath = tempPath + File.separatorChar + generateFileName() ;
		return tempPath;
	}
	
	/**
	 * Generate file name.
	 *
	 * @return the string
	 */
	private String generateFileName(){
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-")
		.append( AuditUtil.dateToString(new Date(), "yyyy-MM-dd"))
		.append(CoreConstants.AUDIT_EXTENTION);
		return name.toString();
	}
	
	

	/**
	 * Converts all separators to the Unix separator of forward slash.
	 * 
	 * @param path
	 *            the path to be changed, null ignored
	 * @return the updated path
	 */
	public static String separatorsToUnix(String path) {
		if (path == null || path.indexOf(CoreConstants.WINDOWS_SEPARATOR) == -1) {
			return path;
		}
		return path.replace(CoreConstants.WINDOWS_SEPARATOR, CoreConstants.UNIX_SEPARATOR);
	}

	/**
	 * Converts all separators to the Windows separator of backslash.
	 * 
	 * @param path
	 *            the path to be changed, null ignored
	 * @return the updated path
	 */
	public static String separatorsToWindows(String path) {
		if (path == null || path.indexOf(CoreConstants.UNIX_SEPARATOR) == -1) {
			return path;
		}
		return path.replace(CoreConstants.UNIX_SEPARATOR, CoreConstants.WINDOWS_SEPARATOR);
	}

	/**
	 * Converts all separators to the system separator.
	 * 
	 * @param path
	 *            the path to be changed, null ignored
	 * @return the updated path
	 */
	public static String separatorsToSystem(String path) {
		if (path == null) {
			return null;
		}
		if (TroubleshootManager.isWindows()) {
			return separatorsToWindows(path);
		} else {
			return separatorsToUnix(path);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.audit4j.core.io.AuditOutputStream#close()
	 */
	@Override
	public void close() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		return null;
	}
}
