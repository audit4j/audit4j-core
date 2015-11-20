package org.audit4j.core.Mock;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;
import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.SelectionType;


public class MethodAnnotationMock {

	@Audit
	public void testAnnotation_selection_all(Integer a, String b, MethodAnnotationMock c, Object d, String e) {

	}
	
	@Audit
    public void testAnnotation_Complex(@AuditField(field = "a") String a, @AuditField(field = "object") TestSuperObjectMock object) {

    }
    
	@Audit(selection = SelectionType.MARKED)
	public void testAnnotation_selection_marked(@AuditField(field = "a") Integer a, @AuditField(field = "b") String b,
			@AuditField(field = "c") MethodAnnotationMock c, Object d,
			@AuditField(field = "e") String e) {
	}
	
	@Audit(selection = SelectionType.MARKED)
	public void testAnnotation_selection_marked_deidentify(@DeIdentify @AuditField(field = "b") String b) {
	}
	
	@Audit(selection = SelectionType.MARKED)
	public void testAnnotation_selection_marked_deidentify_from_left(@DeIdentify(fromLeft=2) @AuditField(field = "b") String b) {
	}
	
	@Audit(selection = SelectionType.MARKED)
	public void testAnnotation_selection_marked_deidentify_from_right(@DeIdentify(fromRight=2) @AuditField(field = "b") String b) {
	}
}
