package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.PaintPanel;
import ca.utoronto.utm.assignment2.paint.StatusModel;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import java.util.ArrayList;

/**
 * CutCmd represents the cut operation in the paint application
 */
public class CutCmd implements Command {
    private final PaintModel model;
    private final PaintPanel panel;

    /**
     * constructs a new CutCmd
     * @param model the paint model to read from and modify
     * @param panel the panel used to access the current drawing shape
     */
    public CutCmd(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    /**
     * executes the cut operation
     * @return command result with command name (cut), boolean representing whether
     * shape was cut (true if yes, false if no shape to cut)
     */
    @Override
    public CommandResult execute() {
        Shape currentShape = this.panel.getCurrentDrawingShape();
        if (currentShape != null && !currentShape.isFinished()) {
            currentShape.finalizeShape(this.model);
        }

        ArrayList<Shape> shapes = model.getShapes();

        if (shapes.isEmpty()) {
            // Handle empty canvas - don't crash
            return new CommandResult("cut", false, null);
        }

        // Get the last drawn shape
        Shape lastShape = shapes.getLast();

        // Copy the shape to clipboard (overwrite previous)
        ArrayList<Shape> copiedShape = model.getCopiedShape();
        copiedShape.clear(); // Clear previous copy
        copiedShape.add(lastShape.clone());

        // Delete the shape from canvas
        shapes.removeLast();

        model.pushUndoShape(lastShape);

        // clear current shape
        this.panel.setCurrentDrawingShape(null);

        // Notify user
        model.update();

        return new CommandResult("cut", true, null);

    }
}
