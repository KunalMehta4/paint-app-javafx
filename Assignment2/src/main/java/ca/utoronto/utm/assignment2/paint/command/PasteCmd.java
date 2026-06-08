package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.Mode;
import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.StatusModel;
import ca.utoronto.utm.assignment2.paint.shape.Shape;

/**
 * Read the last item in PaintModel.copiedShape
 * Representation invariant: copiedShape.size() <= 1
 * make a deep copy of the shape from copiedShape and add it to PaintModel.shapes
 * Allow user to move copied shape around and release mouse to snap it in
 * return CommandResult
 */
public class PasteCmd implements Command {
    PaintModel model;
    Mode modeHandler;

    public PasteCmd(PaintModel model, Mode modeHandler) {
        this.model = model;
        this.modeHandler = modeHandler;
    }

    @Override
    public CommandResult execute() {
        if (this.model.getCopiedShape().size() != 1) {
            // clipboard empty or not the proper size
            return new CommandResult("Paste", false, null);
        }

        Shape pastedShape = this.model.getCopiedShape().getLast().clone();
        pastedShape.translate(5, 5);

        this.model.addShape(pastedShape);
        this.model.update();

        this.modeHandler.setShapeToManipulate(pastedShape);
        this.modeHandler.setMode("Move");

        return new CommandResult("Paste", true, pastedShape);
    }

}
