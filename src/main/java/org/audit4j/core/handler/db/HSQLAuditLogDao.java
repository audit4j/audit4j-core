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

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.audit4j.core.dto.AuditEvent;
import org.audit4j.core.dto.Element;

/**
 * The Class HSQLAuditLogDao.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public class HSQLAuditLogDao extends AuditBaseDao implements AuditLogDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.audit4j.core.handler.db.AuditLogDao#createEvent(org.audit4j.core.
	 * dto.AuditEvent)
	 */
	@Override
	public boolean writeEvent(AuditEvent event) throws SQLException {

		StringBuffer elements = new StringBuffer();

		for (Element element : event.getEventElements()) {
			elements.append(element.getName() + " " + element.getType() + ":" + element.getValue() + ", ");
		}
		StringBuffer query = new StringBuffer();
		query.append("insert into audit(auditId, uuid, timestamp, actor, origin, action, elements) ").append(
				"values (?, ?, ?, ?, ?, ?, ?)");

		PreparedStatement statement = getConnection().prepareStatement(query.toString());
		statement.setInt(1, event.getAuditId());
		statement.setString(2, event.getUuid().toString());
		statement.setString(3, event.getTimestamp().toString());
		statement.setString(4, event.getActor());
		statement.setString(5, event.getOrigin());
		statement.setString(6, event.getAction());
		statement.setString(7, elements.toString());
		return statement.execute();
	}

}
