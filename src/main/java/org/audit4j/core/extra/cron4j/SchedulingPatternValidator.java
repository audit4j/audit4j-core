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

/**
 * <p>
 * A scheduling patterns validator.
 * </p>
 * 
 * <p>
 * The class lets you validate a scheduling pattern before/without using it with
 * a {@link Scheduler} instance. Simply call:
 * </p>
 * 
 * <pre>
 * boolean valid = SchedulingPatternValidator.validate(thePattern);
 * </pre>
 * 
 * <p>
 * It is useful in validating user-entered patterns.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @deprecated Use {@link SchedulingPattern#validate(String)}.
 */
public class SchedulingPatternValidator {

	/**
	 * Validates a string as a scheduling pattern.
	 * 
	 * @param schedulingPattern
	 *            The pattern to validate.
	 * @return true if the given string represents a valid scheduling pattern;
	 *         false otherwise.
	 * @deprecated Use {@link SchedulingPattern#validate(String)}.
	 */
	public static boolean validate(String schedulingPattern) {
		return SchedulingPattern.validate(schedulingPattern);
	}

}
