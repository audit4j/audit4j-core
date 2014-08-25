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

package org.audit4j.core;

import java.lang.reflect.Method;

import org.audit4j.core.annotation.AuditAnnotationAttributes;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.io.AnnotationAuditOutputStream;
import org.audit4j.core.io.AsyncAuditOutputStream;
import org.audit4j.core.io.AuditEventOutputStream;
import org.audit4j.core.io.AuditOutputStream;
import org.audit4j.core.io.AuditProcessOutputStream;

/**
 * The AuditManager. This class is used to submit audit events as well as annotations.
 * This is the only audit submition end point of the Audit4j. 
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public final class AuditManager {

	/**
	 * Instantiates a new audit manager.
	 */
	private AuditManager() {
	}

	/** The audit manager. */
	private static AuditManager auditManager;
	
	/** The context. */
	private static Context context;
	
	/** The stream. */
	private static AuditOutputStream stream;

	/** The annotation stream. */
	private static AnnotationAuditOutputStream annotationStream;

	
	/**
	 * Initializing context and streams.
	 * 
	 * @since 2.0.0
	 */
	private static void init(){
		context = new Context();
		context.init();
		AsyncAuditOutputStream asyncStream =  new AsyncAuditOutputStream(new AuditProcessOutputStream(context));
		stream = new AuditEventOutputStream(asyncStream);
		annotationStream = new AnnotationAuditOutputStream(stream);
	}
	
	/**
	 * Audit.
	 * 
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean audit(AuditEvent event) {
		stream.write(event);
		return true;
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
		final AuditAnnotationAttributes auditAttributes = new AuditAnnotationAttributes();
		if (auditAttributes.hasAnnotation(clazz) || auditAttributes.hasAnnotation(method)) {
			annotationStream.write(clazz, method, args);
		}
		return true;
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
				init();
			}
		}
		return auditManager;
	}
}
