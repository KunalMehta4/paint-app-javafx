package ca.utoronto.utm.assignment2.paint;

import javafx.scene.paint.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Utility class for serializing and deserializing objects.
 * Convert objects to byte arrays and restore objects from bytes.
 * Used by shapes to support deep cloning and save/load operations.
 */

public class SerializationUtils {

    private SerializationUtils() {}

    // can only represent a static color, i.e. not a gradient.
    // represent the Color object as the rgb + a value
    public static void writeColor(ObjectOutputStream out, Color color) throws IOException {
        if (color != null) {
            out.writeDouble(color.getRed());
            out.writeDouble(color.getGreen());
            out.writeDouble(color.getBlue());
            out.writeDouble(color.getOpacity());
        } else {
            out.writeDouble(0);
            out.writeDouble(0);
            out.writeDouble(0);
            out.writeDouble(0);
        }
    }

    // return the color object
    public static Color readColor(ObjectInputStream in) throws IOException {
        double red = in.readDouble();
        double green = in.readDouble();
        double blue = in.readDouble();
        double opacity = in.readDouble();
        return new Color(red, green, blue, opacity);
    }
}

