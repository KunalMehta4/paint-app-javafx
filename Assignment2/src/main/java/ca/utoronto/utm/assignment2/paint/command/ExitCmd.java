package ca.utoronto.utm.assignment2.paint.command;
import javafx.application.Platform;

public class ExitCmd implements Command {

    /**
     * Terminate program; delete everything,
     * return: CommandResult
     */
    @Override
    public CommandResult execute() {
        Platform.exit();
        return new CommandResult("exit", true, null);
    }
}
