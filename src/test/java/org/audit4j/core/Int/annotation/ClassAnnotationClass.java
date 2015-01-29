package org.audit4j.core.Int.annotation;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.audit4j.core.annotation.IgnoreAudit;

@Audit
public class ClassAnnotationClass {

	
	public void testClassAnnotation_selection_all(String a) {

	}
	
	@IgnoreAudit
	public void testClassAnnotation_Ignore(String a) {

	}

	public void testClassAnnotation_selection_marked(@AuditField(field = "a") String a) {
	}
}
