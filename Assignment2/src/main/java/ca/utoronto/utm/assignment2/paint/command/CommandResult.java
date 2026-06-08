package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.shape.Shape;

/**
 * Result of a command after its execution
 */
public class CommandResult {
    public final String commandName;
    public final boolean success;
    public final Shape subject;

    /**
     * New instance of a CommandResult
     * @param commandName name
     * @param success true or false
     * @param subject shape affected
     */
    public CommandResult(String commandName, boolean success, Shape subject) {
        this.commandName = commandName;
        this.success = success;
        this.subject = subject;
    }
}
