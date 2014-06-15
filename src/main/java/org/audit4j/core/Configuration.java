/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.audit4j.core.handler.Handler;

/**
 * The Class Configuration.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Configuration implements Serializable {

	/** asdas. */
	private static final long serialVersionUID = -3370288956459623002L;

	/** The released. */
	Date released;

	/** The version. */
	String version;

	/** The layout. */
	private Layout layout;

	/** The handlers. */
	private List<Handler> handlers = new ArrayList<>();

	/** The meta data. */
	private MetaData metaData;

	/** The properties. */
	Map<String, String> properties;

	/**
	 * Gets the released.
	 * 
	 * @return the released
	 */
	public Date getReleased() {
		return released;
	}

	/**
	 * Sets the released.
	 * 
	 * @param released
	 *            the new released
	 */
	public void setReleased(Date released) {
		this.released = released;
	}

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Gets the meta data.
	 * 
	 * @return the meta data
	 */
	public MetaData getMetaData() {
		return metaData;
	}

	/**
	 * Sets the meta data.
	 * 
	 * @param metaData
	 *            the new meta data
	 */
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	/**
	 * Gets the handlers.
	 * 
	 * @return the handlers
	 */
	public List<Handler> getHandlers() {
		return handlers;
	}

	/**
	 * Sets the handlers.
	 * 
	 * @param handlers
	 *            the new handlers
	 */
	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	/**
	 * Gets the layout.
	 * 
	 * @return the layout
	 */
	public Layout getLayout() {
		return layout;
	}

	/**
	 * Sets the layout.
	 * 
	 * @param layout
	 *            the new layout
	 */
	public void setLayout(Layout layout) {
		this.layout = layout;
	}

	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 * 
	 * @param properties
	 *            the properties
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
}
