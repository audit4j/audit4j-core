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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

import org.audit4j.core.CoreConstants;

/**
 * The Class ZeroCopyFileWriter.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public final class ZeroCopyFileWriter extends AuditFileWriter implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1982643461500366178L;

    /** The random access file. */
    RandomAccessFile randomAccessFile;

    /** The file channel. */
    FileChannel fileChannel;

    /** The path. */
    private final String path;

    /** To track last opened random access file **/
	String lastRealPath;

    /**
     * Instantiates a new zero copy file writer.
     * 
     * @param path
     *            the path
     */
    public ZeroCopyFileWriter(String path) {
        this.path = path;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.handler.file.AuditFileWriter#init()
     */
    @Override
    public void init() {
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.io.AuditOutputStream#write(org.audit4j.core.dto.AuditEvent
     * )
     */
    @Override
    public ZeroCopyFileWriter write(String event) {
//        String realPath = FileHandlerUtil.generateOutputFilePath(path);

        String realPath = FileHandlerUtil.generateOutputFilePath(
    			path, 
    			FileHandlerUtil.generateAuditFileName());

        try {
           /** Close last instance for random access file. **/
			if (randomAccessFile != null && !realPath.equals(lastRealPath)) {
				this.stop();
			}
			
			if (randomAccessFile == null) {
				lastRealPath = realPath;
				randomAccessFile = new RandomAccessFile(new File(realPath), CoreConstants.READ_WRITE);
			}
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fileChannel = randomAccessFile.getChannel();
        
        String str2 = event + CoreConstants.NEW_LINE;

        long numBytes = str2.getBytes().length;

        InputStream inputStream = new ByteArrayInputStream(str2.getBytes(Charset.forName("UTF-8")));

        try {

            // move the cursor to the end of the file
            randomAccessFile.seek(randomAccessFile.length());

            // obtain the a file channel from the RandomAccessFile
            try (ReadableByteChannel inputChannel = Channels.newChannel(inputStream);) {
                fileChannel.transferFrom(inputChannel, randomAccessFile.length(), numBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.io.AuditOutputStream#close()
     */
    /**
     * Close.
     */
    public void close() {
        try {
            randomAccessFile.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.handler.file.AuditFileWriter#stop()
     */
    @Override
    public void stop() {
        try {
            if (randomAccessFile != null) {
                randomAccessFile.close();
                fileChannel.close();
            }
            randomAccessFile = null;
            fileChannel = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
