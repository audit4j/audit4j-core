/*
 * Copyright 2014 Janith Bandara, This source is a part of Audit4j - 
 * An open-source audit platform for Enterprise java platform.
 * http://mechanizedspace.com/audit4j
 * http://audit4j.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.audit4j.core;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.security.AccessControlException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class TroubleshootManager.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 */
public final class TroubleshootManager {

	/** The Constant MIN_PORT_NUMBER. */
	public static final int MIN_PORT_NUMBER = 1100;

	/** The Constant MAX_PORT_NUMBER. */
	public static final int MAX_PORT_NUMBER = 49151;

	/**
	 * Checks if is annotation intregration available.
	 * 
	 * @return true, if is annotation intregration available
	 */
	static boolean isAnnotationIntregrationAvailable() {
		ClassLoader classLoader = TroubleshootManager.class.getClassLoader();
		try {
			Class<?> bindingClass = classLoader.loadClass(CoreConstants.ANNOTATION_INTREGRATION_CLASS);
			// bindingClass.get
			return (bindingClass != null);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * Checks if is port available.
	 * 
	 * @param port
	 *            the port
	 * @return true, if is port available
	 */
	static boolean isPortAvailable(final int port) {
		if (port < MIN_PORT_NUMBER || port > MAX_PORT_NUMBER) {
			throw new IllegalArgumentException("Invalid start port: " + port);
		}

		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}

	/**
	 * Checks for disk access.
	 * 
	 * @param path
	 *            the path
	 * @return true, if successful
	 */
	static boolean hasDiskAccess(final String path) {
		try {
			AccessController.checkPermission(new FilePermission(path, "read,write"));
			return true;
		} catch (AccessControlException e) {
			return false;
		}
	}

	/**
	 * Checks if is file already exists.
	 *
	 * @param path the path
	 * @return true, if is file already exists
	 */
	public static boolean isFileAlreadyExists(String path) {
		File file = new File(path);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is jD k_ n_ or higher.
	 * 
	 * @param n
	 *            the n
	 * @return true, if is jD k_ n_ or higher
	 */
	private static boolean isJDK_N_OrHigher(int n) {
		List<String> versionList = new ArrayList<String>();
		// this code should work at least until JDK 10 (assuming n parameter is
		// always 6 or more)
		for (int i = 0; i < 5; i++) {
			versionList.add("1." + (n + i));
		}

		String javaVersion = System.getProperty("java.version");
		if (javaVersion == null) {
			return false;
		}
		for (String v : versionList) {
			if (javaVersion.startsWith(v))
				return true;
		}
		return false;
	}

	/**
	 * Checks if is jD k5.
	 * 
	 * @return true, if is jD k5
	 */
	static public boolean isJDK5() {
		return isJDK_N_OrHigher(5);
	}

	/**
	 * Checks if is jD k6 or higher.
	 * 
	 * @return true, if is jD k6 or higher
	 */
	static public boolean isJDK6OrHigher() {
		return isJDK_N_OrHigher(6);
	}

	/**
	 * Checks if is jD k7 or higher.
	 * 
	 * @return true, if is jD k7 or higher
	 */
	static public boolean isJDK7OrHigher() {
		return isJDK_N_OrHigher(7);
	}

	/**
	 * Checks if is windows.
	 * 
	 * @return true, if is windows
	 */
	public static boolean isWindows() {
		String os = System.getProperty("os.name");
		return os.startsWith("Windows");
	}
}
