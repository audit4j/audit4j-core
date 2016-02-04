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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.audit4j.core.CoreConstants;
import org.audit4j.core.Initializable;

/**
 * The Class AbstractCommand.
 * 
 * This abstration can be used to add commands options in to the platform.
 *
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.3.0
 */
public abstract class AbstractCommand implements Initializable {

    /** The options. */
    private Map<String, String> commands;

    /**
     * Gets the options.
     *
     * @return the options
     */
    public Map<String, String> getCommands() {
        return commands;
    }

    /**
     * Sets the options.
     *
     * @param commands the options
     */
    void setCommands(Map<String, String> commands) {
        this.commands = commands;
    }
    
    /**
     * Gets the option by command.
     *
     * @param command the command
     * @return the option by command
     */
    protected String getOptionByCommand(String command){
    	return commands.get(command);
    }
    
    /**
     * Gets the options by command.
     *
     * @param command the command
     * @return the options by command
     */
    protected List<String> getOptionsByCommand(String command){
        String rawOption = commands.get(command);
        String[] options = rawOption.split(CoreConstants.COMMA);
        return Arrays.asList(options);
    }

    /**
     * Execute.
     */
    public abstract void execute();
    
    /**
     * Gets the command.
     *
     * @return the command
     */
    public abstract String getCommand();
    
    /**
     * Gets the command description.
     *
     * @return the command description
     */
    public abstract String getCommandDescription();
}
