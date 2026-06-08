package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.PaintPanel;
import ca.utoronto.utm.assignment2.paint.shape.AbstractShape;
import ca.utoronto.utm.assignment2.paint.shape.Shape;

/**
 * Undo the most recent action.
 * If a shape was moved, revert it to its previous position.
 * If a shape was added, remove it from the canvas.
 * If a shape was cut, restore it from the undo stack.
 * Update the model and return a CommandResult.
 */

public class UndoCmd implements Command {
    private final PaintModel model;
    private final PaintPanel panel;

    public UndoCmd(PaintModel model, PaintPanel panel) {
        this.model = model;
        this.panel = panel;
    }

    @Override
    public CommandResult execute() {

        if (this.model.getShapes().isEmpty() && this.model.getundoStack().isEmpty()) {
            return new CommandResult("undo", false, null);
        }

        if (this.model.getShapes().isEmpty() && !this.model.getundoStack().isEmpty()) {
            Shape cut = this.model.getundoStack().pop();
            this.model.getShapes().add(cut);
            this.panel.setCurrentDrawingShape(null);
            this.model.update();
            return new CommandResult("undo", true, null);
        }

        Shape lastShape = this.model.getShapes().getLast();
        AbstractShape targetShape = (AbstractShape) lastShape;

        if (targetShape.revert()) {
            this.model.getShapes().remove(lastShape);
            this.panel.setCurrentDrawingShape(null);
            this.model.update();
            this.model.pushUndoShape(lastShape);
            return new CommandResult("undo move", true, lastShape);
        }
        targetShape.clearMoveHistory();

        int last = this.model.getShapes().size() - 1;
        Shape removed = this.model.getShapes().remove(last);

        this.panel.setCurrentDrawingShape(null);
        this.model.update();
        this.model.pushUndoShape(removed);

        return new CommandResult("undo", true, removed);
    }
}