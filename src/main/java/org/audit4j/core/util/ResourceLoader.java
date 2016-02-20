/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
 * Audit4j - An open source auditing framework.
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

package org.audit4j.core.util;

import java.io.InputStream;
import java.nio.file.NoSuchFileException;

public class ResourceLoader {
	private final String filePath;

	public ResourceLoader(String filePath) {
		this.filePath = filePath;

		if (filePath.startsWith("/")) {
			throw new IllegalArgumentException(
					"Relative paths may not have a leading slash!");
		}
	}

	public InputStream getResource() throws NoSuchFileException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(filePath);

		if (inputStream == null) {
			throw new NoSuchFileException(
					"Resource file not found. Note that the current directory is the source folder!");
		}

		return inputStream;
	}
}