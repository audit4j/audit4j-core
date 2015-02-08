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

package org.audit4j.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.command.impl.ScanAnnotatedCommand;
import org.audit4j.core.filter.AuditAnnotationFilter;
import org.audit4j.core.filter.AuditEventFilter;

/**
 * Registry for internal resource configurations.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public final class PreConfigurationContext {

    /** The Constant commands. */
    private static final Map<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();

    /** The Constant options. */
    private static final List<String> options = new ArrayList<String>();

    /** The Constant preFilters. */
    private static final List<AuditEventFilter> preFilters = new ArrayList<AuditEventFilter>();

    /** The Constant preAnnotationFilters. */
    private static final List<AuditAnnotationFilter> preAnnotationFilters = new ArrayList<AuditAnnotationFilter>();

    static {
        options.add("-scanAnnotated");
        commands.put("-scanAnnotated", new ScanAnnotatedCommand());
    }

    /**
     * Gets the command by option name.
     *
     * @param optionName the option name
     * @return the command by option name
     */
    public static AbstractCommand getCommandByOptionName(String optionName) {
        return commands.get(optionName);
    }

    /**
     * Gets the options.
     *
     * @return the options
     */
    public static List<String> getOptions() {
        return options;
    }

    /**
     * Gets the commands.
     *
     * @return the commands
     */
    public static Map<String, AbstractCommand> getCommands() {
        return commands;
    }

    /**
     * Gets the prefilters.
     *
     * @return the prefilters
     */
    public static List<AuditEventFilter> getPrefilters() {
        return preFilters;
    }

    /**
     * Adds the filter.
     *
     * @param filter the filter
     */
    public static void addFilter(AuditEventFilter filter) {
        preFilters.add(filter);
    }

    /**
     * Adds the annotation filter.
     *
     * @param filter the filter
     */
    public static void addAnnotationFilter(AuditAnnotationFilter filter) {
        preAnnotationFilters.add(filter);
    }

    /**
     * Gets the preannotationfilters.
     *
     * @return the preannotationfilters
     */
    public static List<AuditAnnotationFilter> getPreannotationfilters() {
        return preAnnotationFilters;
    }

    /**
     * Instantiates a new registry.
     */
    private PreConfigurationContext() {
        // Private Constructor
    }

}
