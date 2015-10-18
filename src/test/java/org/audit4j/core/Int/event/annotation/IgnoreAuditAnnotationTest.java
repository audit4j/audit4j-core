package org.audit4j.core.Int.event.annotation;

import java.lang.reflect.Method;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IgnoreAuditAnnotationTest {

    @Before
    public void setup() {

    }

    @Test
    public void testAnnotation_selection_marked_deidentify_from_right() {
        AuditManager manager = AuditManager.getInstance();
        Method annoMethod = null;
        try {
            annoMethod = ClassAnnotationMock.class.getMethod("testClassAnnotation_Ignore", String.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object[] args = new Object[5];
        args[0] = "123232323";

        StopWatch watch = new StopWatch();
        watch.start("smoke");
        manager.audit(ClassAnnotationMock.class, annoMethod, args);
        watch.stop();
        Log.info(watch.getLastTaskTimeMillis());
    }

    @After
    public void after() {
        AuditManager.shutdown();
    }
}
