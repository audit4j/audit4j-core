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
 * A {@link Task} implementation acting as a wrapper around a {@link Runnable}
 * object.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
class RunnableTask extends Task {

	/**
	 * The wrapped runnable object.
	 */
	private Runnable runnable;

	/**
	 * Builds the task.
	 * 
	 * @param runnable
	 *            The wrapped Runnable object.
	 * @throws InvalidPatternException
	 *             If the supplied pattern is not valid.
	 */
	public RunnableTask(Runnable runnable) throws InvalidPatternException {
		this.runnable = runnable;
	}

	/**
	 * Returns the wrapped Runnable object.
	 * 
	 * @return The wrapped Runnable object.
	 */
	public Runnable getRunnable() {
		return runnable;
	}

	/**
	 * Implements {@link Task#execute(TaskExecutionContext)}, launching the
	 * {@link Runnable#run()} method on the wrapped object.
	 */
	public void execute(TaskExecutionContext context) {
		runnable.run();
	}

	/**
	 * Overrides {@link Object#toString()}.
	 */
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Task[");
		b.append("runnable=");
		b.append(runnable);
		b.append("]");
		return b.toString();
	}

}
