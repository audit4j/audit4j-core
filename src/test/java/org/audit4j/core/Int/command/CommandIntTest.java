package org.audit4j.core.Int.command;

import java.util.HashMap;
import java.util.Map;

import org.audit4j.core.PreConfigurationContext;
import org.audit4j.core.command.CommandProcessor;
import org.audit4j.core.command.impl.MetadataCommand;
import org.junit.Assert;
import org.junit.Test;

public class CommandIntTest {

    @Test
    public void testMetadataCommand(){
        CommandProcessor processor = CommandProcessor.getInstance();
        Map<String, String> commands = new HashMap<String, String>();
        commands.put("-metadata", "async");
        
        processor.process(commands);
        
        MetadataCommand command =  (MetadataCommand) PreConfigurationContext.getCommandByName("-metadata");
        Assert.assertTrue(command.isAsync());
    }
}
