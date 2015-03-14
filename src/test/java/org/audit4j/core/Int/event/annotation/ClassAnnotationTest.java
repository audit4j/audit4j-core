package org.audit4j.core.Int.event.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Mock.ClassAnnotationMock;
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
        AuditManager manager = AuditManager.getInstance();
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
    public void testAnnotation_selection_marked_deidentify_from_right() {
    }

    @After
    public void after() {
        AuditManager.getInstance().shutdown();
    }
}
