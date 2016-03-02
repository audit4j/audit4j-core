package org.audit4j.core.command.impl;

import java.util.List;

import org.audit4j.core.codeGen.AuditCodeGenerator;
import org.audit4j.core.codeGen.CodeGenException;
import org.audit4j.core.command.AbstractCommand;
import org.audit4j.core.exception.InitializationException;

/**
 * The Class MetadataCommand.
 * 
 * @author <a href="mailto:janith3000@gmail.com">Janith Bandara</a>
 * 
 * @since 2.5.0
 */
public class CodeGenCommand extends AbstractCommand {

    AuditCodeGenerator generator;

    @Override
    public void init() throws InitializationException {
        List<String> options = getOptionsByCommand(getCommand());
        if (options.contains("true")) {
            generator = new AuditCodeGenerator();
            try {
                generator.generate();
            } catch (CodeGenException e) {
                throw new InitializationException("Unable to generate codes", e);
            }
        }
    }

    @Override
    public void stop() {
        generator.flush();
    }

    @Override
    public void execute() {

    }

    @Override
    public String getCommand() {
        return "-codeGen";
    }

    @Override
    public String getCommandDescription() {
        return "Code genearte based on annotations.";
    }

}
