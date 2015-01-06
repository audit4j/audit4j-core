package org.audit4j.core.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The Class AuditAnnotationAttributes.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.2.0
 */
public class IgnoreAuditAnnotationAttributes {

	/**
	 * Checks for annotation.
	 * 
	 * @param method
	 *            the method
	 * @return the boolean
	 */
	public Boolean hasAnnotation(final Method method) {
		final Annotation[] annotations = method.getAnnotations();
		for (final Annotation annotation : annotations) {
			if (annotation instanceof IgnoreAudit) {
				return true;
			}
		}
		return false;
	}
}
