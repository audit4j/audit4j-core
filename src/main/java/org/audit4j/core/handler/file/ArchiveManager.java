package org.audit4j.core.handler.file;

import it.sauronsoftware.cron4j.Scheduler;

public class ArchiveManager {

	private String datePattern;

	private String path;

	private String cronPattern;

	private ArchiveJob job;

	public void init() {
		Scheduler s = new Scheduler();
		System.out.println("Start Archive Manager");
		s.schedule(cronPattern, new Runnable() {
			@Override
			public void run() {
				System.out.println("Start Archive Manager");
				ArchiveJob archiveJob = job;
				archiveJob.setArchiveDateDiff(extractArchiveDateCount(datePattern));
				archiveJob.setPath(path);
				archiveJob.archive();
			}
		});
	}

	public Integer extractArchiveDateCount(String datePattern) {
		int dateCount = 0;
		String[] splits = datePattern.split("d|M|y");
		if (splits.length>0) {
			dateCount = dateCount + Integer.valueOf(splits[0]);
		}
		
		System.out.println(dateCount);
		
		if (splits.length>1) {
			dateCount = dateCount + (Integer.valueOf(splits[1]) * 30);
		}
		System.out.println(dateCount);
		
		if (splits.length>2) {
			dateCount = dateCount + (Integer.valueOf(splits[2]) * 365);
		}
		System.out.println(dateCount);
		return dateCount;
	}

	public void setArchiveDate(String datePattern) {
		this.datePattern = datePattern;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setCronPattern(String cronPattern) {
		this.cronPattern = cronPattern;
	}

	public void setJob(ArchiveJob job) {
		this.job = job;
	}
}
