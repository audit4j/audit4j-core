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

import org.audit4j.core.AuditManager;
import org.audit4j.core.LifeCycleContext;
import org.audit4j.core.util.Log;

/**
 * The Class ServerAdminImpl.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class ServerAdmin implements ServerAdminMBean {

    /** The life cycle. */
    private final LifeCycleContext lifeCycle;

    /**
     * Instantiates a new server admin impl.
     */
    ServerAdmin() {
        this.lifeCycle = LifeCycleContext.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#isAlive()
     * 
     */
    @Override
    public boolean isAlive() {
        return lifeCycle.isAlive();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#getServerStatus()
     * 
     */
    @Override
    public String getServerStatus() {
        return LifeCycleContext.getInstance().getStatus().getName();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#getStartupTime()
     * 
     */
    @Override
    public Long getStartupTime() {
        return lifeCycle.getStartUpTime();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#start()
     * 
     */
    @Override
    public void start() {
        Log.warn("Invoking Administration Method Remotely: start()");
        AuditManager.getInstance();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#enable()
     * 
     */
    @Override
    public void enable() {
        Log.warn("Invoking Administration Method Remotely: enable()");
        AuditManager.enable();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#disable()
     * 
     */
    @Override
    public void disable() {
        Log.warn("Invoking Administration Method Remotely: disable()");
        AuditManager.disable();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#stop()
     * 
     */
    @Override
    public void stop() {
        Log.warn("Invoking Administration Method Remotely: stop()");
        AuditManager.shutdown();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.jmx.ServerAdminMBean#restart()
     * 
     */
    @Override
    public void restart() {
        Log.warn("Invoking Administration Method Remotely: restart()");
        AuditManager.shutdown();
        AuditManager.getInstance();
    }
}
