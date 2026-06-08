package ca.utoronto.utm.assignment2.paint.shape;

import ca.utoronto.utm.assignment2.paint.PaintModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.io.Serializable;

/**
 * Contract for each shape
 * A shape will be able to draw itself on a canvas, handle mouse inputs, provide method for internal state management,
 * clone itself. This interface extends Serialization to support save and open command
 */
public interface Shape extends Serializable {
    /**
     * Typically the starting point of drawing a shape
     * @param mouseEvent input
     * @param model reference to paint model
     */
    void handleMousePress(MouseEvent mouseEvent, PaintModel model);

    /**
     * Typically the final point of drawing a shape. For moving, it is the snap-in feature to lock shape in place.
     * @param mouseEvent input
     * @param model reference to paint model
     */
    void handleMouseRelease(MouseEvent mouseEvent, PaintModel model);

    /**
     * Typically update shape's dimension in real time. For moving, it updates shape's position.
     * @param mouseEvent input
     * @param model reference to paint model
     */
    void handleMouseDrag(MouseEvent mouseEvent, PaintModel model);

    /**
     * Render shape onto canvas
     * @param g graphicscontext to draw on
     */
    void draw(GraphicsContext g);

    /**
     * This is reserved for multi-stage shape such as polyline; most shape are finalized on mouse release. this method
     * forced a shape to finish itself. Typically called during a mode switch.
     * @param model
     */
    void finalizeShape(PaintModel model);

    /**
     * Create a base instance of the shape; empty
     * Used by the factory pattern design
     * @return a new instance
     */
    Shape createInstance();

    /**
     * @return if shape is finished
     */
    boolean isFinished();

    /**
     * @return if shape is moveable
     */
    boolean isMoveable();

    /**
     * set moveable state
     * @param moveable make shape moveable
     */
    void setMoveable(boolean moveable);

    /**
     * set finished state
     * @param finished no modifications make to shape attributes or dimensions allowed
     */
    void setFinished(boolean finished);

    /**
     * @return a perfect copy of itself
     */
    Shape clone();

    /**
     * Move shape in direction
     * @param dx change of x
     * @param dy change of y
     */
    void translate(double dx, double dy);
}
