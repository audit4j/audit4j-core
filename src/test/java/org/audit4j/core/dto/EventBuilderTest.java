package org.audit4j.core.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.audit4j.core.util.Log;
import org.audit4j.core.util.StopWatch;
import org.junit.Test;

public class EventBuilderTest {

    @Test
    public void testEventBuilder() {
        StopWatch watch = new StopWatch();
        watch.start("builder");
        EventBuilder builder = new EventBuilder();
        builder.addActor("Actor").addAction("myMethod").addOrigin("Origin1").addField("myParam1Name", "param1")
                .addField("myParam2Name", new Integer(2));
        AuditEvent event = builder.build();
        watch.stop();
        Log.info(watch.getLastTaskTimeMillis());

        assertNotNull(event);
        assertEquals("Actor", event.getActor());
        assertEquals("myMethod", event.getAction());
        assertEquals("Origin1", event.getOrigin());
        assertNotNull(event.getFields());
        for (Field field : event.getFields()) {
            assertNotNull(field);
        }
    }
}
