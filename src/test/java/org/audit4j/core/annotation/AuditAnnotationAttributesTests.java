package org.audit4j.core.annotation;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.Mock.MethodAnnotationMock;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.Before;
import org.junit.Test;

public class AuditAnnotationAttributesTests {

    AuditAnnotationAttributes attributes;
    StopWatch watch;

    @Before
    public void before() {
        attributes = new AuditAnnotationAttributes();
        watch = new StopWatch();
    }

    @Test
    public void hasAnnotation_Class() {
        attributes = new AuditAnnotationAttributes();
        watch.start("hasAnnotation_Class");
        boolean result = attributes.hasAnnotation(ClassAnnotationMock.class);
        watch.stop();
        Log.info(watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
        assertTrue(result);
    }

    @Test
    public void hasAnnotation_Method() {

        Method annoMethod = null;
        try {
            annoMethod = MethodAnnotationMock.class.getMethod("testAnnotation_selection_all", Integer.class,
                    String.class, MethodAnnotationMock.class, Object.class, String.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        attributes = new AuditAnnotationAttributes();
        watch.start("hasAnnotation_Method");
        boolean result = attributes.hasAnnotation(annoMethod);
        watch.stop();
        Log.info(watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
        assertTrue(result);
    }
}
