package org.audit4j.core.Int;

import java.lang.reflect.Method;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.Mock.NullAnnotationMock;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;

public class IntTestBase extends Audit4jTestBase {
    StopWatch watch;

    protected AnnotationAuditEvent getSampleAnnotationEvent() {
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
        Object[] args = new Object[1];
        args[0] = "123232323";
        return new AnnotationAuditEvent(ClassAnnotationMock.class, annoMethod, args);
    }

    protected AnnotationAuditEvent getSampleNullAnnotationEvent() {
        Method annoMethod = null;
        try {
            annoMethod = NullAnnotationMock.class.getMethod("testNullAnnotation_Method", String.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Object[] args = new Object[1];
        args[0] = "123232323";
        return new AnnotationAuditEvent(NullAnnotationMock.class, annoMethod, args);
    }

    protected void watchStart(String name) {
        watch = new StopWatch();
        watch.start(name);
    }

    protected void watchStop() {
        watch.stop();
        Log.info(watch.getLastTaskName() +"=" + watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
    }
}
