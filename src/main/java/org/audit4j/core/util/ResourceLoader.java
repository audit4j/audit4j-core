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