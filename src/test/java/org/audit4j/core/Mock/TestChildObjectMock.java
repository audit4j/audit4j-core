package org.audit4j.core.Mock;

public class TestChildObjectMock {

    private String param1;
    
    private int param2;
    
    private TestChildObjectMock param3;

    public TestChildObjectMock() {
        // TODO Auto-generated constructor stub
    }
    
    public TestChildObjectMock(String param1, int param2) {
        this.param1 = param1;
        this.param2 = param2;
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

    public TestChildObjectMock getParam3() {
        return param3;
    }

    public void setParam3(TestChildObjectMock param3) {
        this.param3 = param3;
    }
}
