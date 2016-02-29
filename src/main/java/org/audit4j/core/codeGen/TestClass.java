package org.audit4j.core.codeGen;

import org.audit4j.core.annotation.Audit;
import org.audit4j.core.annotation.AuditField;

@Audit
public class TestClass {

    public void test(){
        
    }
    
    public void test2(@AuditField(field="abb") String a){
        
    }
}
