/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
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

package org.audit4j.core.layout;

import java.util.Date;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Field;
import org.audit4j.core.exception.InitializationException;
import org.audit4j.core.util.ConcurrentDateFormatAccess;

/**
 * The Class SimpleLayout.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class SimpleLayout implements Layout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5057576669171592167L;

	private String dateFormat = CoreConstants.DEFAULT_DATE_FORMAT;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.audit4j.core.Layout#format(org.audit4j.core.dto.AuditEvent)
	 */
	@Override
	public String format(AuditEvent event) {
		final StringBuilder buff = new StringBuilder();
		if (null != event.getTimestamp()) {
			buff.append(new ConcurrentDateFormatAccess(dateFormat).convertDateToString(event.getTimestamp()));
		} else {
			buff.append(new ConcurrentDateFormatAccess(dateFormat).convertDateToString(new Date()));
		}
		buff.append(CoreConstants.PIPE);
		if (null != event.getUuid()) {
			buff.append(event.getUuid().toString());
			buff.append(CoreConstants.PIPE);
		}

		buff.append(event.getActor());
		buff.append(CoreConstants.PIPE);
		buff.append(event.getOrigin());
		buff.append(CoreConstants.PIPE);

		if (event.getAction() != null) {
			buff.append(event.getAction()).append(CoreConstants.ARROW);
		}
		if (event.getFields() != null && !event.getFields().isEmpty()) {
			for (Field actionItem : event.getFields()) {
				buff.append(actionItem.getName()).append(CoreConstants.SPACE).append(actionItem.getType())
						.append(CoreConstants.COLON_CHAR).append(actionItem.getValue())
						.append(CoreConstants.COMMA_CHAR);
			}
		}
		return buff.toString();
	}

	@Override
	public void init() throws InitializationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
    	return dateFormat;
    }
}
