package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.PaintPanel;
import ca.utoronto.utm.assignment2.paint.StatusModel;
import ca.utoronto.utm.assignment2.paint.shape.Shape;

/**
 * Command that creates a new blank canvas
 * clears all shapes from the paint model
 * finalises any incomplete shape before clearing
 */
public class NewCmd implements Command {
    private final PaintModel model;
    private final PaintPanel panel;

    /**
     * constructs a NewCmd with a specified model and panel
     * @param model the PaintModel to clear shapes from
     * @param panel the PainPanel to reset the current drawing shape
     */
    public NewCmd(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    /**
     * executes the new command by finalizing any shape still in progress and
     * clears current drawing shape, removes all shapes from model
     * updates view
     * @return a CommandResult which indicates success with 'new'
     */

    @Override
    public CommandResult execute() {
        Shape currentShape = this.panel.getCurrentDrawingShape();
        if (currentShape != null && !currentShape.isFinished()) {
            currentShape.finalizeShape(this.model);
        }

        this.panel.setCurrentDrawingShape(null);

        this.model.getShapes().clear();

        // Update the view
        this.model.update();
        return new CommandResult("new", true, null);
    }
}
