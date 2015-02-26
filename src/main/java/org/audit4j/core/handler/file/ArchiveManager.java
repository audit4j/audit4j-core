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

import org.audit4j.core.Initializable;
import org.audit4j.core.extra.cron4j.Scheduler;
import org.audit4j.core.util.Log;

/**
 * The Class ArchiveManager.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ArchiveManager implements Initializable{

	/** The date pattern. */
	private String datePattern;

	/** The path. */
	private String path;

	/** The cron pattern. */
	private String cronPattern;

	/** The job. */
	private ArchiveJob archiveJob;

	/** The scheculer. */
	private Scheduler scheculer;
	/**
	 * Inits the.
	 */
	@Override
    public void init() {
	    Log.info("Starting Archive Manager");
		scheculer = new Scheduler();
		scheculer.schedule(cronPattern, new Runnable() {
			@Override
			public void run() {
				archiveJob.setArchiveDateDiff(extractArchiveDateCount(datePattern));
				archiveJob.setPath(path);
				archiveJob.archive();
			}
		});
	}

	/**
	 * Extract archive date count.
	 *
	 * @param datePattern the date pattern
	 * @return the integer
	 */
	public Integer extractArchiveDateCount(String datePattern) {
		int dateCount = 0;
		String[] splits = datePattern.split("d|M|y");
		if (splits.length>0) {
			dateCount = dateCount + Integer.valueOf(splits[0]);
		}
		
		if (splits.length>1) {
			dateCount = dateCount + (Integer.valueOf(splits[1]) * 30);
		}
		
		if (splits.length>2) {
			dateCount = dateCount + (Integer.valueOf(splits[2]) * 365);
		}
		return dateCount;
	}

	/**
	 * Sets the archive date.
	 *
	 * @param datePattern the new archive date
	 */
	public void setArchiveDate(String datePattern) {
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

	/**
	 * Sets the job.
	 *
	 * @param job the new job
	 */
	public void setJob(ArchiveJob job) {
		this.archiveJob = job;
	}

    /* (non-Javadoc)
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        scheculer.stop();
    }
}
