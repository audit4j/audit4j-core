/*
 * Copyright 2014 Janith Bandara
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

package org.audit4j.core.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class GeneralDatabaseAuditHandler.
 * 
 * @author Janith Bandara
 */
public class GeneralDatabaseAuditHandler extends Handler {

	/** The log. */
	private final Log log = LogFactory.getLog(GeneralDatabaseAuditHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#handle()
	 */
	@Override
	public void handle() {
		// final GeneralDatabaseAuditDto auditDto = new
		// GeneralDatabaseAuditDto();
		// auditDto.setUserId(getUserIdentifier());
		// auditDto.setUserFirstName(getUserName());
		// auditDto.setUuid(getUUID());
		// auditDto.setDetail(getQuery());
		// auditDto.setTimestamp(new Date());

		// final AsyncAuditEngine engine = AsyncAuditEngine.getInstance();
		// engine.send(auditDto);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#getUserIdentifier()
	 */
	@Override
	public String getUserIdentifier() {
		// TODO
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO
		return null;
	}
}
