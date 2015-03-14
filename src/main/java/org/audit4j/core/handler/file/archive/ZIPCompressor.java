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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.audit4j.core.exception.Audit4jRuntimeException;
import org.audit4j.core.util.annotation.Beeta;

/**
 * The Class ZIPCompressor.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Beeta
public class ZIPCompressor implements Compressor {

    /** The buffer. */
    byte[] buffer = new byte[1024];

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.audit4j.core.handler.file.archive.Compressor#compress(java.io.InputStream
     * , java.io.OutputStream, java.lang.String)
     */
    @Override
    public void compress(final InputStream in, final OutputStream out, final String targetName) {
        ZipOutputStream zos = new ZipOutputStream(out);
        ZipEntry ze = new ZipEntry(targetName);
        try {
            zos.putNextEntry(ze);
            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            throw new Audit4jRuntimeException("Could not compress the given data", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new Audit4jRuntimeException("Could not compress the given data", e);
            } finally {
                try {
                    zos.closeEntry();
                } catch (IOException e) {
                    throw new Audit4jRuntimeException("Could not compress the given data", e);
                } finally {
                    try {
                        zos.close();
                    } catch (IOException e) {
                        throw new Audit4jRuntimeException("Could not compress the given data", e);
                    }
                }

            }

        }

    }

}
