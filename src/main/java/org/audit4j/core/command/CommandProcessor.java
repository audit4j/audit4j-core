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

package org.audit4j.core.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.audit4j.core.CoreConstants;
import org.audit4j.core.ErrorGuide;
import org.audit4j.core.PreConfigurationContext;
import org.audit4j.core.exception.InitializationException;
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
     * Instantiates a new command processor.
     */
    private CommandProcessor(){
        //Private Constructor.
    }
    
    /**
     * Process.
     *
     * @param options the options
     */
    public void process(Map<String, String> options) {
        Log.info("Initializing Commands...");
        for (Map.Entry<String, String> entry : options.entrySet()) {
            AbstractCommand command = PreConfigurationContext.getCommandByName(entry.getKey());
            if (null != command) {
                command.setCommands(options);
                try {
                    command.init();
                } catch (InitializationException e) {
                    Log.error("There is a problem in the option you configured: ", entry.getKey(),
                            ErrorGuide.getGuide(ErrorGuide.OPTION_ERROR));
                }
                command.execute();
                command.stop();
            }
            Log.info(entry.getKey() + " Command Initialized.");
        }
    }

    public void process(String commandText) {
        if (commandText == null || commandText.isEmpty()) {
            return;
        }
        Map<String, String> commands = new HashMap<String, String>();
        String[] args = StringUtils.split(commandText);
        for (String arg : args) {
            String[] command = StringUtils.split(arg, CoreConstants.EQ_CHAR);
            if (!PreConfigurationContext.getAvailableCommands().contains(command[0])) {
                Log.warn("Invalid command: ", command[0], " Please check your configurations. ",
                        ErrorGuide.getGuide(ErrorGuide.INVALID_COMMAND));
            }
            commands.put(command[0], command[1]);
        }
        
        process(commands);
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
