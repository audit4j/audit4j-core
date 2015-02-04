package org.audit4j.core.command;

import java.util.Map;

import org.audit4j.core.Registry;
import org.audit4j.core.util.Log;

/**
 * The Class CommandProcessor.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public final class CommandProcessor {

    /** The instance. */
    private static CommandProcessor instance;

    /**
     * Process.
     *
     * @param options the options
     */
    public void process(Map<String, String> options) {
        Log.info("Initializing Commands...");
        System.out.println(options);
        for (Map.Entry<String, String> entry : options.entrySet()) {
            AbstractCommand command = Registry.getCommandByOptionName(entry.getKey());
            if (null != command) {
                command.setOptions(options);
                command.init();
                command.execute();
                command.stop();
            }
            Log.info(entry.getKey() + " Initialized.");
        }
    }

    /**
     * Instantiates a new command processor.
     */
    private CommandProcessor(){
        //Private Constructor.
    }
    
    /**
     * Gets the single instance of CommandProcessor.
     *
     * @return single instance of CommandProcessor
     */
    public static CommandProcessor getInstance() {
        if (null == instance) {
            synchronized (CommandProcessor.class) {
                if (null == instance) {
                    instance = new CommandProcessor();
                }
            }
        }
        return instance;
    }

}
