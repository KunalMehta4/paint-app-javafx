package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

/**
 * A drawable rectangle defined by a bounding box.
 * Store top-left corner, width, and height.
 * Allow drawing, moving, and cloning for copy and paste.
 */

public class Rectangle extends AbstractBoundingBoxShape {

    public Rectangle() {
        super();
    }

    public Rectangle(Point topLeft, double width, double height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public Shape createInstance() {
        return new Rectangle();
    }

    @Override
    public void draw(GraphicsContext g) {
        // Allow users to change fill color, stroke width later
        //setFillColor(Color.BLUE);

        g.setFill(getFillColor());
        g.fillRect(this.getTopLeft().x, this.getTopLeft().y, this.getWidth(), this.getHeight());

        // Only draw stroke if width > 0
        if (getStrokeWidth() > 0) {
            g.setStroke(getStrokeColor());
            g.setLineWidth(getStrokeWidth());
            g.strokeRect(this.getTopLeft().x, this.getTopLeft().y, this.getWidth(), this.getHeight());
        }

    }

    @Override
    public Shape clone() {
        Rectangle copy = new Rectangle();

        // Copy topLeft point (deep copy)
        if (this.topLeft != null) {
            copy.topLeft = new Point(this.topLeft.x, this.topLeft.y);
        }

        // Copy start point (deep copy)
        if (this.start != null) {
            copy.start = new Point(this.start.x, this.start.y);
        }

        // Copy dimensions
        copy.width = this.width;
        copy.height = this.height;

        // Copy state
        copy.setFinished(true);

        // Copy properties from AbstractFillableShape parent class
        copy.setFillColor(this.getFillColor());
        copy.setStrokeColor(this.getStrokeColor());
        copy.setStrokeWidth(this.getStrokeWidth());

        copy.setMoveable(true);

        return copy;
    }
}