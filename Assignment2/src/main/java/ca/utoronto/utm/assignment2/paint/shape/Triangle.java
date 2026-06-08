package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;

/**
 * A triangle defined by three points.
 * Track the points added during mouse interaction.
 * Compute and draw the triangle’s edges.
 * Allow drawing, moving, and cloning for copy and paste.
 */

public class Triangle extends AbstractBoundingBoxShape {

    public Triangle() {
        super();
    }

    @Override
    public Shape createInstance() {
        return new Triangle();
    }

    @Override
    public void draw(GraphicsContext g) {
        // Point 1: Top-center of the bounding box
        double xA = this.topLeft.x + this.width / 2.0;
        double yA = this.topLeft.y;

        // Point 2: Bottom-right of the bounding box
        double xB = this.topLeft.x + this.width;
        double yB = this.topLeft.y + this.height;

        // Point 3: Bottom-left of the bounding box
        double xC = this.topLeft.x;
        double yC = this.topLeft.y + this.height;

        double[] xs = {xA, xB, xC};
        double[] ys = {yA, yB, yC};

        // Draw fill
        g.setFill(getFillColor());
        g.fillPolygon(xs, ys, 3);

        // Only draw stroke if width > 0
        if (getStrokeWidth() > 0) {
            g.setStroke(getStrokeColor());
            g.setLineWidth(getStrokeWidth());
            g.strokePolygon(xs, ys, 3);
        }
    }

    @Override
    public Shape clone() {
        Triangle copy = new Triangle();

        if (this.getTopLeft() != null) {
            copy.setTopLeft(new Point(this.getTopLeft().x, this.getTopLeft().y));
        }

        if (this.getStart() != null) {
            copy.setStart(new Point(this.getStart().x, this.getStart().y));
        }

        copy.setFinished(true);

        copy.setWidth(this.getWidth());
        copy.setHeight(this.getHeight());
        copy.setFillColor(this.getFillColor());
        copy.setStrokeColor(this.getStrokeColor());
        copy.setStrokeWidth(this.getStrokeWidth());

        copy.setMoveable(true);

        return copy;


    }
}

