package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.SerializationUtils;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serial;

/**
 * An AbstractFillableShape is a Shape that has an inside area
 * Each AbstractFillableShape must have a fill color
 */
public abstract class AbstractFillableShape extends AbstractShape {
    private transient Paint fillColor = Color.LIGHTGRAY;

    // standard getters setters
    public Paint getFillColor() {
        return fillColor;
    }
    public void setFillColor(Paint fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * serialize fillcolor; see doc in AbstractShape
     * @param out output stream
     * @throws IOException error
     */
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

        SerializationUtils.writeColor(out, (Color) getFillColor());
    }

    /**
     * reconstruct fillcolor; see doc in AbstractShape
     * @param in input stream
     * @throws IOException error
     * @throws ClassNotFoundException error
     */
    @Serial
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();

        setFillColor(SerializationUtils.readColor(in));
    }
}
