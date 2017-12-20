/*
 * Copyright (c) 2014-2017 Janith Bandara, This source is a part of
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

package org.audit4j.core.command.impl;

import java.util.List;

import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;

/**
 * The Class ConsoleAuditHandler.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.6.0
 */
public class BatchCommand extends AbstractCommand{

    private int batchSize = 0;
    
    @Override
    public void init() throws InitializationException {
        List<String> options = getOptionsByCommand(getCommand());
        batchSize = Integer.valueOf(options.get(0));
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getCommand() {
        return "-batchSize";
    }

    @Override
    public String getCommandDescription() {
       return "Batch size command. Available options: batch size int.";
    }

    public int getBatchSize() {
        return batchSize;
    }
}
