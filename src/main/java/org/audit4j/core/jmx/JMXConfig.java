package org.audit4j.core.jmx;

/**
 * The Class JMXConfig.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class JMXConfig {

    /** The context name. */
    private String contextName = "default";

    /**
     * Gets the context name.
     *
     * @return the context name
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Sets the context name.
     *
     * @param contextName the new context name
     */
    public void setContextName(String contextName) {
        this.contextName = contextName;
    }
}
