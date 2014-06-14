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

package org.audit4j.core.handler.db;

import java.sql.SQLException;

import org.audit4j.core.handler.Handler;

/**
 * The Class GeneralDatabaseAuditHandler.
 * 
 * @author Janith Bandara
 */
public class GeneralDatabaseAuditHandler extends Handler {

	/* (non-Javadoc)
	 * @see org.audit4j.core.handler.Handler#init()
	 */
	@Override
	public boolean init() {
		ConnectionFactory factory = ConnectionFactory.getInstance();
		return factory.init();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#handle()
	 */
	@Override
	public void handle() {
		AuditLogDao dao = new HSQLAuditLogDao();
		try {
			dao.writeEvent(getAuditEvent());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#getUserIdentifier()
	 */
	@Override
	public String getUserIdentifier() {
		// TODO
		return "DummyIdentifier";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bi3.commons.audit.handler.Handler#getUserName()
	 */
	@Override
	public String getUserName() {
		// TODO
		return "Dummyusername";
	}

}
