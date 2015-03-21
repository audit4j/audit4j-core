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

package org.audit4j.core.handler.file.archive;

import java.util.List;

import org.audit4j.core.Initializable;
import org.audit4j.core.schedule.CronTrigger;
import org.audit4j.core.schedule.Schedulers;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.annotation.Beeta;

/**
 * The Class ArchiveManager.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Beeta
public class ArchiveManager implements Initializable {

    /** The jobs. */
    private List<AbstractArchiveJob> jobs;

    /** The archive env. */
    private final ArchiveEnv archiveEnv;

    /**
     * Instantiates a new archive manager.
     * 
     * @param archiveEnv
     *            the archive env
     */
    public ArchiveManager(ArchiveEnv archiveEnv) {
        this.archiveEnv = archiveEnv;
    }

    /**
     * Inits the.
     */
    @Override
    public void init() {
        Log.info("Starting Archive Manager");
        if (archiveEnv.getArchiveMethod().equals(ArchiveMethod.SIMPLE)) {
            executeArchive();
        } else if (archiveEnv.getArchiveMethod().equals(ArchiveMethod.SCHEDULED)) {
            jobs = new JobFactory().getJobs(archiveEnv);
            Schedulers.taskRegistry().registor(new CronTrigger(archiveEnv.getCronPattern()), new Runnable() {
                @Override
                public void run() {
                    executeArchive();
                }
            });
        }
    }

    /**
     * Execute archive.
     */
    private void executeArchive() {
        for (AbstractArchiveJob archiveJob : jobs) {
            archiveJob.setArchiveDateDiff(extractArchiveDateCount(archiveEnv.getDatePattern()));
            archiveJob.setPath(archiveEnv.getDirPath());
            archiveJob.setCompressionExtention(archiveEnv.getCompression().getExtention());
            archiveJob.archive();
        }
    }

    /**
     * Extract archive date count.
     * 
     * @param datePattern
     *            the date pattern
     * @return the integer
     */
    public Integer extractArchiveDateCount(String datePattern) {
        int dateCount = 0;
        String[] splits = datePattern.split("d|M|y");
        if (splits.length > 0) {
            dateCount = dateCount + Integer.valueOf(splits[0]);
        }
        if (splits.length > 1) {
            dateCount = dateCount + (Integer.valueOf(splits[1]) * 30);
        }
        if (splits.length > 2) {
            dateCount = dateCount + (Integer.valueOf(splits[2]) * 365);
        }
        return dateCount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.Initializable#stop()
     */
    /**
     * Stop.
     */
    @Override
    public void stop() {
    }
}
