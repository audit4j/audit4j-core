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

package org.audit4j.core.dto;

import java.io.Serializable;

/**
 * The Class Field.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class Field implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5444966344948636042L;

	/** The name. */
	private String name;

	/** The value. */
	private String value;

	/** The type. */
	private String type;

	/**
	 * Instantiates a new element.
	 */
	public Field(){}
	
	/**
	 * Instantiates a new element.
	 *
	 * @param name the name
	 * @param value the value
	 * @param type the type
	 */
	public Field(String name, String value, String type){
		this.name=name;
		this.value=value;
		this.type=type;
	}
	
	/**
	 * Instantiates a new element.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public Field(String name, String value){
		this.name=name;
		this.value=value;
		this.type=value.getClass().getName();
	}
	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
}
