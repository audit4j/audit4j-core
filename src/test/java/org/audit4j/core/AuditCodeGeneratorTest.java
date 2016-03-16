package org.audit4j.core;

import org.audit4j.core.Mock.ClassAnnotationMock;
import org.audit4j.core.codeGen.AuditCodeGenerator;
import org.audit4j.core.codeGen.CodeGenException;
import org.junit.Test;

public class AuditCodeGeneratorTest extends Audit4jTestBase{

    @Test
    public void testGenerate(){
        AuditCodeGenerator gen = new AuditCodeGenerator();
        try {
            gen.generate();
        } catch (CodeGenException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ClassAnnotationMock classMock = new ClassAnnotationMock();
        classMock.testClassAnnotation_selection_marked("testParam1", "testParam2");
    }
}
