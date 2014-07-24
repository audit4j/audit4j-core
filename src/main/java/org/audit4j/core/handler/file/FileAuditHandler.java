package org.audit4j.core.handler.file;

import java.io.FilePermission;
import java.security.AccessControlException;
import java.security.AccessController;

import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.handler.Handler;

/**
 * The Class FileAuditHandler.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class FileAuditHandler extends Handler {

	/** asdas. */
	private static final long serialVersionUID = 1L;

	/** The writer. */
	AuditFileWriter writer;

	private String archive;
	
	/** The date pattern. */
	private String datePattern;

	/** The path. */
	private String path;

	/** The cron pattern. */
	private String cronPattern;

	/** The job. */
	private ArchiveJob job;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.audit4j.core.handler.Handler#init()
	 */
	@Override
	public void init() throws InitializationException {
		writer = new ZeroCopyFileWriter(getProperty("log.file.location"));
		
		if (null != archive && "true".equals(archive)) {
			ArchiveManager manager = new ArchiveManager();
			manager.setArchiveDate(datePattern);
			manager.setCronPattern(cronPattern);
			manager.setPath(getProperty("log.file.location"));
			manager.setJob(job);
			manager.init();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.audit4j.core.handler.Handler#handle()
	 */
	@Override
	public void handle() {
		writer.write(getQuery());
	}

	/**
	 * Checks for disk access.
	 * 
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	static boolean hasDiskAccess(final String path) {
		try {
			AccessController.checkPermission(new FilePermission(path, "read,write"));
			return true;
		} catch (AccessControlException e) {
			return false;
		}
	}
	
	

	public void setArchive(String archive) {
		this.archive = archive;
	}

	/**
	 * Sets the date pattern.
	 *
	 * @param datePattern the new date pattern
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Sets the cron pattern.
	 *
	 * @param cronPattern the new cron pattern
	 */
	public void setCronPattern(String cronPattern) {
		this.cronPattern = cronPattern;
	}
}
