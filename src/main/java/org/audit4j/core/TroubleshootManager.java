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

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.util.Log;

/**
 * The Class TroubleshootManager.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public final class TroubleshootManager {

    /** The Constant MIN_PORT_NUMBER. */
    public static final int MIN_PORT_NUMBER = 1100;

    /** The Constant MAX_PORT_NUMBER. */
    public static final int MAX_PORT_NUMBER = 49151;

    /**
     * Troubleshoot event.
     * 
     * @param event
     *            the event
     */
    public static void troubleshootEvent(AuditEvent event) {
        if (event == null) {
            throw new TroubleshootException(
                    "Invalid Audit event type,\n Audit4j: Audit Event should not null, This event will not be logged by the Audit4j.");
        } else if (event.getActor() == null) {
            if (Context.getConfigContext().getMetaData() == null
                    || Context.getConfigContext().getMetaData().getClass().equals(DummyMetaData.class)) {
                event.setActor(CoreConstants.DEFAULT_ACTOR);
                Log.warn("If you are not parsing the actor to the AuditEvent, "
                        + "you should make a your own AuditMetaData implementation. "
                        + "otherwise actor will be hard coded as \"" + CoreConstants.DEFAULT_ACTOR
                        + "\" in the audit log. See " + ErrorGuide.NULL_ACTOR + " for further details.");

            } else {
                event.setActor(Context.getConfigContext().getMetaData().getActor());
            }
        }

        // TODO commented due to fix
        // else if (event.getOrigin() == null) {
        // throw new TroubleshootException(
        // "Invalid Audit event type,\n Audit4j: origin should not null, This event will not be logged by the Audit4j.");
        // }
    }

    /**
     * Troubleshoot configuration.
     * 
     * @param e
     *            the e
     */
    public static void troubleshootConfiguration(ConfigurationException e) {
        if (e.getId().equals("CONF_001")) {
            Log.warn("Initial confguration file not found. Creating a new configuration file - ",
                    CoreConstants.DEFAULT_CONFIG_FILE_NAME);
            try {
                ConfigUtil.generateConfigFromObject();
            } catch (ConfigurationException e1) {
                throw new InitializationException("Initialization Failed.! Unable to create new configuration file.");
            }
        } else if (e.getId().equals("CONF_002")) {
            Log.error("Configuration file currupted or invalid configuration. ",
                    ErrorGuide.getGuide(ErrorGuide.CONFIG_ERROR));
            throw new TroubleshootException("Configuration error", e);
        }
    }
}
