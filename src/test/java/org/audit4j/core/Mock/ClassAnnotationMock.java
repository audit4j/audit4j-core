package org.audit4j.core.Mock;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.audit4j.core.annotation.IgnoreAudit;

@Audit
public class ClassAnnotationMock {

	
	public void testClassAnnotation_selection_all(String a) {

	}
	
	@IgnoreAudit
	public void testClassAnnotation_Ignore(@AuditField(field = "a") String a) {

	}

	public void testClassAnnotation_selection_marked(@AuditField(field = "a") String a) {
	}
	
    public void testClassAnnotation_Complex(@AuditField(field = "a") String a, @AuditField(field = "object") TestSuperObjectMock object) {

    }
}
