package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


/**
 * A bounding box shape is a fillable shape whose perimeter can be drawn inside a rectangular box
 */
public abstract class AbstractBoundingBoxShape extends AbstractFillableShape{
    protected Point topLeft;
    protected double width;
    protected double height;
    protected Point start;

    // standard setters getters
    public Point getTopLeft() { return topLeft; }
    public void setTopLeft(Point topLeft) { this.topLeft = topLeft; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public Point getStart() { return start; }
    public void setStart(Point start) { this.start = start; }

    /**
     * Do nothing if shape is moveable
     * define two points that make up the bounding box: start, and topleft
     * @param mouseEvent user input
     * @param model holds a reference to all created shapes
     */
    @Override
    public void handleMousePress(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) return;
        this.start = new Point(mouseEvent.getX(), mouseEvent.getY());
        this.topLeft = new Point(mouseEvent.getX(), mouseEvent.getY());
        this.width = 0;
        this.height = 0;
    }

    /**
     * If moveable; reposition topLeft
     * Otherwise, recalculate bounding box for shape
     * @param mouseEvent user input
     * @param model reference to paintmodel
     */
    @Override
    public void handleMouseDrag(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            this.topLeft.x = mouseEvent.getX();
            this.topLeft.y = mouseEvent.getY();
            model.update();
        } else {
            if (this.start == null) return;

            double x0 = this.start.x;
            double y0 = this.start.y;
            double x1 = mouseEvent.getX();
            double y1 = mouseEvent.getY();

            this.topLeft.x = Math.min(x0, x1);
            this.topLeft.y = Math.min(y0, y1);
            this.width = Math.abs(x0 - x1);
            this.height = Math.abs(y0 - y1);
            model.update();
        }
    }

    /**
     * Finalized shape's state. If shape was moved, add to model. If shape was drawn, marked as finished and add to model.
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMouseRelease(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            this.setMoveable(false);
            this.setFinished(true);
            model.addShape(this);
            model.update();
        } else {
            if (!this.isFinished()) {
                handleMouseDrag(mouseEvent, model); // Perform one final update
                this.setFinished(true);
                model.addShape(this);
                model.update();
            }
        }
    }

    /**
     * Move shape in direction
     * @param dx change of x
     * @param dy change of y
     */
    @Override
    public void translate(double dx, double dy) {
        if (this.topLeft != null) {
            this.topLeft.x += dx;
            this.topLeft.y += dy;
        }
        if (this.start != null) {
            this.start.x += dx;
            this.start.y += dy;
        }
    }

    @Override
    public void finalizeShape(PaintModel model) {
        // Nothing to do for these simple shapes
    }
}
