package org.audit4j.core.annotation;

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
