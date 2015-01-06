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
 * Abstract base representation of a cron4j task.
 * </p>
 * <p>
 * Developers can extends this abstract class to build their own tasks.
 * </p>
 * <p>
 * Extending Task means, above all, implementing the
 * {@link Task#execute(TaskExecutionContext)} method. Within this method the
 * task must perform its operation. If the <em>execute()</em> method returns
 * regularly then the execution is considered to be successfully completed. If
 * <em>execute()</em> dies throwing a {@link RuntimeException} then the task
 * execution is considered to be failed. The supplied parameter, which is a
 * {@link TaskExecutionContext} instance, helps the developer in integrating his
 * task with the scheduler executor. Through the context the developer can check
 * if the execution has been paused or stopped, and he can also push back some
 * status informations by calling
 * {@link TaskExecutionContext#setCompleteness(double)} and
 * {@link TaskExecutionContext#setStatusMessage(String)}.
 * </p>
 * <p>
 * If the custom task supports pausing, stopping and/or tracking, that should be
 * notified by overriding {@link Task#canBePaused()},
 * {@link Task#canBeStopped()}, {@link Task#supportsCompletenessTracking()}
 * and/or {@link Task#supportsStatusTracking()}.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
public abstract class Task {

	/**
	 * The ID for this task. Also used as an instance synchronization lock.
	 */
	private Object id = GUIDGenerator.generate();

	/**
	 * Empty constructor, does nothing.
	 */
	public Task() {
	}

	/**
	 * It returns the ID for this task.
	 * 
	 * @return The ID for this task.
	 */
	Object getId() {
		return id;
	}

	/**
	 * <p>
	 * Checks whether this task supports pause requests.
	 * </p>
	 * <p>
	 * Default implementation returns <em>false</em>.
	 * </p>
	 * <p>
	 * Task developers can override this method to let it return a <em>true</em>
	 * value, and at the same time they have to implement the
	 * {@link Task#execute(TaskExecutionContext)} method, so that pause requests
	 * are really handled. This can be done calling regularly the
	 * {@link TaskExecutionContext#pauseIfRequested()} method during the task
	 * execution.
	 * </p>
	 * 
	 * @return true if this task can be paused; false otherwise.
	 */
	public boolean canBePaused() {
		return false;
	}

	/**
	 * <p>
	 * Checks whether this task supports stop requests.
	 * </p>
	 * <p>
	 * Default implementation returns <em>false</em>.
	 * </p>
	 * <p>
	 * Task developers can override this method to let it return a <em>true</em>
	 * value, and at the same time they have to implement the
	 * {@link Task#execute(TaskExecutionContext)} method, so that stop requests
	 * are really handled. This can be done checking regularly the
	 * {@link TaskExecutionContext#isStopped()} method during the task
	 * execution.
	 * </p>
	 * 
	 * @return true if this task can be stopped; false otherwise.
	 */
	public boolean canBeStopped() {
		return false;
	}

	/**
	 * <p>
	 * Tests whether this task supports status tracking.
	 * </p>
	 * <p>
	 * Default implementation returns <em>false</em>.
	 * </p>
	 * <p>
	 * The task developer can override this method and returns <em>true</em>,
	 * having care to regularly calling the
	 * {@link TaskExecutionContext#setStatusMessage(String)} method during the
	 * task execution.
	 * </p>
	 * 
	 * @return true if this task, during its execution, provides status message
	 *         regularly.
	 */
	public boolean supportsStatusTracking() {
		return false;
	}

	/**
	 * <p>
	 * Tests whether this task supports completeness tracking.
	 * </p>
	 * <p>
	 * Default implementation returns <em>false</em>.
	 * </p>
	 * <p>
	 * The task developer can override this method and returns <em>true</em>,
	 * having care to regularly calling the
	 * {@link TaskExecutionContext#setCompleteness(double)} method during the
	 * task execution.
	 * </p>
	 * 
	 * @return true if this task, during its execution, provides a completeness
	 *         value regularly.
	 */
	public boolean supportsCompletenessTracking() {
		return false;
	}

	/**
	 * <p>
	 * This method is called to require a task execution, and should contain the
	 * core routine of any scheduled task.
	 * </p>
	 * 
	 * <p>
	 * If the <em>execute()</em> method ends regularly the scheduler will
	 * consider the execution successfully completed, and this will be
	 * communicated to any {@link SchedulerListener} interested in it. If the
	 * <em>execute()</em> method dies throwing a {@link RuntimeException} the
	 * scheduler will consider it as a failure notification. Any
	 * {@link SchedulerListener} will be notified about the occurred exception.
	 * </p>
	 * 
	 * @param context
	 *            The execution context.
	 * @throws RuntimeException
	 *             Task execution has somehow failed.
	 */
	public abstract void execute(TaskExecutionContext context)
			throws RuntimeException;

}
