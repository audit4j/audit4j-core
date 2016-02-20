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

package org.audit4j.core.jmx;


/**
 * The Interface ServerAdminMBean.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public interface ServerAdminMBean {

    /**
     * Gets the startup time.
     *
     * @return the startup time
     */
    Long getStartupTime();

    /**
     * Checks if is alive.
     *
     * @return true, if is alive
     */
    boolean isAlive();

    /**
     * Gets the server status.
     *
     * @return the server status
     */
    String getServerStatus();

    /**
     * Start.
     */
    void start();

    /**
     * Stop.
     */
    void stop();

    /**
     * Restart.
     */
    void restart();

    /**
     * Disable.
     */
    void disable();

    /**
     * Enable.
     */
    void enable();
}
