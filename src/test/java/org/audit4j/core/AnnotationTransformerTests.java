package org.audit4j.core;

import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.Mock.MethodAnnotationMock;
import org.audit4j.core.dto.AnnotationAuditEvent;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.Test;

public class AnnotationTransformerTests {

    @Test
    public void testtransformToEvent_IgnoreAudit() {
       
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

        
        AnnotationAuditEvent annoEvent = new AnnotationAuditEvent();
        annoEvent.setClazz(ClassAnnotationMock.class);
        annoEvent.setMethod(annoMethod);
        annoEvent.setArgs(args);
        
        StopWatch watch = new StopWatch();
        watch.start("testtransformToEvent");
        AnnotationTransformer transformer = new DefaultAnnotationTransformer();
        AuditEvent event = transformer.transformToEvent(annoEvent);
        assertNull(event);
        watch.stop();
        Log.info(watch.getLastTaskTimeMillis());

    }
    
    @Test
    public void testtransformToEvent_Deidentify() {
       
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

        
        AnnotationAuditEvent annoEvent = new AnnotationAuditEvent();
        annoEvent.setClazz(MethodAnnotationMock.class);
        annoEvent.setMethod(annoMethod);
        annoEvent.setArgs(args);
        
        StopWatch watch = new StopWatch();
        watch.start("testtransformToEvent");
        AnnotationTransformer transformer = new DefaultAnnotationTransformer();
        AuditEvent event = transformer.transformToEvent(annoEvent);
        
        watch.stop();
        Log.info(watch.getLastTaskTimeMillis());

    }
}
