package org.audit4j.core.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.audit4j.core.command.impl.CodeGenCommand;
import org.audit4j.core.command.impl.MetadataCommand;
import org.audit4j.core.command.impl.ObjectSerializerCommand;
import org.audit4j.core.command.impl.ScanAnnotatedCommand;

public class CommandRegistry {

    /** The Constant commands. */
    private static final Map<String, AbstractCommand> commands = new HashMap<String, AbstractCommand>();

    /** The Constant options. */
    private static final List<String> availableCommands = new ArrayList<String>();

    static {
        ScanAnnotatedCommand scanAnnotated = new ScanAnnotatedCommand();
        availableCommands.add(scanAnnotated.getCommand());
        commands.put(scanAnnotated.getCommand(), scanAnnotated);

        MetadataCommand metadataCommand = new MetadataCommand();
        availableCommands.add(metadataCommand.getCommand());
        commands.put(metadataCommand.getCommand(), metadataCommand);
        
        ObjectSerializerCommand serializerCommand = new ObjectSerializerCommand();
        availableCommands.add(serializerCommand.getCommand());
        commands.put(serializerCommand.getCommand(), serializerCommand);
        
        CodeGenCommand codeGenCommand = new CodeGenCommand();
        availableCommands.add(codeGenCommand.getCommand());
        commands.put(codeGenCommand.getCommand(), codeGenCommand);
    }
    
    /**
     * Gets the command by option name.
     * 
     * @param commandName
     *            the option name
     * @return the command by option name
     */
    public static AbstractCommand getCommandByName(String commandName) {
        return commands.get(commandName);
    }
    
    /**
     * Gets the options.
     * 
     * @return the options
     */
    public static List<String> getAvailableCommands() {
        return availableCommands;
    }

    /**
     * Gets the commands.
     * 
     * @return the commands
     */
    public static Map<String, AbstractCommand> getCommands() {
        return commands;
    }
}
