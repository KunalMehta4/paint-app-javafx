package ca.utoronto.utm.assignment2.paint;
import ca.utoronto.utm.assignment2.paint.shape.AbstractFillableShape;
import ca.utoronto.utm.assignment2.paint.shape.AbstractShape;
import ca.utoronto.utm.assignment2.paint.shape.Point;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.Observable;
import java.util.Observer;

/**
 * Main drawing panel for the paint application.
 * Handle mouse events for creating, moving, and finishing shapes.
 * Store the current shape being drawn and forward updates to the PaintModel.
 * Repaint the canvas whenever the model changes.
 */

public class PaintPanel extends Canvas implements Observer {
    private Mode mode;
    private PaintModel model;
    private Shape currentDrawingShape;
    private Mode modeHandler;
    private AttributesModel attributesModel;


    public PaintPanel(PaintModel model, Mode modeHandler, AttributesModel attributesModel) {
        super(500, 500);

        this.model = model;
        this.modeHandler = modeHandler;
        this.attributesModel = attributesModel;
        this.modeHandler.addObserver(this);
        this.model.addObserver(this);

        this.setOnMousePressed(this::handleMousePress);
        this.setOnMouseDragged(this::handleMouseDrag);
        this.setOnMouseReleased(this::handleMouseRelease);
    }

    public Shape getCurrentDrawingShape() {return this.currentDrawingShape;}

    private void handleMousePress(MouseEvent event) {
        if ("Move".equals(modeHandler.getCurrentMode()) && currentDrawingShape instanceof AbstractShape) {
            AbstractShape targetShape = (AbstractShape) currentDrawingShape;
            targetShape.setPrevPosition(new Point(event.getX(), event.getY()));
            targetShape.setCurrentPosition(new Point(event.getX(), event.getY()));
        }

        if (currentDrawingShape != null && currentDrawingShape.isFinished() && !"Move".equals(modeHandler.getCurrentMode())) {
            currentDrawingShape.finalizeShape(this.model);
            currentDrawingShape = null;
        }

        // if no shape in progress, create new one
        if (currentDrawingShape == null) {
            Shape prototype = modeHandler.getCurrentPrototype();
            if (prototype != null) {
                currentDrawingShape = prototype.createInstance();

                // Apply current attributes to the new shape (all shapes are AbstractShape)
                if (currentDrawingShape instanceof AbstractShape) {
                    AbstractShape abstractShape = (AbstractShape) currentDrawingShape;
                    abstractShape.setStrokeWidth(attributesModel.getStrokeWidth());
                    abstractShape.setStrokeColor(attributesModel.getStrokeColor());
                }

                // Apply fill color if it's a fillable shape
                if (currentDrawingShape instanceof AbstractFillableShape) {
                    ((AbstractFillableShape) currentDrawingShape).setFillColor(attributesModel.getFillColor());
                }
            }
        }

        if (currentDrawingShape != null) {
            currentDrawingShape.handleMousePress(event, this.model);
        }
    }

    private void handleMouseDrag(MouseEvent event) {
        if (this.currentDrawingShape != null) {
            this.currentDrawingShape.handleMouseDrag(event, this.model);
        }
    }

    private void handleMouseRelease(MouseEvent event) {
        if ("Move".equals(modeHandler.getCurrentMode()) && currentDrawingShape instanceof AbstractShape) {
            AbstractShape targetShape = (AbstractShape) currentDrawingShape;
            targetShape.setCurrentPosition(new Point(event.getX(), event.getY()));
        }

        if (this.currentDrawingShape == null) {
            return;
        }

        // case 1: finalize moved shape
        if ("Move".equals(modeHandler.getCurrentMode())) {
            this.currentDrawingShape.handleMouseRelease(event, this.model);

            // Clean up
            this.currentDrawingShape = null;
            modeHandler.revertToPreviousMode();
        }
        // case 2: interact with a shape being drawn (does not mean that it is finished on mouse released)
        else if (!this.currentDrawingShape.isFinished()) {
            this.currentDrawingShape.handleMouseRelease(event, this.model);

            // ask the shape if the mouse release finalized the shape
            if (this.currentDrawingShape.isFinished()) {
                this.currentDrawingShape = null;
            }
        }
    }

    // the following is needed for the cut command!
    public void setCurrentDrawingShape(Shape shape) {
        this.currentDrawingShape = shape;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == this.modeHandler) {
            if (currentDrawingShape != null) {
                // Case 1: The pending shape was a moveable, pasted shape.
                if (currentDrawingShape.isMoveable()) {
                    currentDrawingShape.setMoveable(false);
                    currentDrawingShape.setFinished(true);
                    this.model.addShape(currentDrawingShape); // Save it!
                }
                // Case 2: The pending shape was an in-progress Polyline or shapes that relies on mode switch to finalize.
                else if (!currentDrawingShape.isFinished()) {
                    currentDrawingShape.finalizeShape(this.model);
                }
            }
            // After cleanup, always reset the current shape.
            currentDrawingShape = null;

            // If the new mode is "Move", grab the shape to be manipulated.
            if ("Move".equals(this.modeHandler.getCurrentMode())) {
                this.currentDrawingShape = this.modeHandler.getShapeToManipulate();
            }
        }

        GraphicsContext g = this.getGraphicsContext2D();
        g.clearRect(0, 0, this.getWidth(), this.getHeight());

        for (Shape shape : this.model.getShapes()) {
            shape.draw(g);
        }

        if (currentDrawingShape != null) {
            currentDrawingShape.draw(g);
        }
    }
}
