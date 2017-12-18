package org.audit4j.core;

import java.util.Date;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBuilder;
import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.layout.SimpleLayout;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;

/**
 * The Class Audit4jTestBase.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jTestBase {

    /** The watch. */
    protected StopWatch watch;
    
    protected AuditEvent getSampleAuditEvent() {
        String actor = "Dummy Actor";
        EventBuilder builder = new EventBuilder();
        builder.addTimestamp(new Date()).addActor(actor).addAction("myMethod").addOrigin("Origin").addField("myParam1Name", "param1")
                .addField("myParam2Name", new Integer(2));
        return builder.build();
    }
    
    protected AuditEvent getSampleAuditEvent(String action) {
        String actor = "Dummy Actor";
        EventBuilder builder = new EventBuilder();
        builder.addTimestamp(new Date()).addActor(actor).addAction(action).addOrigin("Origin").addField("myParam1Name", "param1")
                .addField("myParam2Name", new Integer(2));
        return builder.build();
    }

    protected Configuration getDefaultConfiguration() {
        Configuration config = new Configuration();
        config.addHandler(new ConsoleAuditHandler());
        config.setLayout(new SimpleLayout());
        config.setMetaData(new DummyMetaData());
        return config;
    }
    
    /**
     * Watch start.
     *
     * @param name the name
     */
    protected void watchStart(String name) {
        watch = new StopWatch();
        watch.start(name);
    }

    /**
     * Watch stop.
     */
    protected void watchStop() {
        watch.stop();
        Log.info(watch.getLastTaskName() + "=" + watch.getLastTaskTime() + ":" + watch.getLastTaskTimeMillis() + "ms");
    }
    
    protected void halt(){
        for (int i = 0; i < 100000; i++) {
            System.out.print("");
        }
    }
    
    /**
     * Before.
     */
    protected void before(){
        
    }
    
    /**
     * After.
     */
    protected void after(){
        watch.reset();
        watch = null;
    }
}
