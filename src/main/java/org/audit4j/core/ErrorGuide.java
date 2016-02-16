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
 * The Class ErrorURL.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public final class ErrorGuide {

	/** The Constant ERROR_URL. */
	public static final String ERROR_URL = CoreConstants.SITE_URL + "/errors#";
	
	/** The Constant NULL_ACTOR. */
	public static final String NULL_ACTOR = ERROR_URL + "metadataimpl";
	
	/** The Constant CONFIG_ERROR. */
	public static final String CONFIG_ERROR = ERROR_URL + "configfile";
	
	/** The Constant CONFIG_NOT_EXISTS. */
	public static final String CONFIG_NOT_EXISTS = ERROR_URL + "configfileNotExists";
	
    /** The Constant JAVA_VERSION_ERROR. */
    public static final String JAVA_VERSION_ERROR = ERROR_URL + "javaVersion";
    
    /** The Constant INVALID_OPTION. */
    public static final String INVALID_COMMAND = ERROR_URL + "invalidCommand";
    
    /** The Constant NO_HANDLERS. */
    public static final String EMPTY_HANDLERS = ERROR_URL + "noHandlers";
    
    /** The Constant HANDLER_ERROR. */
    public static final String HANDLER_ERROR = ERROR_URL + "handlerError";
    
    /** The Constant LAYOUT_ERROR. */
    public static final String EMPTY_LAYOUT = ERROR_URL + "noLayout";
    
    /** The Constant LAYOUT_ERROR. */
    public static final String LAYOUT_ERROR = ERROR_URL + "layoutError";
    
    /** The Constant LAYOUT_ERROR. */
    public static final String OPTION_ERROR = ERROR_URL + "optionError";
   
    /**
     * private constructor to avoid instantiation of this class
     */
    private ErrorGuide(){
    	
    }
 
    /**
     * Gets the guide.
     *
     * @param code the code
     * @return the guide
     */
    public static String getGuide(String code){
        StringBuilder builder = new StringBuilder();
        builder.append(" see ").append(code).append(" for further details.");
        return builder.toString();
    }
}
