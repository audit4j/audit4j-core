package org.audit4j.core.handler.file;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import org.audit4j.core.AuditUtil;
import org.audit4j.core.CoreConstants;
import org.audit4j.core.TroubleshootManager;

public final class FileHandlerUtil {
	private FileHandlerUtil() {
	}

	/**
	 * Generate output file path.
	 * 
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
	 * @return the string
	 */
	public static String generateAuditArchiveFileName(Date date) {
		StringBuffer name = new StringBuffer();
		name.append("Audit_Log-").append(AuditUtil.dateToString(date, "yyyy-MM-dd"))
				.append(".auditarchive");
		return name.toString();
	}

	
	/**
	 * Generate file name.
	 * 
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
		if (TroubleshootManager.isWindows()) {
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
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add days.
	 * 
	 * @param date
	 *            the date
	 * @param different
	 *            the different
	 * @param unit
	 *            the unit
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
	 * @param date
	 *            the date
	 * @param different
	 *            the different
	 * @param unit
	 *            the unit
	 * @return the date
	 */
	public static Date substractDate(final Date date, final Integer different) {

		return addDate(date, -different);
	}
}
