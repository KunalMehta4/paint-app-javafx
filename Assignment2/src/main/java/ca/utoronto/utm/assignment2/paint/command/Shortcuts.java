package ca.utoronto.utm.assignment2.paint.command;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Handle keyboard shortcuts for the paint application.
 * Looks for key presses with the Control key held down.
 * Map common shortcuts (Ctrl+C, Ctrl+X, Ctrl+V, Ctrl+Z, Ctrl+Y)
 * to their corresponding commands using the CommandHandler.
 */

public class Shortcuts {
    private  final CommandHandler commandHandler;

    public Shortcuts(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }


    public void handleKeyPress(KeyEvent event) {
        boolean isControlDown = event.isControlDown();

        KeyCode keyCode = event.getCode();

        if (!isControlDown) {
            return;
        }

        switch (keyCode) {
            case C:
                commandHandler.handleCommand("Copy");
                event.consume();
                break;

            case X:
                commandHandler.handleCommand("Cut");
                event.consume();
                break;

            case V:
                commandHandler.handleCommand("Paste");
                event.consume();
                break;

            case Z:
                commandHandler.handleCommand("Undo");
                event.consume();
                break;

            case Y:
                commandHandler.handleCommand("Redo");
                event.consume();
                break;

            default:
                break;
        }
    }
}
