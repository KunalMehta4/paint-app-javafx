package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Oval extends AbstractBoundingBoxShape {
    public Oval(){
        super();
    }

    public Oval(Point topLeft, double width, double height){
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext g) {
        // Draw fill
        g.setFill(getFillColor());
        g.fillOval(this.topLeft.x, this.topLeft.y, this.width, this.height);

        // Only draw stroke if width > 0
        if (getStrokeWidth() > 0) {
            g.setStroke(getStrokeColor());
            g.setLineWidth(getStrokeWidth());
            g.strokeOval(this.topLeft.x, this.topLeft.y, this.width, this.height);
        }

    }

    @Override
    public Shape createInstance() {
        return new Oval();
    }

    @Override
    public Shape clone() {
        Oval copy = new Oval();

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
