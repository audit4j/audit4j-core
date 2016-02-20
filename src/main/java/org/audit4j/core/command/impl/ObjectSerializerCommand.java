/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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

package org.audit4j.core.command.impl;

import org.audit4j.core.ObjectSerializer;
import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;

public class ObjectSerializerCommand extends AbstractCommand {

	private ObjectSerializer serializer = null;

	@SuppressWarnings("unchecked")
	@Override
	public void init() throws InitializationException {
		String rawClass = getOptionByCommand(getCommand());
		Class<ObjectSerializer> clazz;
		try {
			clazz = (Class<ObjectSerializer>) Class.forName(rawClass);
			serializer = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			throw new InitializationException("Given serializer class name not supported.!", e);
		}
	}
	
	public ObjectSerializer getSerializer() {
		return serializer;
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCommand() {
		return "-objectSerializer";
	}

	@Override
	public String getCommandDescription() {
		return "Sets Custom Serializer implementation ";
	}

}
