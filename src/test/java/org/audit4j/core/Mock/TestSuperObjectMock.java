package org.audit4j.core.Mock;

import java.util.ArrayList;
import java.util.List;

import org.audit4j.core.annotation.DeIdentify;
import org.audit4j.core.annotation.IgnoreAudit;

public class TestSuperObjectMock {

    private String param1;
    
    private int param2;
    
    private Integer param3;
    
    @DeIdentify
    private boolean param4;
    
    private List<String> param5;
    
    private TestChildObjectMock param6;
    
    private List<TestChildObjectMock> param7;
    
    private List<String> param8 = new ArrayList<>();
    
    @IgnoreAudit
    private List<String> param9 = new ArrayList<>();

    public static TestSuperObjectMock create(){
        TestSuperObjectMock supermock = new TestSuperObjectMock();
        supermock.setParam1("test1");
        supermock.setParam2(2);
        supermock.setParam3(3);
        supermock.setParam4(true);
        
        List<String> param5Tests = new ArrayList<>();
        param5Tests.add("param5Test1");
        param5Tests.add("param5Test2");
        param5Tests.add("param5Test3");
        param5Tests.add("param5Test4");
        supermock.setParam5(param5Tests);
        
        TestChildObjectMock childMock = new TestChildObjectMock();
        childMock.setParam1("test1");
        childMock.setParam2(2);
        childMock.setParam3(new TestChildObjectMock("test2", 3));
        supermock.setParam6(childMock);
        
        List<TestChildObjectMock> childMocks = new ArrayList<>();
        childMocks.add(childMock);
        childMocks.add(new TestChildObjectMock("test4", 10));
        supermock.setParam7(childMocks);
        
        
        
        
        return supermock;
    }
    
    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public int getParam2() {
        return param2;
    }

    public void setParam2(int param2) {
        this.param2 = param2;
    }

    public Integer getParam3() {
        return param3;
    }

    public void setParam3(Integer param3) {
        this.param3 = param3;
    }

    public boolean isParam4() {
        return param4;
    }

    public void setParam4(boolean param4) {
        this.param4 = param4;
    }

    public TestChildObjectMock getParam6() {
        return param6;
    }

    public void setParam6(TestChildObjectMock param6) {
        this.param6 = param6;
    }

    public List<TestChildObjectMock> getParam7() {
        return param7;
    }

    public void setParam7(List<TestChildObjectMock> param7) {
        this.param7 = param7;
    }

    public List<String> getParam5() {
        return param5;
    }

    public void setParam5(List<String> param5) {
        this.param5 = param5;
    }

    public List<String> getParam8() {
        return param8;
    }

    public void setParam8(List<String> param8) {
        this.param8 = param8;
    }

    public List<String> getParam9() {
        return param9;
    }

    public void setParam9(List<String> param9) {
        this.param9 = param9;
    }
    
    
}
