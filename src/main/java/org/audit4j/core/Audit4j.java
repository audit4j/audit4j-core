package org.audit4j.core;

/**
 * The Class Audit4j.
 */
public final class Audit4j {

    /**
     * This method allows to external plugins can inject the configurations.
     * Since the security reasons, this allows to create one time configuration
     * setting to Audit4j.
     * 
     * @param configuration
     *            the configuration
     * 
     */
    public static void start(Configuration configuration) {
        Context.setConfig(configuration);
        Context.init();
    }

    /**
     * Initialize audit4j with external configuration file.
     * 
     * This method allows to external plugins can inject the configurations.
     * Since the security reasons, this allows to create one time configuration
     * setting to Audit4j.
     * 
     * @param configFilePath
     *            the config file path
     */
    public static void start(String configFilePath) {
        Context.setConfigFilePath(configFilePath);
        Context.init();
    }

    /**
     * Initialize the audit4j.
     */
    public static void start() {
        Context.init();
    }

    /**
     * Shutdown.
     */
    public static void shutdown() {
        Context.stop();
    }

    /**
     * Enable.
     */
    public static void enable() {
        Context.enable();
    }

    /**
     * Disable.
     */
    public static void disable() {
        Context.disable();
    }
}
