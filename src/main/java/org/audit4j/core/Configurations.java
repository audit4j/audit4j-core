package org.audit4j.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FilenameUtils;
import org.audit4j.core.exception.ConfigurationException;
import org.audit4j.core.util.AuditUtil;
import org.audit4j.core.util.ClassLoaderUtils;
import org.audit4j.core.util.Log;

/**
 * The Class Configurations.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class Configurations {

    /** The Constant XML_EXTENTION. */
    public static final String XML_EXTENTION = "xml";

    /** The Constant YML_EXTENTION. */
    public static final String YML_EXTENTION = "yml";

    /** The Constant YAML_EXTENTION. */
    public static final String YAML_EXTENTION = "yaml";

    /** The Constant CONFIG_FILE_NAME. */
    public static final String CONFIG_FILE_NAME = "audit4j.conf";

    /** The Constant DEFAULT_CONFIG_FILE_NAME. */
    public static final String DEFAULT_CONFIG_FILE_NAME = "audit4j.conf.yml";

    /** The Constant CONFIG_PROPERTY_EXCEPTION_ID. */
    static final String CONFIG_PROPERTY_EXCEPTION_ID = "CONF_004";

    /** The Constant CONFIG_EXTENTION_NOT_SUPPORTED_EXCEPTION_ID. */
    static final String CONFIG_EXTENTION_NOT_SUPPORTED_EXCEPTION_ID = "CONF_005";
    
    static final String FILE_NOT_FOUND_EXCEPTION_ID = "CONF_006";

    /** The Constant SYSTEM_PROPERTY_CONFIG_VARIABLE_NAME. */
    static final String SYSTEM_PROPERTY_CONFIG_VARIABLE_NAME = "audit4j.conf.file.path";

    /** The Constant ENVIRONMENT_CONFIG_VARIABLE_NAME. */
    static final String ENVIRONMENT_CONFIG_VARIABLE_NAME = "AUDIT4J_CONF_FILE_PATH";

    /**
     *  
     */
    private Configurations() {

    }
    
    /**
     * Load config.
     * 
     * @param configFilePath
     *            the config file path
     * @return the configuration
     * @throws ConfigurationException
     *             the configuration exception
     */
    static Configuration loadConfig(String configFilePath) throws ConfigurationException {
        return loadConfig(resolveConfigFileAsStream(configFilePath));
    }

    /**
     * Load config.
     *
     * @param stream the stream
     * @return the configuration
     * @throws ConfigurationException the configuration exception
     */
    static Configuration loadConfig(ConfigurationStream stream) throws ConfigurationException {
        ConfigProvider<Configuration> provider = getProviderByFileExtention(stream.getExtention());
        return provider.readConfig(stream.getInputStream());
    }

    /**
     * Resolve config path.
     *
     * @param configFilePath the config file path
     * @return the path
     * @throws ConfigurationException the configuration exception
     */
    static ConfigurationStream resolveConfigFileAsStream(String configFilePath) throws ConfigurationException {
        InputStream fileStream;
        String fileExtention;
        
        if (configFilePath != null) {
            if (new File(configFilePath).isDirectory()) {
                String path = scanConfigFile(configFilePath);
                fileStream = getFileAsStream(new File(path));
                fileExtention = FilenameUtils.getExtension(path);
            } else {
                fileStream = getFileAsStream(configFilePath);
                fileExtention = FilenameUtils.getExtension(configFilePath);
            }
        } else if (hasEnvironmentVariable(ENVIRONMENT_CONFIG_VARIABLE_NAME)) {
            fileStream = getFileAsStream(getEnvironemtVariableConfigFilePath());
            fileExtention = FilenameUtils.getExtension(getEnvironemtVariableConfigFilePath().toFile().getName());
        } else if (hasSystemPropertyVariable(SYSTEM_PROPERTY_CONFIG_VARIABLE_NAME)) {
            fileStream = getFileAsStream(getSystemPropertyConfigFilePath());
            fileExtention = FilenameUtils.getExtension(getSystemPropertyConfigFilePath().toFile().getName());
        } else if (getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + YML_EXTENTION) != null) {
            fileStream = getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + YML_EXTENTION);
            fileExtention = YML_EXTENTION;
        } else if (getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + YAML_EXTENTION) != null) {
            fileStream = getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + YAML_EXTENTION);
            fileExtention = YAML_EXTENTION;
        } else if (getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + XML_EXTENTION) != null) {
            fileStream = getClasspathResourceAsStream(CONFIG_FILE_NAME + "." + XML_EXTENTION);
            fileExtention = XML_EXTENTION;
        } else {

            String defaultConfigDir = System.getProperty("user.dir");
            String defaultConfigPath = scanConfigFile(defaultConfigDir);
            fileExtention = FilenameUtils.getExtension(defaultConfigPath);
            fileStream = getFileAsStream(new File(defaultConfigPath));
        }
        ConfigurationStream config = new ConfigurationStream();
        config.setExtention(fileExtention);
        config.setInputStream(fileStream);
        return config;
    }

    /**
     * Scan config file.
     *
     * @param dirPath the dir path
     * @return the string
     * @throws ConfigurationException the configuration exception
     */
    static String scanConfigFile(String dirPath) throws ConfigurationException {

        String filePath = dirPath + File.separator + CONFIG_FILE_NAME + ".";

        String fullFilePath;
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
            generateConfigFile(fullFilePath);
        }
        return fullFilePath;
    }

    /**
     * Generate config file.
     *
     * @param configFilePath the config file path
     * @throws ConfigurationException the configuration exception
     */
    static void generateConfigFile(String configFilePath) throws ConfigurationException {
        String fileExtention = FilenameUtils.getExtension(configFilePath);
        ConfigProvider<Configuration> provider = getProviderByFileExtention(fileExtention);
        if (!AuditUtil.isFileExists(configFilePath)) {
            provider.generateConfig(Configuration.DEFAULT, configFilePath);
        }
    }

    /**
     * Gets the provider by file extention.
     * 
     * @param extention
     *            the extention
     * @return the provider by file extention
     * @throws ConfigurationException 
     */
    private static ConfigProvider<Configuration> getProviderByFileExtention(String extention) throws ConfigurationException {
        ConfigProvider<Configuration> provider;
        if (XML_EXTENTION.equals(extention)) {
            provider = new XMLConfigProvider<>(Configuration.class);
        } else if (YML_EXTENTION.equals(extention) || YAML_EXTENTION.equals(extention)) {
            provider = new YAMLConfigProvider<>(Configuration.class);
        } else {
            throw new ConfigurationException("Given file type is not supported.",
                    CONFIG_EXTENTION_NOT_SUPPORTED_EXCEPTION_ID);
        }

        return provider;
    }

    /**
     * Gets the environemt variable config file path.
     *
     * @return the environemt variable config file path
     */
    static Path getEnvironemtVariableConfigFilePath() {
        final String value = System.getenv(ENVIRONMENT_CONFIG_VARIABLE_NAME);
        return Paths.get(value);
    }

    /**
     * Checks for environment variable.
     *
     * @param variable the variable
     * @return true, if successful
     */
    static boolean hasEnvironmentVariable(String variable) {
        final String value = System.getenv(variable);
        if (value != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets the system property config file path.
     *
     * @return the system property config file path
     */
    static Path getSystemPropertyConfigFilePath() {
        final String path = System.getProperty(SYSTEM_PROPERTY_CONFIG_VARIABLE_NAME);
        return Paths.get(path);
    }

    /**
     * Checks for system property variable.
     *
     * @param variable the variable
     * @return true, if successful
     */
    static boolean hasSystemPropertyVariable(String variable) {
        final String path = System.getProperty(variable);
        if (path != null) {
            return true;
        }
        return false;
    }

    /**
     * Gets the file as stream.
     * 
     * @param resourceFile
     *            the resource file
     * @return the file as stream
     * @throws ConfigurationException 
     */
    static InputStream getFileAsStream(String resourceFile) throws ConfigurationException {
        return getFileAsStream(new File(resourceFile));
    }

    /**
     * Gets the file as stream.
     * 
     * @param resourceFile
     *            the resource file
     * @return the file as stream
     * @throws ConfigurationException 
     */
    static InputStream getFileAsStream(Path resourceFile) throws ConfigurationException {
        return getFileAsStream(resourceFile.toFile());
    }

    /**
     * Gets the file as stream.
     * 
     * @param resourceFile
     *            the resource file
     * @return the file as stream
     * @throws ConfigurationException 
     */
    static InputStream getFileAsStream(File resourceFile) throws ConfigurationException {
        try {
            return new FileInputStream(resourceFile);
        } catch (FileNotFoundException e) {
            Log.error("File Resource could not be resolved. Given Resource:" + resourceFile, e);
            throw new ConfigurationException("File Resource could not be resolve", FILE_NOT_FOUND_EXCEPTION_ID,e);
        }
    }

    /**
     * Gets the classpath config path.
     *
     * @param resourceName the resource name
     * @return the classpath config path
     */
    static InputStream getClasspathResourceAsStream(String resourceName) {
        return ClassLoaderUtils.getClassLoader(Configurations.class).getResourceAsStream(resourceName);
    }
    
    /**
     * The Class ConfigurationStream.
     *
     */
    public static class ConfigurationStream {
        
        /** The input stream. */
        private InputStream inputStream;

        /** The extention. */
        private String extention;

        /**
         * Gets the input stream.
         *
         * @return the input stream
         */
        public InputStream getInputStream() {
            return inputStream;
        }

        /**
         * Sets the input stream.
         *
         * @param inputStream the new input stream
         */
        public void setInputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        /**
         * Gets the extention.
         *
         * @return the extention
         */
        public String getExtention() {
            return extention;
        }

        /**
         * Sets the extention.
         *
         * @param extention the new extention
         */
        public void setExtention(String extention) {
            this.extention = extention;
        }
    }
}
