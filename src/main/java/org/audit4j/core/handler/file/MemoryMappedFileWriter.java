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
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import org.audit4j.core.CoreConstants;

/**
 * The Class MemoryMappedFileWriter.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class MemoryMappedFileWriter extends AuditFileWriter implements Serializable {

	/** The path. */
	private final String path;
	
	/** The out. */
	MappedByteBuffer out;

	/**
	 * Instantiates a new memory mapped file writer.
	 *
	 * @param path the path
	 */
	public MemoryMappedFileWriter(String path) {
		this.path = path;
	}

	/* (non-Javadoc)
	 * @see org.audit4j.core.handler.file.AuditFileWriter#init()
	 */
	@Override
	public void init() {
		int count = 10485760;
		RandomAccessFile randomAccessFile;
		String realPath = FileHandlerUtil.generateOutputFilePath(path);

		try {
			if (FileHandlerUtil.isFileAlreadyExists(realPath)) {
				randomAccessFile = new RandomAccessFile(realPath, CoreConstants.READ_WRITE);
			} else {
				randomAccessFile = new RandomAccessFile(new File(realPath), CoreConstants.READ_WRITE);
			}
			// move the cursor to the end of the file
			randomAccessFile.seek(randomAccessFile.length());

			 out = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, count);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see org.audit4j.core.handler.file.AuditFileWriter#write(java.lang.String)
	 */
	@Override
	public AuditFileWriter write(String event) {

		String str2 = event + CoreConstants.NEW_LINE;
		out.put(str2.getBytes(Charset.forName("UTF-8")));
		return this;
	}

	/* (non-Javadoc)
	 * @see org.audit4j.core.handler.file.AuditFileWriter#stop()
	 */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
