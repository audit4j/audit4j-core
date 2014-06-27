package org.audit4j.core.handler.file;

public abstract class ArchiveJob {
	protected Integer archiveDateDiff;

	protected String path;

	abstract void archive();

	public void setArchiveDateDiff(Integer archiveDateDiff) {
		this.archiveDateDiff = archiveDateDiff;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
