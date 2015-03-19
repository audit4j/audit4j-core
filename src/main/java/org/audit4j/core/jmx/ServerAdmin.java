package org.audit4j.core.jmx;

import org.audit4j.core.RunStatus;

public interface ServerAdmin {

    String getMXBeanType();
    
    Long startupTime();
    
    boolean isAlive();
    
    RunStatus serverStatus();
    
    void start();
    
    void stop();
    
    void restart();
    
}
