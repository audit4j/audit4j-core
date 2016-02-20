/*
 * Copyright (c) 2014-2016 Janith Bandara, This source is a part of
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
 * The Class MetadataCommand.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.4.0
 */
public class MetadataCommand extends AbstractCommand {

    /** The async. */
    private boolean async = false;

    /** The Constant ASYNC_OPTION. */
    static final String ASYNC_OPTION = "async";

    /** The Constant SYNC_OPTION. */
    static final String SYNC_OPTION = "sync";

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#init()
     *
     */
    @Override
    public void init() throws InitializationException {
        List<String> options = getOptionsByCommand(getCommand());
        options.contains(ASYNC_OPTION);
        async = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.Initializable#stop()
     *
     */
    @Override
    public void stop() {
        // Nothing to stop
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.command.AbstractCommand#execute()
     *
     */
    @Override
    public void execute() {
        // Nothing to execute

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.command.AbstractCommand#getCommand()
     *
     */
    @Override
    public String getCommand() {
        return "-metadata";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.audit4j.core.command.AbstractCommand#getCommandDescription()
     *
     */
    @Override
    public String getCommandDescription() {
        return "Metadata tuning commands. Available options: async, sync";
    }

    /**
     * Checks if is async.
     *
     * @return true, if is async
     */
    public boolean isAsync() {
        return async;
    }
    

}
