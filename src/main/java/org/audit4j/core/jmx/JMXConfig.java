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
 * The Class JMXConfig.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class JMXConfig {

    /** The context name. */
    private String contextName = "default";

    /**
     * Gets the context name.
     *
     * @return the context name
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Sets the context name.
     *
     * @param contextName the new context name
     */
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
}
