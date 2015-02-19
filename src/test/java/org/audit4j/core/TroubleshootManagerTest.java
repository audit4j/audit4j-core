package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.TroubleshootException;
import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TroubleshootManagerTest.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class TroubleshootManagerTest extends Audit4jTestBase {

	/**
	 * Test troubleshoot event_ null_ event.
	 */
	@Test(expected = TroubleshootException.class)
	public void testTroubleshootEvent_Null_Event(){
		TroubleshootManager.troubleshootEvent(null);
	}
	
	/**
	 * Test troubleshoot event_ actor.
	 */
	@Test
	public void testTroubleshootEvent_Actor() {
		AuditEvent event = getSampleAuditEvent();
		// Set Actor as null
		event.setActor(null);
		TroubleshootManager.troubleshootEvent(event);
		Assert.assertNotNull(event.getActor());
	}
	
	/**
	 * Test troubleshoot event_ orign.
	 */
	//@Test(expected = TroubleshootException.class)
	public void testTroubleshootEvent_Orign() {
		AuditEvent event = getSampleAuditEvent();
		// Set Actor as null
		event.setOrigin(null);
		TroubleshootManager.troubleshootEvent(event);	
	}
	
	@Test
	public void testTroubleshootEvent_NULL_ACTOR_NULL_METADATA() {
        Configuration config = getDefaultConfiguration();
        config.setMetaData(null);
        AuditManager manager = AuditManager.getConfigurationInstance(config);
	    AuditEvent event = getSampleAuditEvent();
        // Set Actor as null
        event.setActor(null);
        TroubleshootManager.troubleshootEvent(event);   
    }
}
