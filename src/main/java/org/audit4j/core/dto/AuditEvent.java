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

import java.util.List;

/**
 * The Class AuditEvent.
 * 
 * @author Janith Bandara
 */
public class AuditEvent extends AuditBase {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7009763987501353992L;
	
	/** The actor. */
	private String actor;

	/** The origin. */
	private String origin;

	/** The action. */
	private String action;

	/** The action item. */
	private List<Element> eventElements;

	/**
	 * Gets the actor.
	 * 
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * Sets the actor.
	 * 
	 * @param actor
	 *            the new actor
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

	/**
	 * Gets the origin.
	 * 
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Sets the origin.
	 * 
	 * @param origin
	 *            the new origin
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
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
	 * @param action
	 *            the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Gets the event elements.
	 *
	 * @return the event elements
	 */
	public List<Element> getEventElements() {
		return eventElements;
	}

	/**
	 * Sets the event elements.
	 *
	 * @param eventElements the new event elements
	 */
	public void setEventElements(List<Element> eventElements) {
		this.eventElements = eventElements;
	}
}
