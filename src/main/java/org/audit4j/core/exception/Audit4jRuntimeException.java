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

package org.audit4j.core.exception;

import org.audit4j.core.util.Log;

/**
 * The Class Audit4jRuntimeException.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jRuntimeException extends RuntimeException {

    /** asdas. */
    private static final long serialVersionUID = -7750687125705697432L;

    /**
     * Instantiates a new audit4j runtime exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public Audit4jRuntimeException(String message, Throwable cause) {
        super(message, cause);
        Log.error("Problem while running Audit4j: " + message, cause);
    }
}
