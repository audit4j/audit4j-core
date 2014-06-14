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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hsqldb.Server;

/**
 * A factory for creating Connection objects.
 */
public final class ConnectionFactory {

	/**
	 * Instantiates a new connection factory.
	 */
	private ConnectionFactory() {
	}

	/** The hsql server. */
	Server hsqlServer = null;

	/** The instance. */
	private static ConnectionFactory instance;

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/audit4j", "user", "password");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * Inits the hsql if not initialized.
	 */
	void initHSQLIfNotInitialized() {
		if (hsqlServer == null) {
			hsqlServer = new Server();
			hsqlServer.setLogWriter(null);
			hsqlServer.setSilent(true);
			hsqlServer.setDatabaseName(0, "audit4j");
			hsqlServer.setDatabasePath(0, "file:audit4jdb;user=user;password=password");
			hsqlServer.start();
		}
	}

	/**
	 * Creates a new Connection object.
	 */
	void createTableStructureIfNotExists() {
		StringBuffer query = new StringBuffer("create table if not exists audit (");
		query.append("auditId INT NOT NULL,");
		query.append("uuid char(60) NOT NULL,");
		query.append("timestamp varchar(100) NOT NULL,");
		query.append("actor varchar(200) NOT NULL,");
		query.append("origin varchar(200),");
		query.append("action varchar(200) NOT NULL,");
		query.append("elements varchar(20000)");
		query.append(");");
		try {
			getConnection().prepareStatement(query.toString()).execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Inits the.
	 *
	 * @return true, if successful
	 */
	boolean init(){
		initHSQLIfNotInitialized();
		createTableStructureIfNotExists();
		return Boolean.TRUE;
	}

	/**
	 * Gets the single instance of ConnectionFactory.
	 *
	 * @return single instance of ConnectionFactory
	 */
	static ConnectionFactory getInstance() {
		if (instance == null) {
			instance = new ConnectionFactory();
		}
		return instance;
	}
}
