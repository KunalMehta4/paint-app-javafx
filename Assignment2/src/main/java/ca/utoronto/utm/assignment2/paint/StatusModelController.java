package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.Mode;
import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.StatusModel;
import ca.utoronto.utm.assignment2.paint.command.CommandHandler;
import ca.utoronto.utm.assignment2.paint.command.CommandResult;
import ca.utoronto.utm.assignment2.paint.command.PasteCmd;
import ca.utoronto.utm.assignment2.paint.shape.AbstractFillableShape;
import ca.utoronto.utm.assignment2.paint.shape.AbstractShape;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

/**
 * Controller for updating the status display in the paint application.
 * Listen for changes in the StatusModel and update the Status View.
 * Show messages that reflect the current tool, mode, or action.
 */

public class StatusModelController implements Observer {
    private final StatusModel statusModel;

    public StatusModelController (PaintModel paintModel, Mode modeHandler, StatusModel statusModel, CommandHandler commandHandler) {
        this.statusModel = statusModel;

        // The controller registers itself to listen for changes.
        paintModel.addObserver(this);
        modeHandler.addObserver(this);
        commandHandler.addObserver(this);
    }

    /**
     * Handle the current message of status bar.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Mode mode) {
            // Handle mode switching feedback
            String currentMode = mode.getCurrentMode();
            if (currentMode.isEmpty()) {
                this.statusModel.setMessage("Ready");
            } else {
                this.statusModel.setMessage("Current tool: " + currentMode);
            }

        } else if (o instanceof PaintModel) {
            // Handle shape creation feedback
            PaintModel model = (PaintModel) o;
            if (!model.getShapes().isEmpty()) {
                Shape lastShape = model.getShapes().getLast();
                String shapeName = lastShape.getClass().getSimpleName();
                String colorInfo = getColorInfo(lastShape);
                this.statusModel.setMessage("Created a new " + shapeName + colorInfo);
            }
        } else if (o instanceof CommandHandler) {
            // Handle command execution
            CommandResult result = (CommandResult) arg;
            if (result.success) {
                if (result.subject != null) {
                    this.statusModel.setMessage(result.commandName + ": " + result.subject.getClass().getSimpleName());
                } else {
                    this.statusModel.setMessage(result.commandName + " success.");
                }
            } else {
                this.statusModel.setMessage(result.commandName + " failed.");
            }
        }
    }

    /**
     * Get color information for a shape
     */
    private String getColorInfo(Shape shape) {
        StringBuilder info = new StringBuilder();

        // Cast to AbstractShape to access stroke properties
        if (shape instanceof AbstractShape) {
            AbstractShape abstractShape =  (AbstractShape) shape;

            // Get stroke color
            String strokeColor = getColorName(abstractShape.getStrokeColor());
            if (abstractShape.getStrokeWidth() > 0) {
                info.append(" with ").append(strokeColor).append(" stroke");
            }
        }

        // Get fill color for fillable shapes
        if (shape instanceof AbstractFillableShape) {
            AbstractFillableShape fillableShape = (AbstractFillableShape) shape;
            String fillColor = getColorName(fillableShape.getFillColor());

            if (fillableShape.getFillColor() != Color.TRANSPARENT) {
                if (info.length() > 0) {
                    info.append(" and ");
                } else {
                    info.append(" with ");
                }
                info.append(fillColor).append(" fill");
            }
        }

        return info.toString();
    }

    /**
     * Convert Color to readable name
     */
    private String getColorName(javafx.scene.paint.Paint paint) {
        if (!(paint instanceof Color)) return "custom color";

        Color color = (Color) paint;

        if (color.equals(Color.TRANSPARENT)) return "transparent";
        if (color.equals(Color.BLACK)) return "black";
        if (color.equals(Color.WHITE)) return "white";
        if (color.equals(Color.RED)) return "red";
        if (color.equals(Color.GREEN)) return "green";
        if (color.equals(Color.BLUE)) return "blue";
        if (color.equals(Color.YELLOW)) return "yellow";
        if (color.equals(Color.ORANGE)) return "orange";
        if (color.equals(Color.PURPLE)) return "purple";
        if (color.equals(Color.PINK)) return "pink";
        if (color.equals(Color.GRAY)) return "gray";
        if (color.equals(Color.CYAN)) return "cyan";
        if (color.equals(Color.MAGENTA)) return "magenta";

        // For custom colors, show RGB values!
        return String.format("RGB(%.0f,%.0f,%.0f)",
                color.getRed() * 255,
                color.getGreen() * 255,
                color.getBlue() * 255);
    }
}

