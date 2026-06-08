package ca.utoronto.utm.assignment2.paint.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Store the name and an instance of a command
 */
public class CommandHandler extends Observable {
    // A map to store command prototypes or instances.
    private final Map<String, Command> commandMap = new HashMap<>();

    /**
     * Map command name to a command and execute if it is not null
     * @param commandName name
     */
    public void handleCommand(String commandName) {
        Command command = this.commandMap.get(commandName);
        if (command != null) {
            CommandResult result = command.execute();

            this.setChanged();
            this.notifyObservers(result);
        } else {
            System.out.println("Unknown command: " + commandName);
        }
    }

    /**
     * Store the command name and command in a dict (map)
     * @param commandName name
     * @param command reference to the command
     */
    public void registerCommand(String commandName, Command command) {
        this.commandMap.put(commandName, command);
    }
}