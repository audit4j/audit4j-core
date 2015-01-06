package org.audit4j.core.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.junit.Before;
import org.junit.Test;

public class MethodAnnotationTest {

	@Before
	public void setup() {

	}

	@Test
	public void testAnnotation_selection_all() {
		AuditManager manager = AuditManager.getInstance();
		Method annoMethod = null;
		try {
			annoMethod = MethodAnnotationClass.class.getMethod("testAnnotation_selection_all", Integer.class, String.class,
					MethodAnnotationClass.class, Object.class, String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(annoMethod);
		Object[] args = new Object[5];
		args[0] = 1;
		args[1] = "1";
		args[2] = new MethodAnnotationClass();
		args[3] = new Object();
		args[4] = "1";

		manager.audit(MethodAnnotationClass.class, annoMethod, args);
	}

	@Test
	public void testAnnotation_selection_marked() {
		AuditManager manager = AuditManager.getInstance();
		Method annoMethod = null;
		try {
			annoMethod = MethodAnnotationClass.class.getMethod("testAnnotation_selection_marked", Integer.class,
					String.class, MethodAnnotationClass.class, Object.class, String.class);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(annoMethod);
		Object[] args = new Object[5];
		args[0] = 1;
		args[1] = "1";
		args[2] = new MethodAnnotationClass();
		args[3] = new Object();
		args[4] = "1";

		manager.audit(MethodAnnotationClass.class, annoMethod, args);
	}
}
