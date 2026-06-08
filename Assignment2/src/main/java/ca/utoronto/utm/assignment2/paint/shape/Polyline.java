package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.input.MouseEvent;

/**
 * Represented as a set of mouse clicks
 */
public class Polyline extends Squiggle {
    public Polyline() {
        super();
    }

    /**
     * add new point to polyline path
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMousePress(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            super.handleMousePress(mouseEvent, model);
            return;
        }
        this.getPoints().add(new Point(mouseEvent.getX(), mouseEvent.getY()));
        model.update();
    }

    /**
     * IF drawing, do nothing
     * If moving, call parents' move logic
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMouseDrag(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            super.handleMouseDrag(mouseEvent, model);
        }
    }

    /**
     * If drawing, do nothing
     * If moving, finalize and add to model
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMouseRelease(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            this.setMoveable(false);
            model.addShape(this);
            model.update();
        }
    }

    /**
     * add to model if there exists at least two points. called only user switching drawing modes.
     * @param model reference to paint model
     */
    @Override
    public void finalizeShape(PaintModel model) {
        if (getPoints().size() > 1 && !isFinished()) {
            model.addShape(this);
            System.out.println("Added Polyline");
            this.setFinished(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createInstance() {
        return new Polyline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape clone() {
        Polyline copy = new Polyline();

        // Deep copy all points from the ArrayList
        for (Point p : this.getPoints()) {
            copy.getPoints().add(new Point(p.x, p.y));
        }

        // Copy state
        copy.setFinished(true);

        // Copy properties from parent class (Squiggle -> AbstractStrokeShape)
        copy.setStrokeColor(this.getStrokeColor());
        copy.setStrokeWidth(this.getStrokeWidth());

        copy.setMoveable(true);

        return copy;
    }
}