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
 * A TaskExecutorListener is notified with events from a {@link TaskExecutor}.
 * You can add listeners to a TaskExecutor by calling its
 * {@link TaskExecutor#addTaskExecutorListener(TaskExecutorListener)} method.
 * 
 * @see TaskExecutor
 * @author Carlo Pelliccia
 * @since 2.0
 */
public interface TaskExecutorListener {

	/**
	 * Called when the execution has been requested to be paused.
	 * 
	 * @param executor
	 *            The source executor.
	 */
	public void executionPausing(TaskExecutor executor);

	/**
	 * Called when the execution has been requested to be resumed.
	 * 
	 * @param executor
	 *            The source executor.
	 */
	public void executionResuming(TaskExecutor executor);

	/**
	 * Called when the executor has been requested to be stopped.
	 * 
	 * @param executor
	 *            The source executor.
	 */
	public void executionStopping(TaskExecutor executor);

	/**
	 * Called at execution end. If the execution has failed due to an error, the
	 * encountered exception is reported.
	 * 
	 * @param executor
	 *            The source executor.
	 * @param exception
	 *            If the execution has been terminated due to an error, this is
	 *            the encountered exception; otherwise the parameter is null.
	 */
	public void executionTerminated(TaskExecutor executor, Throwable exception);

	/**
	 * Called every time the execution status message changes.
	 * 
	 * @param executor
	 *            The source executor.
	 * @param statusMessage
	 *            The new status message.
	 */
	public void statusMessageChanged(TaskExecutor executor, String statusMessage);

	/**
	 * Called every time the execution completeness value changes.
	 * 
	 * @param executor
	 *            The source executor.
	 * @param completenessValue
	 *            The new completeness value.
	 */
	public void completenessValueChanged(TaskExecutor executor,
			double completenessValue);

}
