package org.audit4j.core.Int;

import java.io.File;
import java.lang.reflect.Method;

import org.audit4j.core.Audit4jTestBase;
import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.Mock.NullAnnotationMock;
import org.audit4j.core.dto.AnnotationAuditEvent;

/**
 * The Class IntTestBase.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class IntTestBase extends Audit4jTestBase {
    
    /**
     * Gets the sample annotation event.
     *
     * @return the sample annotation event
     */
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

    /**
     * Gets the sample null annotation event.
     *
     * @return the sample null annotation event
     */
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

    /**
     * Safetly flush file.
     *
     * @param filePath the file path
     */
    protected void safetlyFlushFile(String filePath) {
        safetlyFlushFile(new File(filePath));
    }
    
    /**
     * Safetly flush file.
     *
     * @param file the file
     */
    protected void safetlyFlushFile(File file) {
        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("Delete operation is failed.");
        }
    }
    
    /**
     * Before.
     */
    @Override
    protected void before(){
        super.before();
    }
    
    /**
     * After.
     */
    @Override
    protected void after(){
        super.after();
    }
}
