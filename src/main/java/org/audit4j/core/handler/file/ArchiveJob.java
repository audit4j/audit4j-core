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

import java.io.Serializable;

import org.audit4j.core.Initializable;

/**
 * The Class ArchiveJob.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public abstract class ArchiveJob implements Initializable, Serializable{
	
	/** The archive date diff. */
	protected Integer archiveDateDiff;

	/** The path. */
	protected String path;

	/**
	 * Archive.
	 */
	abstract void archive();

	/**
	 * Sets the archive date diff.
	 *
	 * @param archiveDateDiff the new archive date diff
	 */
	public void setArchiveDateDiff(Integer archiveDateDiff) {
		this.archiveDateDiff = archiveDateDiff;
	}

	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
