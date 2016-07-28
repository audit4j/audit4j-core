package org.audit4j.core.codeGen;

import org.audit4j.core.AuditManager;
import org.audit4j.core.Configuration;

public class MainTest {

    public static void main(String[] args) {
        Configuration config = Configuration.DEFAULT;
        config.setCommands("-codeGen=true");
        AuditManager.startWithConfiguration(Configuration.DEFAULT);
        
        ClassAnnotationMock classMock = new ClassAnnotationMock();
        classMock.testClassAnnotation_selection_marked("testParam1", "testParam2");
        
        classMock.testClassAnnotation_Ignore("ignoreParam");
    }
}
