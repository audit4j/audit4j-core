package org.audit4j.core;

import java.util.concurrent.TimeUnit;

import org.audit4j.core.dto.EventBuilder;
import org.junit.Test;

public class SmokeTest {

	@Test
	public void smokeTestAuditEvent() throws InterruptedException{
		String actor = "Dummy Actor";
		EventBuilder builder = new EventBuilder();
		builder.addActor(actor).addAction("myMethod").addOrigin("Origin1").addField("myParam1Name", "param1").addField("myParam2Name", new Integer(2));
		AuditManager manager = AuditManager.getInstance();
		manager.audit(builder.build());
		
		TimeUnit.SECONDS.sleep(4);
	}
}
