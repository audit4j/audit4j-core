package org.audit4j.core.Int.event.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.Mock.MethodAnnotationMock;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MethodAnnotationTest {

    @Before
    public void setup() {
        AuditManager.startWithConfiguration(Configuration.DEFAULT);
    }

    @Test
    public void testAnnotation_selection_all() {
       // AuditManager manager = AuditManager.getInstance();
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
        System.out.println(annoMethod);
        Object[] args = new Object[5];
        args[0] = 1;
        args[1] = "1";
        args[2] = new MethodAnnotationMock();
        args[3] = new Object();
        args[4] = "1";

        StopWatch watch = new StopWatch();
        watch.start("smoke");
        System.out.println("test1");
        AuditManager.getInstance().audit(MethodAnnotationMock.class, annoMethod, args);
        watch.stop();
        Log.info(watch.getLastTaskTimeMillis());
    }

    @Test
    public void testAnnotation_selection_marked() {
        Method annoMethod = null;
        try {
            annoMethod = MethodAnnotationMock.class.getMethod("testAnnotation_selection_marked", Integer.class,
                    String.class, MethodAnnotationMock.class, Object.class, String.class);
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
        args[2] = new MethodAnnotationMock();
        args[3] = new Object();
        args[4] = "1";
        System.out.println("test2");
        AuditManager.getInstance().audit(MethodAnnotationMock.class, annoMethod, args);
    }

    @After
    public void after() {
        AuditManager.shutdown();
    }
}
