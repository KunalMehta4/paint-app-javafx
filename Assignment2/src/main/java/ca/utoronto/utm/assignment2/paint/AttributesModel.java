package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.Observable;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.Serial;

/**
 * Store the current drawing attributes for the paint application.
 * Track stroke width, stroke color, and fill color.
 * Provide getters and setters so shapes can use the selected attributes.
 */

//2 lines in view called new!
public class AttributesModel extends Observable implements Serializable {
    private double strokeWidth = 1.0;
    private transient Color strokeColor = Color.BLACK;
    private transient Paint fillColor = Color.TRANSPARENT;

    public double getStrokeWidth() { return strokeWidth; }
    public Paint getFillColor() { return fillColor; }
    public Color getStrokeColor() { return strokeColor; }

    public void setStrokeWidth(double strokeWidth) {
        if (strokeWidth < 0) strokeWidth = 0;
        this.strokeWidth = strokeWidth;
        setChanged();
        notifyObservers();
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        setChanged();
        notifyObservers();
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
        setChanged();
        notifyObservers();
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        SerializationUtils.writeColor(out, (Color) getFillColor());
        SerializationUtils.writeColor(out, strokeColor );
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        setFillColor(SerializationUtils.readColor(in));
        setStrokeColor(SerializationUtils.readColor(in));
    }
}
