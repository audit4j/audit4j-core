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
 * A TaskExecutionContext object provides support methods for the execution of a
 * task. An instance of this class is always passed to the task when its
 * {@link Task#execute(TaskExecutionContext)} method is called. The task, while
 * executing, can use the received context to exchange informations with its own
 * executor. If the task declares to supports pausing, stopping, completeness
 * tracking and/or status tracking, it has to use its context methods to perform
 * any declared operation (checks pause and stop requests, sends back tracking
 * informations).
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
public interface TaskExecutionContext {

	/**
	 * Returns the scheduler.
	 * 
	 * @return The scheduler.
	 */
	public Scheduler getScheduler();

	/**
	 * Returns the task executor.
	 * 
	 * @return The task executor.
	 */
	public TaskExecutor getTaskExecutor();

	/**
	 * Sets the current status tracking message, that has to be something about
	 * what the task is doing at the moment.
	 * 
	 * @param message
	 *            A message representing the current execution status. Null
	 *            messages will be blanked.
	 */
	public void setStatusMessage(String message);

	/**
	 * Sets the completeness tracking value, that has to be between 0 and 1.
	 * 
	 * @param completeness
	 *            A completeness value, between 0 and 1. Values out of range
	 *            will be ignored.
	 */
	public void setCompleteness(double completeness);

	/**
	 * If the task execution has been paused, stops until the operation is
	 * resumed. It can also returns because of a stop operation without any
	 * previous resuming. Due to this the task developer should always check the
	 * {@link TaskExecutionContext#isStopped()} value after any
	 * <em>pauseIfRequested()</em> call. Note that a task execution can be
	 * paused only if the task {@link Task#canBePaused()} method returns
	 * <em>true</em>.
	 */
	public void pauseIfRequested();

	/**
	 * Checks whether the task execution has been demanded to be stopped. If the
	 * returned value is <em>true</em>, the task developer must shut down
	 * gracefully its task execution, as soon as possible. Note that a task
	 * execution can be stopped only if the task {@link Task#canBePaused()}
	 * method returns <em>true</em>.
	 * 
	 * @return <em>true</em> if the current task execution has been demanded to
	 *         be stopped; <em>false</em> otherwise.
	 */
	public boolean isStopped();

}
