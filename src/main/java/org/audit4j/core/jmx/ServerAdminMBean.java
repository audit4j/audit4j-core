package org.audit4j.core.jmx;


public interface ServerAdminMBean {

    Long getStartupTime();

    boolean isAlive();

    String getServerStatus();

    void start();

    void stop();

    void restart();
}
