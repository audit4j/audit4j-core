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
