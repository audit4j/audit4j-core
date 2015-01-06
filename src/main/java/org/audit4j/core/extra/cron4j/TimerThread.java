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
 * TimeThreads are used by {@link Scheduler} instances. A TimerThread spends
 * most of the time sleeping. It wakes up every minute and it requests to the
 * scheduler the spawning of a {@link LauncherThread}.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
class TimerThread extends Thread {

	/**
	 * A GUID for this object.
	 */
	private String guid = GUIDGenerator.generate();

	/**
	 * The owner scheduler.
	 */
	private Scheduler scheduler;

	/**
	 * Builds the timer thread.
	 * 
	 * @param scheduler
	 *            The owner scheduler.
	 */
	public TimerThread(Scheduler scheduler) {
		this.scheduler = scheduler;
		// Thread name.
		String name = "cron4j::scheduler[" + scheduler.getGuid() + "]::timer[" + guid + "]";
		setName(name);
	}

	/**
	 * Returns the GUID for this object.
	 * 
	 * @return The GUID for this object.
	 */
	public Object getGuid() {
		return guid;
	}

	/**
	 * It has been reported that the {@link Thread#sleep(long)} method sometimes
	 * exits before the requested time has passed. This one offers an
	 * alternative that sometimes could sleep a few millis more than requested,
	 * but never less.
	 * 
	 * @param millis
	 *            The length of time to sleep in milliseconds.
	 * @throws InterruptedException
	 *             If another thread has interrupted the current thread. The
	 *             <i>interrupted status</i> of the current thread is cleared
	 *             when this exception is thrown.
	 * @see Thread#sleep(long)
	 */
	private void safeSleep(long millis) throws InterruptedException {
		long done = 0;
		do {
			long before = System.currentTimeMillis();
			sleep(millis - done);
			long after = System.currentTimeMillis();
			done += (after - before);
		} while (done < millis);
	}

	/**
	 * Overrides {@link Thread#run()}.
	 */
	public void run() {
		// What time is it?
		long millis = System.currentTimeMillis();
		// Calculating next minute.
		long nextMinute = ((millis / 60000) + 1) * 60000;
		// Work until the scheduler is started.
		for (;;) {
			// Coffee break 'till next minute comes!
			long sleepTime = (nextMinute - System.currentTimeMillis());
			if (sleepTime > 0) {
				try {
					safeSleep(sleepTime);
				} catch (InterruptedException e) {
					// Must exit!
					break;
				}
			}
			// What time is it?
			millis = System.currentTimeMillis();
			// Launching the launching thread!
			scheduler.spawnLauncher(millis);
			// Calculating next minute.
			nextMinute = ((millis / 60000) + 1) * 60000;
		}
		// Discard scheduler reference.
		scheduler = null;
	}

}
