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
 * Implement this interface and register your instance with the
 * {@link Scheduler#addSchedulerListener(SchedulerListener)} method to receive
 * notifications about scheduled task executions.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
public interface SchedulerListener {

	/**
	 * This one is called by the scheduler when a task execution is starting.
	 * 
	 * @param executor
	 *            The task executor.
	 */
	public void taskLaunching(TaskExecutor executor);

	/**
	 * This one is called by the scheduler to notify that a task execution has
	 * been successfully completed.
	 * 
	 * @param executor
	 *            The task executor.
	 */
	public void taskSucceeded(TaskExecutor executor);

	/**
	 * This one is called by the scheduler to notify that a task execution has
	 * failed.
	 * 
	 * @param executor
	 *            The task executor.
	 * @param exception
	 *            The exception representing the failure notification.
	 */
	public void taskFailed(TaskExecutor executor, Throwable exception);

}
