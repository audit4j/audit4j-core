package org.audit4j.core.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.junit.Before;
import org.junit.Test;

public class DeIdentifyAnnotationTest {

	@Before
	public void setup() {

	}
	
	@Test
	public void testAnnotation_selection_marked_deidentify() {
		AuditManager manager = AuditManager.getInstance();
		Method annoMethod = null;
		try {
			annoMethod = MethodAnnotationClass.class.getMethod("testAnnotation_selection_marked_deidentify", String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(annoMethod);
		Object[] args = new Object[5];
		args[0] = "123232323";

		manager.audit(MethodAnnotationClass.class, annoMethod, args);
	}
	
	
	@Test
	public void testAnnotation_selection_marked_deidentify_from_left() {
		AuditManager manager = AuditManager.getInstance();
		Method annoMethod = null;
		try {
			annoMethod = MethodAnnotationClass.class.getMethod("testAnnotation_selection_marked_deidentify_from_left", String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(annoMethod);
		Object[] args = new Object[5];
		args[0] = "123232323";

		manager.audit(MethodAnnotationClass.class, annoMethod, args);
	}
	
	@Test
	public void testAnnotation_selection_marked_deidentify_from_right() {
		AuditManager manager = AuditManager.getInstance();
		Method annoMethod = null;
		try {
			annoMethod = MethodAnnotationClass.class.getMethod("testAnnotation_selection_marked_deidentify_from_right", String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(annoMethod);
		Object[] args = new Object[5];
		args[0] = "123232323";

		manager.audit(MethodAnnotationClass.class, annoMethod, args);
	}
}
