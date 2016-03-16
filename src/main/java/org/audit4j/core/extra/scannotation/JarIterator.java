/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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

package org.audit4j.core.extra.scannotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JarIterator implements StreamIterator {
    JarInputStream jar;
    JarEntry next;
    Filter filter;
    boolean initial = true;
    boolean closed = false;

    public JarIterator(File file, Filter filter) throws IOException {
        this(new FileInputStream(file), filter);
    }

    public JarIterator(InputStream is, Filter filter) throws IOException {
        this.filter = filter;
        jar = new JarInputStream(is);
    }

    private void setNext() {
        initial = true;
        try {
            if (next != null) {
                jar.closeEntry();
            }
            next = null;
            do {
                next = jar.getNextJarEntry();
            } while (next != null
                    && (next.isDirectory() || (filter == null || !filter.accepts(next.getName()))));
            if (next == null) {
                close();
            }
        } catch (IOException e) {
            throw new RuntimeException("failed to browse jar", e);
        }
    }

    @Override
    public InputStream next() {
        if (closed || (next == null && !initial)) {
            return null;
        }
        setNext();
        if (next == null) {
            return null;
        }
        return new InputStreamWrapper(jar);
    }

    @Override
    public void close() {
        try {
            closed = true;
            jar.close();
        } catch (IOException ignored) {

        }

    }
}
