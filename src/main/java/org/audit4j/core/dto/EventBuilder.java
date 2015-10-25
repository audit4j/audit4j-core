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

import java.util.Date;

/**
 * The Class EventBuilder.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.1
 */
public class EventBuilder {

	/** The event. */
	private AuditEvent event = null;
	
	/**
	 * Instantiates a new event builder.
	 */
	public EventBuilder(){
		event = new AuditEvent();
	}
	
	/**
	 * Adds the actor.
	 *
	 * @param actor the actor
	 * @return the event builder
	 */
	public EventBuilder addActor(String actor){
		event.setActor(actor);
		return this;
	}
	
	/**
	 * Adds the action.
	 *
	 * @param action the action
	 * @return the event builder
	 */
	public EventBuilder addAction(String action){
		event.setAction(action);
		return this;
	}
	
	/**
	 * Adds the origin.
	 *
	 * @param origin the origin
	 * @return the event builder
	 */
	public EventBuilder addOrigin(String origin){
		event.setOrigin(origin);
		return this;
	}
	
	/**
	 * Adds the timestamp.
	 *
	 * @param timestamp the timestamp
	 * @return the event builder
	 */
	public EventBuilder addTimestamp(Date timestamp){
		event.setTimestamp(timestamp);
		return this;
	}
	
	/**
	 * Adds the field.
	 *
	 * @param name the name
	 * @param value the value
	 * @return the event builder
	 */
	public EventBuilder addField(String name, Object value){
		event.addField(name, value);
		return this;
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param name the name
	 * @return the event builder
	 * 
	 * @since 2.4.0
	 */
	public EventBuilder addTag(String name){
	    event.setTag(name);
	    return this;
	}
	
	/**
	 * Builds the.
	 *
	 * @return the audit event
	 */
	public AuditEvent build(){
		return event;
	}
}
