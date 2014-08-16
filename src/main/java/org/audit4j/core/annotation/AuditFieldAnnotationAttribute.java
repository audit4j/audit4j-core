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

package org.audit4j.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.DeIndentifyUtil;
import org.audit4j.core.dto.Field;

/**
 * The Class AuditFieldAnnotationAttribute.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class AuditFieldAnnotationAttribute {

	/**
	 * Gets the all params.
	 * 
	 * @param method
	 *            the method
	 * @param arg1
	 *            the arg1
	 * @return the all params
	 * @deprecated
	 */
	@Deprecated
	public Map<String, String> getAllParams(final Method method, final Object[] arg1) {

		final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		final Map<String, String> paramMap = new HashMap<String, String>();

		int i = 0;
		String paramName = null;
		String paramValue = null;
		for (final Annotation[] annotations : parameterAnnotations) {
			final Object object = arg1[i++];
			paramValue = object.toString();
			for (final Annotation annotation : annotations) {
				if (annotation instanceof AuditField) {
					final AuditField field = (AuditField) annotation;
					paramName = field.field();
				} else if (annotation instanceof DeIdentify) {
					final DeIdentify deidentify = (DeIdentify) annotation;
					paramValue = DeIndentifyUtil.deidentify(paramValue, deidentify.left(), deidentify.right(),
							deidentify.fromLeft(), deidentify.fromRight());
				}
			}
			if (null == paramName) {
				paramName = "arg" + (i - 1);
			}
			paramMap.put(paramName, paramValue);
			paramName = null;
			paramValue = null;
		}
		return paramMap;

	}

	/**
	 * Gets the all params.
	 * 
	 * @param method
	 *            the method
	 * @param arg1
	 *            the arg1
	 * @return the all params
	 */
	public List<Field> getAllFields(final Method method, final Object[] arg1) {

		final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		List<Field> actionItems = new ArrayList<Field>();

		int i = 0;
		String paramName = null;
		String paramValue = null;
		for (final Annotation[] annotations : parameterAnnotations) {
			final Object object = arg1[i++];
			paramValue = object.toString();
			for (final Annotation annotation : annotations) {
				if (annotation instanceof AuditField) {
					final AuditField field = (AuditField) annotation;
					paramName = field.field();
				} else if (annotation instanceof DeIdentify) {
					final DeIdentify deidentify = (DeIdentify) annotation;
					paramValue = DeIndentifyUtil.deidentify(paramValue, deidentify.left(), deidentify.right(),
							deidentify.fromLeft(), deidentify.fromRight());
				}
			}
			if (null == paramName) {
				paramName = "arg" + (i - 1);
			}
			Field actionItem = new Field();
			actionItem.setName(paramName);
			actionItem.setValue(paramValue);

			paramName = null;
			paramValue = null;
		}
		return actionItems;

	}

	/**
	 * Gets the marked params.
	 * 
	 * @param method
	 *            the method
	 * @param arg1
	 *            the arg1
	 * @return the marked params
	 * @deprecated Gets the marked params.
	 */
	@Deprecated
	public Map<String, String> getMarkedParams(final Method method, final Object[] arg1) {
		final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		final Map<String, String> paramMap = new HashMap<String, String>();

		int i = 0;
		String paramName = null;
		String paramValue = null;
		for (final Annotation[] annotations : parameterAnnotations) {
			final Object object = arg1[i++];
			paramValue = object.toString();
			for (final Annotation annotation : annotations) {
				if (annotation instanceof AuditField) {
					final AuditField field = (AuditField) annotation;
					paramName = field.field();
				} else if (annotation instanceof DeIdentify) {
					final DeIdentify deidentify = (DeIdentify) annotation;
					paramValue = DeIndentifyUtil.deidentify(paramValue, deidentify.left(), deidentify.right(),
							deidentify.fromLeft(), deidentify.fromRight());
				}
			}
			if (paramName != null) {
				paramMap.put(paramName, paramValue);
			}
			paramName = null;
			paramValue = null;
		}
		return paramMap;
	}

	/**
	 * Gets the marked action items.
	 * 
	 * @param method
	 *            the method
	 * @param arg1
	 *            the arg1
	 * @return the marked action items
	 */
	public List<Field> getMarkedFields(final Method method, final Object[] arg1) {
		final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		final List<Field> markedActionItems = new ArrayList<Field>();

		int i = 0;
		String paramName = null;
		String paramValue = null;
		for (final Annotation[] annotations : parameterAnnotations) {
			final Object object = arg1[i++];
			paramValue = object.toString();
			for (final Annotation annotation : annotations) {
				if (annotation instanceof AuditField) {
					final AuditField field = (AuditField) annotation;
					paramName = field.field();
				} else if (annotation instanceof DeIdentify) {
					final DeIdentify deidentify = (DeIdentify) annotation;
					paramValue = DeIndentifyUtil.deidentify(paramValue, deidentify.left(), deidentify.right(),
							deidentify.fromLeft(), deidentify.fromRight());
				}
			}
			if (paramName != null) {
				Field field = new Field();
				field.setName(paramName);
				field.setValue(paramValue);
			}
			paramName = null;
			paramValue = null;
		}
		return markedActionItems;
	}

}
