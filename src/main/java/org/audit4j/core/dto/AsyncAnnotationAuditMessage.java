/*
 * Copyright 2014 Janith Bandara, This source is a part of 
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

import org.audit4j.core.Configuration;

/**
 * The Class AsyncAnnotationAuditMessage.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AsyncAnnotationAuditMessage implements Serializable{

	/**
	 * asdas
	 */
	private static final long serialVersionUID = 1L;

	/** The conf. */
	private Configuration conf;
	
	/** The async audit dto. */
	private AnnotationAuditEvent asyncAuditDto;
	
	/**
	 * Gets the conf.
	 *
	 * @return the conf
	 */
	public Configuration getConf() {
		return conf;
	}
	
	/**
	 * Sets the conf.
	 *
	 * @param conf the new conf
	 */
	public void setConf(Configuration conf) {
		this.conf = conf;
	}
	
	/**
	 * Gets the async audit dto.
	 *
	 * @return the async audit dto
	 */
	public AnnotationAuditEvent getAsyncAuditDto() {
		return asyncAuditDto;
	}
	
	/**
	 * Sets the async audit dto.
	 *
	 * @param asyncAuditDto the new async audit dto
	 */
	public void setAsyncAuditDto(AnnotationAuditEvent asyncAuditDto) {
		this.asyncAuditDto = asyncAuditDto;
	}
}
