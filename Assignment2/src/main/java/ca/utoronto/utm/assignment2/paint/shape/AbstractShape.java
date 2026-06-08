package ca.utoronto.utm.assignment2.paint.shape;
import ca.utoronto.utm.assignment2.paint.SerializationUtils;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.Serial;

/**
 * An abstract class for all shapes. Provide many boiler plate code common properties and state management. Handle
 * stroke color, stroke width, and  moveable & finished state. Provide serialization to handle non-serializable Paint object
 */
public abstract class AbstractShape implements Shape {
    private boolean moveable = false;
    private boolean finished = false;
    private transient Paint strokeColor = Color.BLACK;
    private double strokeWidth = 1.0;
    private Point prevPosition;
    private Point currentPosition;

    // standard getters & setters....
    public Paint getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Paint strokeColor) {
        this.strokeColor = strokeColor;
    }

    public double getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(double strokeWidth) {
        this.strokeWidth = strokeWidth;
    }


    public abstract Shape clone();

    // javafx.scene.paint.Color is not Serializable, handle this manually
    /**
     * Serialize stroke color by manually writing r,g,b,a to stream
     * @param out output stream
     * @throws IOException error
     */
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        SerializationUtils.writeColor(out, (Color) getStrokeColor());
    }

    /**
     * Read r,g,b,a properties from in stream and reconstruct Color object
     * @param in input stream
     * @throws IOException error
     * @throws ClassNotFoundException error
     */
    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        setStrokeColor(SerializationUtils.readColor(in));
    }

    @Override
    public void setMoveable(boolean value) {
        this.moveable = value;
    }

    @Override
    public boolean isMoveable() {return this.moveable;}

    @Override
    public void setFinished(boolean value) {
        this.finished = value;
    }

    @Override
    public boolean isFinished() {return this.finished;}

    public void setPrevPosition(Point p) { this.prevPosition = p; }

    public void setCurrentPosition(Point p) { this.currentPosition = p; }

    public boolean revert() {
        if (this.prevPosition != null && this.currentPosition != null) {
            double dx = prevPosition.x - currentPosition.x;
            double dy = prevPosition.y - currentPosition.y;
            this.translate(dx, dy);

            Point temp = this.prevPosition;
            this.prevPosition = this.currentPosition;
            this.currentPosition = temp;

            return true;
        }
        return false;
    }
    public void clearMoveHistory() {
        this.prevPosition = null;
        this.currentPosition = null;
    }
}
