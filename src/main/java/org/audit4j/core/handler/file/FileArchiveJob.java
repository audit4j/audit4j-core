package org.audit4j.core.handler.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.audit4j.core.Log;

public class FileArchiveJob extends ArchiveJob {

	byte[] buffer = new byte[1024];

	@Override
	public void archive() {
		Log.info("Starting archiving...");
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

}
