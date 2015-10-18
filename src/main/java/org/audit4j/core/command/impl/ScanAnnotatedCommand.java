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

package org.audit4j.core.command.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import org.audit4j.core.PreConfigurationContext;
import org.audit4j.core.annotation.Audit;
import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.extra.scannotation.AnnotationDB;
import org.audit4j.core.filter.impl.ScanAnnotatedFilter;

/**
 * Scan Annotated Command.
 * 
 * Scan annotations in initialization time and store the information. These
 * information will use later to speedup the event processing.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public class ScanAnnotatedCommand extends AbstractCommand {

    /** The db. */
    AnnotationDB db;

    /* (non-Javadoc)
     * @see org.audit4j.core.command.AbstractCommand#getCommand()
     */
    @Override
    public String getCommand() {
        return "-scanAnnotated";
    }

    /* (non-Javadoc)
     * @see org.audit4j.core.command.AbstractCommand#getCommandDescription()
     */
    @Override
    public String getCommandDescription() {
        return "Scan annotations in initialization time and store the information. "
                + "These information will use later to speedup the event processing";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.Initializable#init()
     */
    @Override
    public void init() {
        String packageName = getCommands().get(getCommand());

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        String path = packageName.replace('.', '/');
        URL url = null;
        try {
            final Enumeration<URL> urls = classLoader.getResources(path);

            while (urls.hasMoreElements()) {
                url = urls.nextElement();
                int index = url.toExternalForm().lastIndexOf(path);

                url = new URL(url.toExternalForm().substring(0, index));
            }
        } catch (IOException e) {
            throw new InitializationException("", e);
        }

        db = new AnnotationDB();
        db.setScanClassAnnotations(true);
        db.setScanMethodAnnotations(true);
        try {
            db.scanArchives(url);
        } catch (IOException e) {
            throw new InitializationException("", e);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.command.AbstractCommand#execute()
     */
    @Override
    public void execute() {
        Set<String> annotated = db.getAnnotationIndex().get(Audit.class.getName());
        ScanAnnotatedFilter filter = new ScanAnnotatedFilter();
        for (String annotaionClass : annotated) {
            try {
                Class<?> clazz = Class.forName(annotaionClass);
                filter.addClass(clazz);
            } catch (ClassNotFoundException e) {
                throw new InitializationException("", e);
            }
        }
        PreConfigurationContext.addAnnotationFilter(filter);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.audit4j.core.Initializable#stop()
     */
    @Override
    public void stop() {
        db = null;
    }
}
