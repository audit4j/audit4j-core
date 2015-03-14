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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.handler.file.FileHandlerUtil;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.annotation.Beeta;

/**
 * The Class FileArchiveJob.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Beeta
public class FileArchiveJob extends AbstractArchiveJob {

	/** The buffer. */
	byte[] buffer = new byte[1024];

	/* (non-Javadoc)
	 * @see org.audit4j.core.handler.file.ArchiveJob#archive()
	 */
	@Override
	public void archive() {
		Log.info("Starting local archiving...");
		
		String fileName = FileHandlerUtil.generateAuditFileName(FileHandlerUtil.substractDate(new Date(),
				archiveDateDiff));
		String archiveFileName = FileHandlerUtil.generateAuditArchiveFileName(FileHandlerUtil.substractDate(new Date(),
				archiveDateDiff));

		String filePath = FileHandlerUtil.generateOutputFilePath(path, fileName);
		String archivePath = FileHandlerUtil.generateOutputFilePath(path, archiveFileName);

		if (FileHandlerUtil.isFileAlreadyExists(filePath)) {

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(archivePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ZipOutputStream zos = new ZipOutputStream(fos);
			ZipEntry ze = new ZipEntry(fileName);
			try {
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(filePath);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
				zos.closeEntry();
				zos.close();
				System.out.println("Done");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

    @Override
    public void init() throws InitializationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }
}
