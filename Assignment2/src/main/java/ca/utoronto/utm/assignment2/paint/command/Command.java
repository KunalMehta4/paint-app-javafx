package ca.utoronto.utm.assignment2.paint.command;

/**
 * Base contract for all command
 * - must provide a way to run it
 */
public interface Command {
    CommandResult execute();
}
