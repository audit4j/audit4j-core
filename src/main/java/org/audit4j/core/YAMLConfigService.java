/*
 * Copyright (c) 2014-2015 Janith Bandara, This source is a part of
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

package org.audit4j.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.exception.TroubleshootException;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

public class YAMLConfigService implements ConfigService{

	@Override
	public Configuration readConfig(String filePath) throws ConfigurationException {
		try {
			YamlReader reader = new YamlReader(new FileReader(filePath));
			reader.getConfig().setClassTag("Configuration", Configuration.class);
			return (Configuration) reader.read();
		} catch (FileNotFoundException e) {
			throw new ConfigurationException("Configuration Exception", "CONF_001", e);
		} catch (YamlException e) {
			throw new ConfigurationException("Configuration Exception", "CONF_002", e);
		}

	}

	@Override
	public void generateDummyConfig(Configuration config, String filePath) {
		StringBuffer yml = new StringBuffer("!Configuration\n");
		yml.append("released: ").append(CoreConstants.RELEASE_DATE).append("\n");
		yml.append("version: ").append(CoreConstants.RELEASE_VERSION).append("\n");
		yml.append("handlers:").append("\n");
		yml.append("- !org.audit4j.core.handler.ConsoleAuditHandler {}").append("\n");
		yml.append("- !org.audit4j.core.handler.file.FileAuditHandler {}").append("\n");
		yml.append("layout: !org.audit4j.core.SimpleLayout {}").append("\n");
		yml.append("metaData: !org.audit4j.core.DummyMetaData {}").append("\n");
		yml.append("properties:").append("\n");
		yml.append("   log.file.location: user.dir").append("\n");
		
		File file = new File(CoreConstants.CONFIG_FILE_NAME);
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(yml.toString());
			bw.close();
		} catch (IOException e) {
			throw new TroubleshootException("Unable to create configuration file. ", e);
		}
	}

}
