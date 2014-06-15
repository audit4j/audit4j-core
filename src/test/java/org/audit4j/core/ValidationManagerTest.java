package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.exception.ValidationException;
import org.junit.Test;

/**
 * The Class ValidationManagerTest.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class ValidationManagerTest extends Audit4jTestBase{

	/**
	 * Test origin.
	 */
	@Test(expected = ValidationException.class)
	public void testValidate_Null_Event() {
		ValidationManager.validateEvent(null);
	}
	
	/**
	 * Test actor.
	 */
	@Test(expected = ValidationException.class)
	public void testValidate_Actor() {
		AuditEvent event = getSampleEvent();
		// Set Actor as null
		event.setActor(null);
		ValidationManager.validateEvent(event);
	}
	
	/**
	 * Test origin.
	 */
	@Test(expected = ValidationException.class)
	public void testValidate_Origin() {
		AuditEvent event = getSampleEvent();
		// Set Origin as null
		event.setOrigin(null);
		ValidationManager.validateEvent(event);
	}

}
