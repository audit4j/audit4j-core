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

import java.lang.reflect.Method;
import java.util.List;

import org.audit4j.core.dto.AuditBase;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Element;
import org.audit4j.core.handler.Handler;

/**
 * The Class AuditProcessor.
 * 
 * @param <T>
 *            the generic type
 * @author Janith Bandara
 */
public abstract class AuditProcessor<T extends AuditBase> {

	private Configuration conf;

	/**
	 * Process.
	 * 
	 * @param t
	 *            the t
	 */
	public abstract void process(T event);

	/**
	 * Builds the query.
	 * 
	 * @param actionItems
	 *            the action items
	 * @param action
	 *            the action
	 * @return the string
	 */
	@Deprecated
	protected String buildQuery(final List<Element> actionItems, String action) {
		if (actionItems != null && !actionItems.isEmpty()) {
			final StringBuilder buff = new StringBuilder();
			if (action != null) {
				buff.append(action).append(CoreConstants.ARROW);
			}
			for (Element actionItem : actionItems) {
				buff.append(actionItem.getName()).append(CoreConstants.COLON_CHAR).append(actionItem.getValue())
						.append(CoreConstants.COMMA_CHAR);
			}
			return buff.toString();
		} else {
			return "No data for selectred audit criteria";
		}
	}

	/**
	 * Execute handlers.
	 * 
	 * @param handlers
	 *            the handlers
	 * @param event
	 *            the event
	 * @param query
	 *            the query
	 */
	@Deprecated
	protected void executeHandlers(final List<Handler> handlers, AuditEvent event, final String query) {
		for (final Handler handler : handlers) {

			handler.setAuditEvent(event);
			handler.setQuery(query);
			handler.handle();
		}
	}

	/**
	 * Execute handlers.
	 * 
	 * @param handlers
	 *            the handlers
	 * @param event
	 *            the event
	 * @param query
	 *            the query
	 */
	protected void executeHandlers(AuditEvent event) {
		event.setActor(getConf().getMetaData().getActor());
		for (final Handler handler : getConf().getHandlers()) {
			handler.setAuditEvent(event);
			handler.setQuery(getConf().getLayout().format(event));
			handler.handle();
		}
	}

	/**
	 * Gets the actor.
	 * 
	 * @param handler
	 *            the handler
	 * @return the actor
	 */
	@Deprecated
	protected String getActor(final Handler handler) {
		return getConf().getMetaData().getActor();
	}

	/**
	 * Gets the action.
	 * 
	 * @param action
	 *            the action
	 * @param method
	 *            the method
	 * @return the action
	 */
	@Deprecated
	protected String getAction(String action, Method method) {
		if (action.equals(CoreConstants.ACTION.equals(action))) {
			return method.getName();
		}
		return action;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}
}
