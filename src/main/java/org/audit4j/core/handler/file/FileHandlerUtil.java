/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
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

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.util.AuditUtil;
import org.audit4j.core.util.EnvUtil;

/**
 * The Class FileHandlerUtil.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public final class FileHandlerUtil {
	
	/**
	 * Instantiates a new file handler util.
	 */
	private FileHandlerUtil() {
	}

	/**
	 * Generate output file path.
	 *
	 * @param path the path
	 * @return the string
	 */
	@Deprecated
	public static String generateOutputFilePath(String path) {
		String tempPath = separatorsToSystem(path);
		tempPath = tempPath + File.separatorChar + generateAuditFileName();
		return tempPath;
	}
	
	/**
	 * Generate output file path.
	 *
	 * @param path the path
	 * @param fileName the file name
	 * @return the string
	 */
	public static String generateOutputFilePath(String path, String fileName) {
		String tempPath = separatorsToSystem(path);
		tempPath = tempPath + File.separatorChar + fileName;
		return tempPath;
	}

	/**
	 * Generate file name.
	 * 
	 * @return the string
	 */
	public static String generatePreviousPreviousFileName() {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-").append(AuditUtil.dateToString(new Date(), "yyyy-MM-dd"))
				.append(CoreConstants.AUDIT_EXTENTION);
		return name.toString();
	}

	/**
	 * Generate file name.
	 * 
	 * @return the string
	 */
	public static String generateAuditFileName() {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-").append(AuditUtil.dateToString(new Date(), "yyyy-MM-dd"))
				.append(CoreConstants.AUDIT_EXTENTION);
		return name.toString();
	}
	
	/**
	 * Generate file name.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String generateAuditArchiveFileName(Date date) {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Archive-").append(AuditUtil.dateToString(date, "yyyy-MM-dd"))
				.append(CoreConstants.AUDIT_ARCHIVE_EXTENTION);
		return name.toString();
	}

	
	/**
	 * Generate file name.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String generateAuditFileName(Date date) {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-").append(AuditUtil.dateToString(date, "yyyy-MM-dd"))
				.append(CoreConstants.AUDIT_EXTENTION);
		return name.toString();
	}
	
	/**
	 * Generate file name.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static String generateCommonFileName(Date date) {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-").append(AuditUtil.dateToString(date, "yyyy-MM-dd"));
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
		if (EnvUtil.isWindows()) {
			return separatorsToWindows(path);
		} else {
			return separatorsToUnix(path);
		}
	}

	/**
	 * Checks if is file already exists.
	 * 
	 * @param path
	 *            the path
	 * @return true, if is file already exists
	 */
	public static boolean isFileAlreadyExists(String path) {
		File file = new File(path);
		return file.exists();
	}

	/**
	 * Add days.
	 *
	 * @param date the date
	 * @param different the different
	 * @return the date
	 */
	public static Date addDate(final Date date, final Integer different) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, different);
		return cal.getTime();
	}

	/**
	 * Substract days.
	 *
	 * @param date the date
	 * @param different the different
	 * @return the date
	 */
	public static Date substractDate(final Date date, final Integer different) {

		return addDate(date, -different);
	}
}
