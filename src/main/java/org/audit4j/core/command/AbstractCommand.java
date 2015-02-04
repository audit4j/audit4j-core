package org.audit4j.core.command;

import java.util.Map;

import org.audit4j.core.Initializable;

/**
 * The Class AbstractCommand.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public abstract class AbstractCommand implements Initializable {

    /** The options. */
    private Map<String, String> options;

    /**
     * Gets the options.
     *
     * @return the options
     */
    public Map<String, String> getOptions() {
        return options;
    }

    /**
     * Sets the options.
     *
     * @param options the options
     */
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    /**
     * Execute.
     */
    public abstract void execute();
}
