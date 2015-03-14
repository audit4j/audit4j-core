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

import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.audit4j.core.Initializable;
import org.audit4j.core.exception.Audit4jRuntimeException;
import org.audit4j.core.util.AuditUtil;
import org.audit4j.core.util.annotation.Beeta;

/**
 * The Class ArchiveJob.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
@Beeta
public abstract class AbstractArchiveJob implements Initializable, Serializable {

    /** The archive date diff. */
    protected Integer archiveDateDiff;

    /** The path. */
    protected String path;

    /** The archive options. */
    protected String archiveOptions;

    /** The compression extention. */
    protected String compressionExtention;

    /**
     * Archive.
     */
    abstract void archive();

    /**
     * Gets the available files.
     *
     * @param logFileLocation the log file location
     * @param maxDate the max date
     * @return the available files
     */
    protected File[] getAvailableFiles(final String logFileLocation, final Date maxDate) {

        File dir = new File(logFileLocation);

        return dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                boolean extentionMatch = fileName.endsWith(compressionExtention);
                boolean dateMatch = maxDate.before(fileCreatedDate(fileName));
                return dateMatch && extentionMatch;
            }
        });

    }

    /**
     * File created date.
     *
     * @param fileName the file name
     * @return the date
     */
    private Date fileCreatedDate(String fileName) {
        String[] splittedWithoutExtention = fileName.split(".");
        String fileNameWithoutExtention = splittedWithoutExtention[0];
        String[] splittedWithoutPrefix = fileNameWithoutExtention.split("-");
        String fileNameDateInStr = splittedWithoutPrefix[1];
        try {
            return AuditUtil.stringTodate(fileNameDateInStr, "yyyy-MM-dd");
        } catch (ParseException e) {
            throw new Audit4jRuntimeException("Exception occured parsing the date.", e);
        }
    }

    /**
     * Sets the archive date diff.
     * 
     * @param archiveDateDiff
     *            the new archive date diff
     */
    public void setArchiveDateDiff(Integer archiveDateDiff) {
        this.archiveDateDiff = archiveDateDiff;
    }

    /**
     * Sets the path.
     * 
     * @param path
     *            the new path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Sets the archive options.
     * 
     * @param archiveOptions
     *            the new archive options
     */
    public void setArchiveOptions(String archiveOptions) {
        this.archiveOptions = archiveOptions;
    }

    /**
     * Sets the compression extention.
     *
     * @param compressionExtention the new compression extention
     */
    public void setCompressionExtention(String compressionExtention) {
        this.compressionExtention = compressionExtention;
    }

}
