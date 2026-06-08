package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * A drawable square shape.
 * Maintain equal width and height while dragging.
 * Allow drawing, moving, and cloning for copy and paste.
 */


public class Square extends Rectangle {
    public Square() {
        super();
    }

    public Square(Point topLeft, double side) {
        super(topLeft, side, side);
    }

    @Override
    public void handleMouseDrag(MouseEvent mouseEvent, PaintModel model) {
        if (this.isMoveable()) {
            super.handleMouseDrag(mouseEvent, model);
            return;
        }

        if (start == null) return;

        double x1 = start.x;
        double y1 = start.y;
        double x2 = mouseEvent.getX();
        double y2 = mouseEvent.getY();

        double deltaX = Math.abs(x1 - x2);
        double deltaY = Math.abs(y1 - y2);
        double side = Math.max(deltaX, deltaY);

        double newTopLeftX;
        double newTopLeftY;

        if (x2 < x1) {
            //The top-left x-coordinate is the start point minus the side length.
            newTopLeftX = x1 - side;
        } else {
            //The top-left x-coordinate is the start point.
            newTopLeftX = x1;
        }

        if (y2 < y1) {
            // The top-left y-coordinate is the start point minus the side length.
            newTopLeftY = y1 - side;
        } else {
            // The top-left y-coordinate is the start point.
            newTopLeftY = y1;
        }

        this.topLeft = new Point(newTopLeftX, newTopLeftY);
        this.width = side;
        this.height = side;

        model.update();
    }


    @Override
    public Shape createInstance() {
        return new Square();
    }


    @Override
    public Shape clone() {
        Square copy = new Square();

        // Copy all accessible fields from Rectangle parent class
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