package org.audit4j.core.smoke;

import java.util.concurrent.TimeUnit;

import org.audit4j.core.AuditManager;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBuilder;
import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.Test;

public class SmokeTest {

    @Test
    public void smokeTestAuditEvent() throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start("smoke");
        String actor = "Dummy Actor";
        EventBuilder builder = new EventBuilder();
        builder.addActor(actor).addAction("myMethod").addOrigin("Origin1").addField("myParam1Name", "param1")
                .addField("myParam2Name", new Integer(2));
        AuditEvent event = builder.build();
        watch.stop();
        Log.info(watch.getTotalTime());
        watch.start();
        AuditManager manager = AuditManager.getInstance();
        manager.audit(event);

        watch.stop();
        Log.info(watch.getTotalTime());

        watch.start("smoke_event");
        AuditManager.getInstance().audit(builder.build());
        watch.stop();
        Log.info(watch.getTotalTime());
        TimeUnit.SECONDS.sleep(4);
    }

    public static void main(String[] args) {
        AuditManager manager = AuditManager.getInstance();
        int count = 0;
        while (count < 100000) {
            EventBuilder builder = new EventBuilder();
            builder.addActor("Dummy Actor").addAction("myMethod").addOrigin("Origin1")
                    .addField("myParam1Name", "param1").addField("myParam2Name", new Integer(2));
            AuditEvent event = builder.build();
            manager.audit(event);
            count++;
        }
    }
}
