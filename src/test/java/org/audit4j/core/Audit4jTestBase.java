package org.audit4j.core;

import java.util.Date;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.EventBuilder;
import org.audit4j.core.handler.ConsoleAuditHandler;
import org.audit4j.core.layout.SimpleLayout;

/**
 * The Class Audit4jTestBase.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jTestBase {

    protected AuditEvent getSampleAuditEvent() {
        String actor = "Dummy Actor";
        EventBuilder builder = new EventBuilder();
        builder.addTimestamp(new Date()).addActor(actor).addAction("myMethod").addOrigin("Origin").addField("myParam1Name", "param1")
                .addField("myParam2Name", new Integer(2));
        AuditEvent event = builder.build();
        return event;
    }

    protected Configuration getDefaultConfiguration() {
        Configuration config = new Configuration();
        config.addHandler(new ConsoleAuditHandler());
        config.setLayout(new SimpleLayout());
        config.setMetaData(new DummyMetaData());
        return config;
    }
}
