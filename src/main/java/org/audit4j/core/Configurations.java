package org.audit4j.core;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.util.AuditUtil;

/**
 * The Class Configurations.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class Configurations {

    /** The Constant XML_EXTENTION. */
    static final String XML_EXTENTION = "xml";

    /** The Constant YML_EXTENTION. */
    static final String YML_EXTENTION = "yml";

    /** The Constant YAML_EXTENTION. */
    static final String YAML_EXTENTION = "yaml";

    /** The Constant CONFIG_FILE_NAME. */
    static final String CONFIG_FILE_NAME = "audit4j.conf";

    /** The provider. */
    private static ConfigProvider<Configuration> provider;

    /**
     * Instantiates a new configurations.
     */
    public Configurations() {

    }

    /**
     * Scan config file.
     *
     * @param dirPath the dir path
     * @return the string
     */
    static String scanConfigFile(String dirPath) {
        if (dirPath == null) {
            dirPath = System.getProperty("user.dir");
        }

        String filePath = dirPath + File.separator + CONFIG_FILE_NAME + ".";

        String fullFilePath = null;
        // Scan for Yaml file
        if (AuditUtil.isFileExists(filePath + YML_EXTENTION)) {
            fullFilePath = filePath + YML_EXTENTION;
        } else if (AuditUtil.isFileExists(filePath + YAML_EXTENTION)) {
            fullFilePath = filePath + YAML_EXTENTION;
        } else if (AuditUtil.isFileExists(filePath + XML_EXTENTION)) {
            // Scan for XML file
            fullFilePath = filePath + XML_EXTENTION;
        } else {
            // Default configuratin file
            fullFilePath = filePath + YML_EXTENTION;
        }
        return fullFilePath;
    }

    /**
     * Load config.
     *
     * @param configFilePath the config file path
     * @return the configuration
     * @throws ConfigurationException the configuration exception
     */
    static Configuration loadConfig(String configFilePath) throws ConfigurationException {
        String fileExtention = FilenameUtils.getExtension(configFilePath);
        if (fileExtention != null
                && (XML_EXTENTION.equals(fileExtention) || YML_EXTENTION.equals(fileExtention) || YAML_EXTENTION
                        .equals(fileExtention))) {
            provider = getProviderByFileExtention(fileExtention);
        } else {
            throw new ConfigurationException("Given file type is not supported.", "");
        }
        if (!AuditUtil.isFileExists(configFilePath)) {
            provider.generateConfig(Configuration.DEFAULT, configFilePath);
        }
        Configuration configuration = provider.readConfig(configFilePath);
        return configuration;
    }

    /**
     * Gets the provider by file extention.
     *
     * @param extention the extention
     * @return the provider by file extention
     */
    private static ConfigProvider<Configuration> getProviderByFileExtention(String extention) {
        ConfigProvider<Configuration> provider = null;
        if (XML_EXTENTION.equals(extention)) {
            provider = new XMLConfigProvider<>(Configuration.class);
        } else if (YML_EXTENTION.equals(extention) || YAML_EXTENTION.equals(extention)) {
            provider = new YAMLConfigProvider<>(Configuration.class);
        } else {
            // Default Provider.
            provider = new YAMLConfigProvider<>(Configuration.class);
        }

        return provider;
    }
    
    static Path getEnvironemtVariableConfigFilePath(){
        final String value = System.getenv("AUDIT4J_CONF_FILE_PATH");
        return Paths.get(value);
    }
    
    static Path getSystemPropertyConfigFilePAth(){
        final String path = System.getProperty("audit4j.conf.file.path");
        return Paths.get(path);
    }
    
    static Path getClasspathConfigPath(){
        return null;
    }
    
}
