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

import java.util.List;
import java.util.Map;

import org.audit4j.core.handler.Handler;


/**
 * The Class AsyncCallAuditDto.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
@Deprecated
public class AsyncCallAuditDto extends AuditBase {

	/** asdas. */
	private static final long serialVersionUID = -3935010172421814679L;

	/** The handlers. */
	private List<Handler> handlers;

	/** The action. */
	private String action;

	/** The param map. */
	private Map<String, Object> paramMap;

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
	 * @param handlers the new handlers
	 */
	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the param map.
	 *
	 * @return the param map
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * Sets the param map.
	 *
	 * @param paramMap the param map
	 */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

}
