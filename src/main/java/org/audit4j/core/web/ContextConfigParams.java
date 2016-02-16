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

package org.audit4j.core.web;

/**
 * The Class ContextConfigParams.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.1
 */
class ContextConfigParams {

    /** The Constant PARAM_HANDLERS. */
    static final String PARAM_HANDLERS = "handlers";
    
    /** The Constant PARAM_LAYOUT. */
    static final String PARAM_LAYOUT = "layout";
    
    /** The Constant PARAM_FILTERS. */
    static final String PARAM_FILTERS = "filters";
    
    /** The Constant PARAM_OPTIONS. */
    static final String PARAM_OPTIONS = "options";
    
    /** The Constant PARAM_META_DATA. */
    static final String PARAM_META_DATA = "metaData";
    
    /** The Constant PARAM_PROPERTIES. */
    static final String PARAM_PROPERTIES = "properties";
    /**
     * private constructor to avoid instantiation of this class
     */
    private ContextConfigParams(){
    	
    }
}
