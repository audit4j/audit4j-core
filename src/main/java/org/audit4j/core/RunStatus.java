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

/**
 * The life cycle states of Audit4j.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.2.0
 */
public enum RunStatus {

    /** Audit4j in this state is inactive and ready to initialize. */
    READY("READY"),

    /** Audit4j in this state is operational. */
    RUNNING("RUNNING"),

    /**
     * Audit4j stopped and ready to initialize. All the resources loaded in to
     * the memory cleared.
     */
    STOPPED("STOPPED"),

    /**
     * Manually disabled the audit4j but all resources are loaded and ready to
     * run. Will not audit the events.
     */
    DISABLED("DISABLED"),

    /**
     * Audit4j in this state has encountered a problem and may not be
     * operational.
     */
    TERMINATED("TERMINATED");
    
    /** The name. */
    private String name;
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Instantiates a new run status.
     *
     * @param name the name
     */
    RunStatus(String name){
        this.name = name;
    }
}
