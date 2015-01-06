/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2010 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package org.audit4j.core.extra.cron4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This kind of task can be used to invoke a static method of a Java class. The
 * specified method must accept an array of strings as its sole argument.
 * 
 * @author Carlo Pelliccia
 * @since 2.2
 */
class StaticMethodTask extends Task {

	/**
	 * The Java class name.
	 */
	private String className;

	/**
	 * The name of the static method of the class that has to be launched.
	 */
	private String methodName;

	/**
	 * Arguments for the static method. The array can be empty, but it can't be
	 * null.
	 */
	private String[] args;

	/**
	 * Builds the task.
	 * 
	 * @param className
	 *            The Java class name.
	 * @param methodName
	 *            The name of the static method of the class that has to be
	 *            launched.
	 * @param args
	 *            Arguments for the static method. The array can be empty, but
	 *            it can't be null.
	 */
	public StaticMethodTask(String className, String methodName, String[] args) {
		this.className = className;
		this.methodName = methodName;
		this.args = args;
	}

	/**
	 * Implements {@link Task#execute(TaskExecutionContext)}. It uses Java
	 * reflection to load the given class and call the given static method with
	 * the supplied arguments.
	 */
	public void execute(TaskExecutionContext context) throws RuntimeException {
		// Loads the class.
		Class classObject;
		try {
			classObject = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Cannot load class " + className, e);
		}
		// Finds the method.
		Method methodObject;
		try {
			Class[] argTypes = new Class[] { String[].class };
			methodObject = classObject.getMethod(methodName, argTypes);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("Cannot find a " + methodName
					+ "(String[]) method in class " + className, e);
		}
		int modifiers = methodObject.getModifiers();
		if (!Modifier.isStatic(modifiers)) {
			throw new RuntimeException("The method " + methodName
					+ "(String[]) of the class " + className + " is not static");
		}
		// Invokes the method.
		try {
			methodObject.invoke(null, new Object[] { args });
		} catch (Exception e) {
			throw new RuntimeException("Failed to invoke the static method "
					+ methodName + "(String[]) of the class " + className);
		}
	}

}
