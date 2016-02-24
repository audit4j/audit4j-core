package org.audit4j.core.Int.event.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.IAuditManager;
import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.Mock.TestSuperObjectMock;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClassAnnotationTest {

    @Before
    public void setup() {

    }

    @Test
    public void testAnnotation_selection_all() {
        IAuditManager manager = AuditManager.startWithConfiguration(Configuration.DEFAULT);
        Method annoMethod = null;
        try {
            annoMethod = ClassAnnotationMock.class.getMethod("testClassAnnotation_selection_all", String.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object[] args = new Object[5];
        args[0] = "123232323";

        manager.audit(new AnnotationAuditEvent(ClassAnnotationMock.class, annoMethod, args));
    }

    @Test
    public void testAnnotation_complex() {
        IAuditManager manager = AuditManager.startWithConfiguration(Configuration.DEFAULT);
        Method annoMethod = null;
        try {
            annoMethod = ClassAnnotationMock.class.getMethod("testClassAnnotation_Complex", String.class,
                    TestSuperObjectMock.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object[] args = new Object[5];
        args[0] = "123232323";
        args[1] = TestSuperObjectMock.create();

        manager.audit(new AnnotationAuditEvent(ClassAnnotationMock.class, annoMethod, args));
    }

    @Test
    public void testAnnotation_selection_marked_deidentify_from_right() {
    }

    @After
    public void after() {
        AuditManager.shutdown();
    }
}
