package org.audit4j.core;



import org.audit4j.core.exception.ValidationException;
import org.junit.Assert;
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
	@Test
	public void testValidate_Null_Event() {
		try {
			ValidationManager.validateEvent(null);
		} catch (ValidationException e) {
			Assert.assertNotNull(e);
		}
	}
	
	//TODO Commented due to fixes
/*	*//**
	 * Test actor.
	 *//*
	@Test
	public void testValidate_Actor() {
		AuditEvent event = getSampleEvent();
		// Set Actor as null
		event.setActor(null);
		try {
			ValidationManager.validateEvent(event);
		} catch (ValidationException e) {
			Assert.assertNotNull(e);
		}
	}
	
	*//**
	 * Test origin.
	 *//*
	@Test
	public void testValidate_Origin() {
		AuditEvent event = getSampleEvent();
		// Set Origin as null
		event.setOrigin(null);
		try {
			ValidationManager.validateEvent(event);
		} catch (ValidationException e) {
			Assert.assertNotNull(e);
		}
	}
*/
}
