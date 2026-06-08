package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * A drawable circle shape.
 * Store centre point and radius.
 * Support mouse-based drawing, moving, and rendering.
 * Provide a deep clone for copy and paste operations.
 */

public class Circle extends AbstractFillableShape {
    private Point centre;
    private double radius;

    public Circle() {
        this.centre = null;
        this.radius = 0;
    }

    public Circle(Point centre, double radius) {
        this.centre = centre;
        this.radius = radius;
    }

    public Point getCentre() {
        return centre;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void handleMousePress(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {return;}
        this.centre = new Point(mouseEvent.getX(), mouseEvent.getY());
        this.radius = 0;
    }

    @Override
    public void handleMouseDrag(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            this.centre = new Point(x, y);
            model.update();
        } else {
            if (this.centre != null) {
                double rx = mouseEvent.getX() - this.centre.x;
                double ry = mouseEvent.getY() - this.centre.y;
                this.setRadius(Math.hypot(rx, ry));
                model.update();
            }
        }

    }

    @Override
    public void handleMouseRelease(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            this.setMoveable(false);
            model.addShape(this);
            model.update();
        } else {
            if (this.centre != null && !this.isFinished()) {
                handleMouseDrag(mouseEvent, model);
                this.setFinished(true);
                model.addShape(this);
                model.update();
                System.out.println("Added Circle");
            }
        }
    }

    @Override
    public void finalizeShape(PaintModel model) {}

    @Override
    public Shape createInstance() {
        return new Circle();
    }

    @Override
    public void draw(GraphicsContext g) {
        double x = this.getCentre().x;
        double y = this.getCentre().y;
        double r = this.getRadius();

        g.setFill(getFillColor());
        g.fillOval(x - r, y - r, 2 * r, 2 * r);

        // Only draw stroke if width > 0
        if (getStrokeWidth() > 0) {
            g.setStroke(getStrokeColor());
            g.setLineWidth(getStrokeWidth());
            g.strokeOval(x - r, y - r, 2 * r, 2 * r);
        }
    }

    @Override
    public Shape clone() {
        Circle copy = new Circle();

        // Copy centre point (deep copy)
        if (this.centre != null) {
            copy.centre = new Point(this.centre.x, this.centre.y);
        }

        // Copy radius
        copy.radius = this.radius;

        // Copy state
        copy.setFinished(true);

        // Copy properties from AbstractFillableShape parent class
        copy.setFillColor(this.getFillColor());
        copy.setStrokeColor(this.getStrokeColor());
        copy.setStrokeWidth(this.getStrokeWidth());

        copy.setMoveable(true);
        return copy;
    }

    @Override
    public void translate(double dx, double dy) {
        this.centre.x += dx;
        this.centre.y += dy;
    }
}

