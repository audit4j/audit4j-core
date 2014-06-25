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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.annotation.AsyncAuditAnnotationAttributes;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AsyncAnnotationAuditMessage;
import org.audit4j.core.dto.AsyncAuditMessage;
import org.audit4j.core.dto.AsyncCallAuditDto;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.HandlerException;
import org.audit4j.core.exception.TroubleshootException;
import org.audit4j.core.exception.ValidationException;
import org.audit4j.core.handler.Handler;

/**
 * The Class AuditUtils.
 * 
 * @author Janith Bandara
 */
public class AuditManager {

	/**
	 * Instantiates a new audit manager.
	 */
	public AuditManager() {
	}

	/** The audit manager. */
	private static AuditManager auditManager;

	/** The conf. */
	private Configuration conf;

	/**
	 * Instantiates a new audit manager.
	 * 
	 * @param handlers
	 *            the handlers
	 * @param action
	 *            the action
	 */
	public AuditManager(final List<Handler> handlers, final String action) {
		this.setHandlers(handlers);
		this.setAction(action);
	}

	/** The handlers. */
	private List<Handler> handlers;

	/** The action. */
	private String action;

	/** The param map. */
	private Map<String, Object> paramMap;

	/** The param count. */
	private Integer paramCount = 0;

	/**
	 * Audit.
	 * 
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean audit(AuditEvent event) {
		try {
			ValidationManager.validateEvent(event);
			final AsyncAuditEngine engine = AsyncAuditEngine.getInstance();
			AsyncAuditMessage message = new AsyncAuditMessage();
			message.setEvent(event);
			message.setConf(Context.getConfig());
			engine.init();
			engine.send(message);
			return Boolean.TRUE;
		} catch (ValidationException e) {
			try {
				TroubleshootManager.troubleshootEvent(event);
				final AsyncAuditEngine engine = AsyncAuditEngine.getInstance();
				AsyncAuditMessage message = new AsyncAuditMessage();
				message.setEvent(event);
				message.setConf(Context.getConfig());
				engine.init();
				engine.send(message);
				return Boolean.TRUE;
			} catch (TroubleshootException e1) {
				return Boolean.FALSE;
			}
		}
	}

	/**
	 * Audit field.
	 * 
	 * @param name
	 *            the name
	 * @param param
	 *            the param
	 * @return the audit manager
	 */
	public AuditManager auditField(String name, Object param) {
		if (null == paramMap) {
			paramMap = new LinkedHashMap<String, Object>();
		}
		paramMap.put(name, param);
		return this;
	}

	/**
	 * Audit field.
	 * 
	 * @param param
	 *            the param
	 * @return the audit manager
	 */
	public AuditManager auditField(Object param) {
		if (null == paramMap) {
			paramMap = new LinkedHashMap<String, Object>();
		}
		paramMap.put("arg" + paramCount, param);
		paramCount++;
		return this;
	}

	/**
	 * Do final.
	 * 
	 * @return the boolean
	 */
	public Boolean doFinal() {
		return audit(handlers, action, paramMap);
	}

	/**
	 * Audit.
	 * 
	 * @param handlers
	 *            the handlers
	 * @param action
	 *            the action
	 * @param paramMap
	 *            the param map
	 * @return true, if successful
	 */
	public boolean audit(final List<Handler> handlers, final String action, final Map<String, Object> paramMap) {

		for (Handler handler : handlers) {
			// handler.setArguments(arguments);
			// handler.setMethod(method);
			handler.setQuery(AuditUtil.buildQuery(AuditUtil.transformMap(paramMap), action));
			// handler.setParameters(AuditUtil.transformMap(paramMap));
			try {
				handler.handle();
			} catch (HandlerException e) {
				Log.warn("Failed to submit audit event.");
			}
		}
		return Boolean.TRUE;
	}

	/**
	 * Audit with annotation.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param method
	 *            the method
	 * @param args
	 *            the args
	 * @return true, if successful
	 */
	public boolean audit(Class<?> clazz, Method method, Object[] args) {
		final AsyncAuditAnnotationAttributes asyncAttributes = new AsyncAuditAnnotationAttributes();
		if (asyncAttributes.hasAnnotation(clazz) || asyncAttributes.hasAnnotation(method)) {
			AnnotationAuditEvent asyncAuditDto = new AnnotationAuditEvent();
			asyncAuditDto.setClazz(clazz);
			asyncAuditDto.setMethod(method);
			asyncAuditDto.setArgs(args);

			AsyncAnnotationAuditMessage message = new AsyncAnnotationAuditMessage();
			message.setConf(Context.getConfig());
			message.setAsyncAuditDto(asyncAuditDto);
			final AsyncAuditEngine engine = AsyncAuditEngine.getInstance();
			engine.init();
			engine.send(asyncAuditDto);
		} else {
			SynchronousAnnotationProcessor syncProcessor = SynchronousAnnotationProcessor.getInstance();
			AnnotationAuditEvent syncAuditDto = new AnnotationAuditEvent();
			syncAuditDto.setClazz(clazz);
			syncAuditDto.setMethod(method);
			syncAuditDto.setArgs(args);
			syncProcessor.process(syncAuditDto);
		}
		return Boolean.TRUE;
	}

	/**
	 * Async audit.
	 * 
	 * @param handlers
	 *            the handlers
	 * @param action
	 *            the action
	 * @param paramMap
	 *            the param map
	 * @return true, if successful
	 */
	public static boolean asyncAudit(final List<Handler> handlers, final String action,
			final Map<String, Object> paramMap) {

		AsyncCallAuditDto asyncAuditDto = new AsyncCallAuditDto();
		asyncAuditDto.setHandlers(handlers);
		asyncAuditDto.setAction(action);
		asyncAuditDto.setParamMap(paramMap);

		final AsyncAuditEngine engine = AsyncAuditEngine.getInstance();
		engine.init();
		engine.send(asyncAuditDto);
		return Boolean.TRUE;
	}

	/**
	 * Gets the current param map.
	 * 
	 * @return the current param map
	 */
	public Map<String, String> getCurrentParamMap() {
		return AuditUtil.transformMap(paramMap);
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
	 * Report.
	 * 
	 * @param msg
	 *            the msg
	 * @param t
	 *            the t
	 */
	public void report(String msg, Throwable t) {
		AuditUtil.report(msg, t);
	}

	/**
	 * Report.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void report(String msg) {
		AuditUtil.report(msg);
	}

	/**
	 * Audit.
	 * 
	 * @param msg
	 *            the msg
	 */
	public void audit(String msg) {

	}

	/**
	 * Gets the single instance of AuditHelper.
	 * 
	 * @return single instance of AuditHelper
	 */
	public static AuditManager getInstance() {
		synchronized (AuditManager.class) {
			if (auditManager == null) {
				auditManager = new AuditManager();
			}
		}
		return auditManager;
	}
}
