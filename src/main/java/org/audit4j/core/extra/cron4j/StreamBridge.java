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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * <p>
 * A package-reserved utility class. It spawns a secondary thread in which the
 * supplied {@link InputStream} instance is read, and the incoming contents are
 * written in the supplied {@link OutputStream}.
 * </p>
 * 
 * @author Carlo Pelliccia
 * @since 2.0
 */
class StreamBridge {

	/**
	 * Used to trace alive instances.
	 */
	private static ArrayList traced = new ArrayList();

	/**
	 * A self-referece, for inner classes.
	 */
	private StreamBridge myself = this;

	/**
	 * The thread executing the job.
	 */
	private Thread thread;

	/**
	 * The stream from which the data is read.
	 */
	private InputStream in;

	/**
	 * The stream in which the data is written.
	 */
	private OutputStream out;

	/**
	 * Builds the instance.
	 * 
	 * @param in
	 *            The stream from which the data is read.
	 * @param out
	 *            The stream in which the data is written.
	 */
	public StreamBridge(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
		this.thread = new Thread(new Runner());
		synchronized (traced) {
			traced.add(this);
		}
	}

	/**
	 * Starts the bridge job.
	 */
	public void start() {
		thread.start();
	}

	/**
	 * Aborts the ongoing job.
	 */
	public void abort() {
		thread.interrupt();
		try {
			out.close();
		} catch (Throwable t) {
			;
		}
		try {
			in.close();
		} catch (Throwable t) {
			;
		}
	}

	/**
	 * Waits for this job to die.
	 * 
	 * @throws InterruptedException
	 *             If another thread has interrupted the current thread. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public void join() throws InterruptedException {
		thread.join();
	}

	/**
	 * Waits at most <code>millis</code> milliseconds for this thread to die. A
	 * timeout of <code>0</code> means to wait forever.
	 * 
	 * @param millis
	 *            the time to wait in milliseconds.
	 * @throws InterruptedException
	 *             If another thread has interrupted the current thread. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public void join(long millis) throws InterruptedException {
		thread.join(millis);
	}

	/**
	 * @param millis
	 *            the time to wait in milliseconds.
	 * @param nanos
	 *            0-999999 additional nanoseconds to wait.
	 * @throws IllegalArgumentException
	 *             if the value of millis is negative the value of nanos is not
	 *             in the range 0-999999.
	 * @throws InterruptedException
	 *             If another thread has interrupted the current thread. The
	 *             interrupted status of the current thread is cleared when this
	 *             exception is thrown.
	 */
	public void join(long millis, int nanos) throws IllegalArgumentException,
			InterruptedException {
		thread.join(millis, nanos);
	}

	/**
	 * Tests if this bridge is alive. A job is alive if it has been started and
	 * has not yet completed.
	 * 
	 * @return <code>true</code> if this thread is alive; <code>false</code>
	 *         otherwise.
	 */
	public boolean isAlive() {
		return thread.isAlive();
	}

	/**
	 * Contains the routine doing the job in the secondary thread.
	 */
	private class Runner implements Runnable {

		public void run() {
			boolean skipout = false;
			for (;;) {
				int b;
				try {
					b = in.read();
				} catch (IOException e) {
					if (!Thread.interrupted()) {
						e.printStackTrace();
					}
					break;
				}
				if (b == -1) {
					break;
				}
				if (!skipout) {
					try {
						out.write(b);
					} catch (IOException e) {
						if (!Thread.interrupted()) {
							e.printStackTrace();
						}
						skipout = true;
					}
				}
			}
			try {
				out.close();
			} catch (Throwable t) {
				;
			}
			try {
				in.close();
			} catch (Throwable t) {
				;
			}
			synchronized (traced) {
				traced.remove(myself);
			}
		}

	}

}
