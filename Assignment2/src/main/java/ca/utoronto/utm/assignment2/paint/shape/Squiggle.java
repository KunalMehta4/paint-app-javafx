
package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a continuous mouse drag
 */
public class Squiggle extends AbstractLineShape {
    private final ArrayList<Point> points = new ArrayList<>();
    protected Point lastDragPoint; // For calculating delta during move

    public Squiggle() {}
    protected List<Point> getPoints() {
        return this.points;
    }

    /**
     * If drawing, clear previous points; add first point
     * If moving, record click position for delta calc
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMousePress(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            // store the initial press point to calculate drag delta
            lastDragPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
        } else {
            this.points.clear();
            this.points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
        }
    }

    /**
     * If drawing, add new point to the path
     * If moving, calc distance from last known point and translate shape
     * @param mouseEvent input
     * @param model reference to paint model
     */
    @Override
    public void handleMouseDrag(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            if (lastDragPoint == null) return;
            double dx = mouseEvent.getX() - lastDragPoint.x;
            double dy = mouseEvent.getY() - lastDragPoint.y;
            this.translate(dx, dy);
            lastDragPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
            model.update();
        } else {
            this.points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
            model.update();
        }
    }

    /**
     * If drawing, marked as finished and add to model
     * If moving, finalizes and add to model
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
            lastDragPoint = null;
        } else {
            if (!this.points.isEmpty() && !this.isFinished()) {
                this.setFinished(true);
                model.addShape(this);
                model.update();
                System.out.println("Added Squiggle");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape createInstance() {
        return new Squiggle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(GraphicsContext g) {
        if (points.size() < 2) {
            return;
        }

        // dont draw anything if stroke width is 0
        if (getStrokeWidth() == 0) {
            return;
        }

        // Allow user to change later
        g.setStroke(getStrokeColor());
        g.setLineWidth(getStrokeWidth());

        g.beginPath();
        Point startPoint = this.points.getFirst();
        g.moveTo(startPoint.x, startPoint.y);

        for (int i = 1; i < this.points.size(); i++) {
            Point endPoint = this.points.get(i);
            g.lineTo(endPoint.x, endPoint.y);
        }

        g.stroke();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void finalizeShape(PaintModel model) {}

    /**
     * {@inheritDoc}
     */
    @Override
    public void translate(double dx, double dy) {
        for (Point p : this.points) {
            p.x += dx;
            p.y += dy;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shape clone() {
        Squiggle copy = new Squiggle();

        // Deep copy all points from the ArrayList
        for (Point p : this.points) {
            copy.points.add(new Point(p.x, p.y));
        }

        // Copy state
        copy.setFinished(true);

        // Copy properties from AbstractLineShape parent class
        copy.setStrokeColor(this.getStrokeColor());
        copy.setStrokeWidth(this.getStrokeWidth());

        copy.setMoveable(true);

        return copy;
    }
}

