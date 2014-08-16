/*
 * Copyright 2014 Janith Bandara, This source is a part of 
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

package org.audit4j.core;

import org.audit4j.core.dto.AuditEvent;

/**
 * The Class SecureLayout.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 1.0.0
 */
public class SecureLayout extends SimpleLayout {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5678939488854601303L;

	/* (non-Javadoc)
	 * @see org.audit4j.core.SimpleLayout#format(org.audit4j.core.dto.AuditEvent)
	 */
	@Override
	public String format(AuditEvent event) {
		String formatText = super.format(event);
		try {
			//TODO hard coded key
			EncryptionUtil util = EncryptionUtil.getInstance("1234", CoreConstants.SALT);
			return util.encrypt(formatText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
