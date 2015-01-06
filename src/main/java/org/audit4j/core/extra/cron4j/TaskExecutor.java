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

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>
 * Represents a task executor, which is something similar to threads.
 * </p>
 * <p>
 * Each time a task is launched, a new executor is spawned, executing and
 * watching the task
 * </p>
 * <p>
 * Alive task executors can be retrieved with the
 * {@link Scheduler#getExecutingTasks()} method, and they expose method to
 * control the ongoing execution.
 * </p>
 * 
 * @see Scheduler#getExecutingTasks()
 * @author Carlo Pelliccia
 * @since 2.0
 */
public class TaskExecutor {

	/**
	 * The scheduler whose this executor belongs to.
	 */
	private Scheduler scheduler;

	/**
	 * The executed task.
	 */
	private Task task;

	/**
	 * A task execution context.
	 */
	private MyContext context;

	/**
	 * A unique ID for this executor (used also as a lock object).
	 */
	private String guid = GUIDGenerator.generate();

	/**
	 * An alternative to this (inner classes need it).
	 */
	private TaskExecutor myself = this;

	/**
	 * A list of {@link TaskExecutorListener} instances.
	 */
	private ArrayList listeners = new ArrayList();

	/**
	 * A time stamp reporting the start time of this thread.
	 */
	private long startTime = -1;

	/**
	 * The thread actually executing the task.
	 */
	private Thread thread;

	/**
	 * Is this executor paused now?
	 */
	private boolean paused = false;

	/**
	 * Has been this executor stopped?
	 */
	private boolean stopped = false;

	/**
	 * A lock object, for synchronization purposes.
	 */
	private Object lock = new Object();

	/**
	 * Builds the executor.
	 * 
	 * @param scheduler
	 *            The scheduler whose this executor belongs to.
	 * @param task
	 *            The task that has to be executed.
	 */
	TaskExecutor(Scheduler scheduler, Task task) {
		this.scheduler = scheduler;
		this.task = task;
		this.context = new MyContext();
	}

	/**
	 * Adds a listener to the executor.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addTaskExecutorListener(TaskExecutorListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	/**
	 * Removes a listener from the executor.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void removeTaskExecutorListener(TaskExecutorListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

	/**
	 * Returns an array containing any {@link TaskExecutorListener} previously
	 * registered with the
	 * {@link TaskExecutor#addTaskExecutorListener(TaskExecutorListener)}
	 * method.
	 * 
	 * @return An array containing any {@link TaskExecutorListener} previously
	 *         registered with the
	 *         {@link TaskExecutor#addTaskExecutorListener(TaskExecutorListener)}
	 *         method.
	 */
	public TaskExecutorListener[] getTaskExecutorListeners() {
		synchronized (listeners) {
			int size = listeners.size();
			TaskExecutorListener[] ret = new TaskExecutorListener[size];
			for (int i = 0; i < size; i++) {
				ret[i] = (TaskExecutorListener) listeners.get(i);
			}
			return ret;
		}
	}

	/**
	 * Returns a GUID for this executor.
	 * 
	 * @return A GUID for this executor.
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Returns the {@link Scheduler} instance whose this executor belongs to.
	 * 
	 * @return The scheduler.
	 */
	public Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Returns the representation of the executed task.
	 * 
	 * @return The executing/executed task.
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Returns a time stamp reporting the start time of this executor, or a
	 * value less than 0 if this executor has not been yet started.
	 * 
	 * @return A time stamp reporting the start time of this executor, or a
	 *         value less than 0 if this executor has not been yet started.
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * Checks whether this executor supports pausing.
	 * 
	 * @return true if this executor supports pausing.
	 */
	public boolean canBePaused() {
		return task.canBePaused();
	}

	/**
	 * Checks whether this executor supports stopping.
	 * 
	 * @return true if this executor supports stopping.
	 */
	public boolean canBeStopped() {
		return task.canBeStopped();
	}

	/**
	 * Checks whether this executor provides completeness tracking informations.
	 * 
	 * @return true if this executor provides completeness tracking
	 *         informations.
	 */
	public boolean supportsCompletenessTracking() {
		return task.supportsCompletenessTracking();
	}

	/**
	 * Checks whether this executor provides status tracking messages.
	 * 
	 * @return true if this executor provides status tracking messages.
	 */
	public boolean supportsStatusTracking() {
		return task.supportsStatusTracking();
	}

	/**
	 * Starts executing the task (spawns a secondary thread).
	 * 
	 * @param daemon
	 *            true to spawn a daemon thread; false otherwise.
	 */
	void start(boolean daemon) {
		synchronized (lock) {
			startTime = System.currentTimeMillis();
			String name = "cron4j::scheduler[" + scheduler.getGuid() + "]::executor[" + guid + "]";
			thread = new Thread(new Runner());
			thread.setDaemon(daemon);
			thread.setName(name);
			thread.start();
		}
	}

	/**
	 * Pauses the ongoing execution.
	 * 
	 * @throws UnsupportedOperationException
	 *             The operation is not supported if
	 *             {@link TaskExecutor#canBePaused()} returns <em>false</em>.
	 */
	public void pause() throws UnsupportedOperationException {
		if (!canBePaused()) {
			throw new UnsupportedOperationException("Pause not supported");
		}
		synchronized (lock) {
			if (thread != null && !paused) {
				notifyExecutionPausing();
				paused = true;
			}
		}
	}

	/**
	 * Resumes the execution after it has been paused.
	 */
	public void resume() {
		synchronized (lock) {
			if (thread != null && paused) {
				notifyExecutionResuming();
				paused = false;
				lock.notifyAll();
			}
		}
	}

	/**
	 * Stops the ongoing execution.
	 * 
	 * @throws UnsupportedOperationException
	 *             The operation is not supported if
	 *             {@link TaskExecutor#canBeStopped()} returns <em>false</em>.
	 */
	public void stop() throws UnsupportedOperationException {
		if (!canBeStopped()) {
			throw new UnsupportedOperationException("Stop not supported");
		}
		boolean joinit = false;
		synchronized (lock) {
			if (thread != null && !stopped) {
				stopped = true;
				if (paused) {
					resume();
				}
				notifyExecutionStopping();
				thread.interrupt();
				joinit = true;
			}
		}
		if (joinit) {
			do {
				try {
					thread.join();
					break;
				} catch (InterruptedException e) {
					continue;
				}
			} while (true);
			thread = null;
		}
	}

	/**
	 * Waits for this executor to die.
	 * 
	 * @throws InterruptedException
	 *             If any thread has interrupted the current thread. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public void join() throws InterruptedException {
		if (thread != null) {
			thread.join();
		}
	}

	/**
	 * Tests if this executor is alive. An executor is alive if it has been
	 * started and has not yet died.
	 * 
	 * @return true if this executor is alive; false otherwise.
	 */
	public boolean isAlive() {
		if (thread != null) {
			return thread.isAlive();
		} else {
			return false;
		}
	}

	/**
	 * Returns the current status message.
	 * 
	 * @return The current status message.
	 * @throws UnsupportedOperationException
	 *             The operation is not supported if
	 *             {@link TaskExecutor#supportsStatusTracking()} returns
	 *             <em>false</em>.
	 */
	public String getStatusMessage() throws UnsupportedOperationException {
		if (!supportsStatusTracking()) {
			throw new UnsupportedOperationException(
					"Status tracking not supported");
		}
		return context.getStatusMessage();
	}

	/**
	 * Returns the current completeness value, which is a value between 0 and 1.
	 * 
	 * @return The current completeness value, which is a value between 0 and 1.
	 * @throws UnsupportedOperationException
	 *             The operation is not supported if
	 *             {@link TaskExecutor#supportsCompletenessTracking()} returns
	 *             <em>false</em>.
	 */
	public double getCompleteness() throws UnsupportedOperationException {
		if (!supportsCompletenessTracking()) {
			throw new UnsupportedOperationException(
					"Completeness tracking not supported");
		}
		return context.getCompleteness();
	}

	/**
	 * Tests whether this executor has been paused.
	 * 
	 * @return true if this executor is paused; false otherwise.
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * Tests whether this executor has been stopped.
	 * 
	 * @return true if this executor is stopped; false otherwise.
	 */
	public boolean isStopped() {
		return stopped;
	}

	/**
	 * Notify registered listeners the execution has been paused.
	 */
	private void notifyExecutionPausing() {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.executionPausing(this);
			}
		}
	}

	/**
	 * Notify registered listeners the execution has been resumed.
	 * 
	 */
	private void notifyExecutionResuming() {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.executionResuming(this);
			}
		}
	}

	/**
	 * Notify registered listeners the executor is stopping.
	 */
	private void notifyExecutionStopping() {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.executionStopping(this);
			}
		}
	}

	/**
	 * Notify registered listeners the execution has been terminated.
	 * 
	 * @param exception
	 *            If the execution has been terminated due to an error, this is
	 *            the encountered exception; otherwise the parameter is null.
	 */
	private void notifyExecutionTerminated(Throwable exception) {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.executionTerminated(this, exception);
			}
		}
	}

	/**
	 * Notify registered listeners the execution status message has changed.
	 * 
	 * @param statusMessage
	 *            The new status message.
	 */
	private void notifyStatusMessageChanged(String statusMessage) {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.statusMessageChanged(this, statusMessage);
			}
		}
	}

	/**
	 * Notify registered listeners the execution completeness value has changed.
	 * 
	 * @param completenessValue
	 *            The new completeness value.
	 */
	private void notifyCompletenessValueChanged(double completenessValue) {
		synchronized (listeners) {
			for (Iterator i = listeners.iterator(); i.hasNext();) {
				TaskExecutorListener l = (TaskExecutorListener) i.next();
				l.completenessValueChanged(this, completenessValue);
			}
		}
	}

	/**
	 * Inner Runnable class.
	 */
	private class Runner implements Runnable {

		/**
		 * It implements {@link Thread#run()}, executing the wrapped task.
		 */
		public void run() {
			Throwable error = null;
			startTime = System.currentTimeMillis();
			try {
				// Notify.
				scheduler.notifyTaskLaunching(myself);
				// Task execution.
				task.execute(context);
				// Succeeded.
				scheduler.notifyTaskSucceeded(myself);
			} catch (Throwable exception) {
				// Failed.
				error = exception;
				scheduler.notifyTaskFailed(myself, exception);
			} finally {
				// Notify.
				notifyExecutionTerminated(error);
				scheduler.notifyExecutorCompleted(myself);
			}
		}
	}

	/**
	 * Inner TaskExecutionHelper implementation.
	 */
	private class MyContext implements TaskExecutionContext {

		/**
		 * Status message.
		 */
		private String message = "";

		/**
		 * Completeness value.
		 */
		private double completeness = 0D;

		public Scheduler getScheduler() {
			return scheduler;
		}

		public TaskExecutor getTaskExecutor() {
			return myself;
		}

		public boolean isStopped() {
			return stopped;
		}

		public void pauseIfRequested() {
			synchronized (lock) {
				if (paused) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						;
					}
				}
			}
		}

		public void setCompleteness(double completeness) {
			if (completeness >= 0D && completeness <= 1D) {
				this.completeness = completeness;
				notifyCompletenessValueChanged(completeness);
			}
		}

		public void setStatusMessage(String message) {
			this.message = message != null ? message : "";
			notifyStatusMessageChanged(message);
		}

		/**
		 * Returns the current status message.
		 * 
		 * @return The current status message.
		 */
		public String getStatusMessage() {
			return message;
		}

		/**
		 * Returns the current completeness value, which is a value between 0
		 * and 1.
		 * 
		 * @return The current completeness value, which is a value between 0
		 *         and 1.
		 */
		public double getCompleteness() {
			return completeness;
		}

	}

}
