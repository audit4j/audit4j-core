package org.audit4j.core.smoke;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;
import org.audit4j.core.Mock.ClassAnnotationMock;
import org.junit.Test;

public class CommandSmokeTest {
    @Test
    public void testCodeGenCommand(){
        Configuration config = Configuration.DEFAULT;
        config.setCommands("-codeGen=true");
        AuditManager.startWithConfiguration(Configuration.DEFAULT);
        
        ClassAnnotationMock classMock = new ClassAnnotationMock();
        classMock.testClassAnnotation_selection_marked("testParam1", "testParam2");
        
        classMock.testClassAnnotation_Ignore("ignoreParam");
    }
}
