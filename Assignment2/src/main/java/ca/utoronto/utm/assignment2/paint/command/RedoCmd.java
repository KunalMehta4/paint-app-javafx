package ca.utoronto.utm.assignment2.paint.command;


import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.shape.AbstractShape;
import ca.utoronto.utm.assignment2.paint.shape.Shape;

/**
 * Redo the last undone action.
 * Read the last shape stored in the undo stack.
 * If the shape was removed, add it back to the canvas.
 * If the shape was moved, reapply the movement.
 * Update the model and return a CommandResult.
 */

public class RedoCmd implements Command {
    private final PaintModel model;

    public RedoCmd(PaintModel model) {
        this.model = model;
    }

    @Override
    public CommandResult execute() {
        if (this.model.getundoStack().isEmpty()) {
            return new CommandResult("redo", false, null);
        }

        Shape redoShape = this.model.getundoStack().pop();

        boolean shapesPresent = this.model.getShapes().contains(redoShape);
        if (shapesPresent) {
            this.model.getShapes().remove(redoShape);
            this.model.pushUndoShape(redoShape);
            this.model.update();
            return new CommandResult("redo", true, null);
        }

        this.model.getShapes().add(redoShape);

        AbstractShape abstractRedoShape = (AbstractShape) redoShape;
        if (abstractRedoShape.revert()){
            this.model.update();
            return new CommandResult("redo move", true, null);
        }

        this.model.update();

        return new  CommandResult("redo", true, redoShape);
    }
}


