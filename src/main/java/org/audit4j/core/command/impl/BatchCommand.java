package org.audit4j.core.command.impl;

import java.util.List;

import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;

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
