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

package org.audit4j.core.dto;

import java.lang.reflect.Method;

/**
 * The Class AsyncAuditDto.
 * 
 * @author Janith Bandara
 */
public class AnnotationAuditEvent extends AuditBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2830800466963787273L;

	/** The clazz. */
	private Class clazz;

	/** The method. */
	Method method;

	/** The args. */
	Object[] args;

	/**
	 * Gets the clazz.
	 * 
	 * @return the clazz
	 */
	public Class getClazz() {
		return clazz;
	}

	/**
	 * Sets the clazz.
	 * 
	 * @param clazz
	 *            the new clazz
	 */
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets the method.
	 * 
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 * 
	 * @param method
	 *            the new method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	/**
	 * Gets the args.
	 * 
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * Sets the args.
	 * 
	 * @param args
	 *            the new args
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

}
