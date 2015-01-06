package org.audit4j.core;

import org.audit4j.core.exception.ConfigurationException;

public interface ConfigService {

	Configuration readConfig(final String filePath) throws ConfigurationException;

	void generateDummyConfig(Configuration config, final String filePath) throws ConfigurationException;
}
