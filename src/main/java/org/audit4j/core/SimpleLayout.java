/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;

/**
 * The Class SimpleLayout.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class SimpleLayout implements Layout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5057576669171592167L;

	/* (non-Javadoc)
	 * @see org.audit4j.core.Layout#format(org.audit4j.core.dto.AuditEvent)
	 */
	@Override
	public String format(AuditEvent event) {
		final StringBuilder buff = new StringBuilder();
		buff.append(AuditUtil.dateToString(event.getTimestamp(), "MM/dd/yyyy HH:mm:ss"));
		buff.append(CoreConstants.PIPE);
		buff.append(event.getUuid().toString());
		buff.append(CoreConstants.PIPE);
		buff.append(event.getActor());
		buff.append(CoreConstants.PIPE);
		buff.append(event.getOrigin());
		buff.append(CoreConstants.PIPE);
		if (event.getFields() != null && !event.getFields().isEmpty()) {

			if (event.getAction() != null) {
				buff.append(event.getAction()).append(CoreConstants.ARROW);
			}
			for (Field actionItem : event.getFields()) {
				buff.append(actionItem.getName()).append(CoreConstants.SPACE).append(actionItem.getType()).append(CoreConstants.COLON_CHAR)
						.append(actionItem.getValue()).append(CoreConstants.COMMA_CHAR);
			}
			return buff.toString();
		} else {
			return "No data for selectred audit criteria";
		}
	}
}
