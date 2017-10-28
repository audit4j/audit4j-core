package org.audit4j.core.Int.event.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.Mock.MethodAnnotationMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DeIdentifyAnnotationTest {

    @Before
    public void setup() {
        AuditManager.startWithConfiguration(Configuration.DEFAULT);
    }

    @Test
    public void testAnnotation_selection_marked_deidentify() {
        IAuditManager manager = AuditManager.getInstance();
        Method annoMethod = null;
        try {
            annoMethod = MethodAnnotationMock.class.getMethod("testAnnotation_selection_marked_deidentify",
                    String.class);
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

        manager.audit(MethodAnnotationMock.class, annoMethod, args);
    }

    @Test
    public void testAnnotation_selection_marked_deidentify_from_left() {
        IAuditManager manager = AuditManager.getInstance();
        Method annoMethod = null;
        try {
            annoMethod = MethodAnnotationMock.class.getMethod("testAnnotation_selection_marked_deidentify_from_left",
                    String.class);
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

        manager.audit(MethodAnnotationMock.class, annoMethod, args);
    }

    @Test
    public void testAnnotation_selection_marked_deidentify_from_right() {
        IAuditManager manager = AuditManager.getInstance();
        Method annoMethod = null;
        try {
            annoMethod = MethodAnnotationMock.class.getMethod("testAnnotation_selection_marked_deidentify_from_right",
                    String.class);
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

        manager.audit(MethodAnnotationMock.class, annoMethod, args);
    }

    @After
    public void after() {
        AuditManager.shutdown();
    }
}
