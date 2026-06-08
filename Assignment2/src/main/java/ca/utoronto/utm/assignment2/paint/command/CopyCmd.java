package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.PaintPanel;
import ca.utoronto.utm.assignment2.paint.StatusModel;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import java.util.ArrayList;

/**
 * copy action for the paint application
 */
public class CopyCmd implements Command {
    private final PaintModel model;
    private final PaintPanel panel;

    /**
     * constructs a copy command
     * @param model the PaintModel to read from and update
     * @param panel the PaintPanel providing the current drawing shape
     */
    public CopyCmd(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    /**
     * executes the copy operation
     * finalizes the current shape - if any in progress
     * updates model - notifies observers
     * @return
     */
    @Override
    public CommandResult execute() {
        Shape currentShape = this.panel.getCurrentDrawingShape();
        if (currentShape != null && !currentShape.isFinished()) {
            currentShape.finalizeShape(this.model);
        }
        // notify user if successfully copied
        ArrayList<Shape> shapes = model.getShapes();

        if (shapes.isEmpty()) {
            // Handle empty canvas - don't crash
            return  new CommandResult("copy", false, null);
        }

        // Get the last drawn shape
        Shape lastShape = shapes.getLast();

        // Copy the shape to clipboard (overwrite previous)
        ArrayList<Shape> copiedShape = model.getCopiedShape();
        copiedShape.clear(); // Clear previous copy
        copiedShape.add(lastShape.clone());

        // Notify user
        model.update();

        return new  CommandResult("copy", true, lastShape);
    }

}
