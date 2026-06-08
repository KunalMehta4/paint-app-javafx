
package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.shape.Shape;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Observable;

/**
 * Representation of all drawing modes using a dictionary (map). Each key is mode name, and the corresponding value is
 * the base instance of a shape
 */
public class Mode extends Observable implements Serializable {
    private Map<String, Shape> shapePrototypes = new HashMap<>();
    private String currentMode = "";
    private String previousMode = "";
    private Shape shapeToManipulate = null;

    /**
     * register the mode to shapePrototypes
     * @param mode mode name
     * @param prototype base instance of a shape
     */
    public void addMode(String mode, Shape prototype) {
        this.shapePrototypes.put(mode, prototype);
    }

    /**
     * Change the currentMode to mode
     * @param mode mode name
     */
    public void setMode(String mode) {
        if (!this.currentMode.equals(mode)) {
            if (!this.currentMode.equals("Move")) {
                this.previousMode = this.currentMode;
            }
            this.currentMode = mode;

            this.setChanged();
            this.notifyObservers();
        }
    }

    /**
     * Change currentMode to the value of previousMode
     * This is only used for Moveable shape (reverting from Move state to previous drawing state)
     */
    public void revertToPreviousMode() {
        // go back to previous drawing mode after moving the shape
        this.currentMode = this.previousMode;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * Set shape to manipulate
     * Representation invariant: shape.isFinished() == true
     * @param shape a valid shape
     */
    public void setShapeToManipulate(Shape shape) {
        this.shapeToManipulate = shape;
        this.setChanged();
        this.notifyObservers();
    }

    /**
     * @return Shape
     */
    public Shape getShapeToManipulate() {
        return this.shapeToManipulate;
    }

    /**
     * @return String currentMode
     */
    public String getCurrentMode() {
        return currentMode;
    }

    /**
     * @return Shape base instance of the shape corresponding to a given mode
     */
    public Shape getCurrentPrototype() {
        return this.shapePrototypes.get(this.currentMode);
    }
}

