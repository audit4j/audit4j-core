package org.audit4j.core.command;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CommandProcessorTest {

    @Test
    public void testProcess() {
        CommandProcessor processor = CommandProcessor.getInstance();
        Map<String, String> commands = new HashMap<String, String>();
        commands.put("-metadata", "sync");

        processor.process(commands);
    }

    @Test
    public void testProcessStr() {
        CommandProcessor processor = CommandProcessor.getInstance();
        processor.process("-codeGen=true");
    }
}
