package org.audit4j.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;

/**
 * The Class Audit4jTestBase.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class Audit4jTestBase {

	/**
	 * Gets the sample event.
	 *
	 * @return the sample event
	 */
	protected AuditEvent getSampleEvent() {
		AuditEvent event = new AuditEvent();
		event.setAction("action");
		event.setActor("actor");
		event.setAuditId(1212);
		event.setOrigin("origin");
		event.setTimestamp(new Timestamp(new Date().getTime()));
		event.setUuid(Long.parseLong("1221"));
		Field element = new Field();
		element.setName("elementName");
		element.setType("elementType");
		element.setValue("elementValue");

		Field element2 = new Field();
		element2.setName("elementName");
		element2.setType("elementType");
		element2.setValue("elementValue");
		List<Field> elements = new ArrayList<Field>();
		elements.add(element);
		elements.add(element2);
		event.setFields(elements);
		return event;
	}

}
