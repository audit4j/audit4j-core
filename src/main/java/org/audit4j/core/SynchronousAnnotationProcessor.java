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

import java.util.List;

import org.audit4j.core.annotation.AuditAnnotationAttributes;
import org.audit4j.core.annotation.AuditFieldAnnotationAttribute;
import org.audit4j.core.annotation.SelectionType;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;
import org.audit4j.core.handler.Handler;

/**
 * The Class SynchronousAnnotationProcessor.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
@Deprecated
public class SynchronousAnnotationProcessor extends AnnotationAuditProcessor<AnnotationAuditEvent> {

	/**
	 * Instantiates a new synchronous audit processor.
	 */
	private SynchronousAnnotationProcessor() {
	}

	/** The audit processor. */
	private static SynchronousAnnotationProcessor auditProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bi3.commons.audit.AuditProcessor#process(com.bi3.commons.audit.dto
	 * .AuditBaseDto)
	 */
	@Override
	public void process(AnnotationAuditEvent auditDto) {
		final AuditAnnotationAttributes attributes = new AuditAnnotationAttributes();
		final AuditFieldAnnotationAttribute fieldAttributes = new AuditFieldAnnotationAttribute();
		List<Handler> handlers = null;
		List<Field> fields = null;
		AuditEvent event = new AuditEvent();
		String action = "";

		if (attributes.hasAnnotation(auditDto.getClass())) {
			handlers = Context.getConfig().getHandlers();

			final SelectionType selection = attributes.getSelection(auditDto.getClass());
			if (selection.equals(SelectionType.ALL)) {
				fields = fieldAttributes.getAllFields(auditDto.getMethod(), auditDto.getArgs());
			} else if (selection.equals(SelectionType.MARKED)) {
				fields = fieldAttributes.getMarkedFields(auditDto.getMethod(), auditDto.getArgs());
			}
			action = attributes.getAction(auditDto.getMethod().getClass(), auditDto.getMethod());
		} else if (attributes.hasAnnotation(auditDto.getMethod())) {
			handlers = Context.getConfig().getHandlers();
			final SelectionType selection = attributes.getSelection(auditDto.getMethod());
			if (selection.equals(SelectionType.ALL)) {
				fields = fieldAttributes.getAllFields(auditDto.getMethod(), auditDto.getArgs());
			} else if (selection.equals(SelectionType.MARKED)) {
				fields = fieldAttributes.getMarkedFields(auditDto.getMethod(), auditDto.getArgs());
			}
			action = attributes.getAction(auditDto.getMethod());
		}

		final String query = super.buildQuery(fields, action);
		event.setAction(super.getAction(action, auditDto.getMethod()));
		event.setFields(fields);
		super.executeHandlers(handlers, event, query);
	}

	/**
	 * Gets the single instance of AuditHelper.
	 * 
	 * @return single instance of AuditHelper
	 */
	public static SynchronousAnnotationProcessor getInstance() {
		synchronized (SynchronousAnnotationProcessor.class) {
			if (auditProcessor == null) {
				auditProcessor = new SynchronousAnnotationProcessor();
			}
		}
		return auditProcessor;
	}
}
