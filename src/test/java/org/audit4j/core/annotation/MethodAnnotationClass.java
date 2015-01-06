package org.audit4j.core.annotation;


public class MethodAnnotationClass {

	@Audit
	public void testAnnotation_selection_all(Integer a, String b, MethodAnnotationClass c, Object d, String e) {

	}

	@Audit(selection = SelectionType.MARKED)
	public void testAnnotation_selection_marked(@AuditField(field = "a") Integer a, @AuditField(field = "b") String b,
			@AuditField(field = "c") MethodAnnotationClass c, Object d,
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
