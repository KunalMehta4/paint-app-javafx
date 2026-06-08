package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.shape.Shape;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

/**
 * Store references to all finalized shapes. Store reference to a copied shape. Store references to all undo shapes.
 */
public class PaintModel extends Observable {
    private final ArrayList<Shape> shapes = new ArrayList<>();
    // size(copiedShape) == 1
    private final ArrayList<Shape> copiedShape = new ArrayList<>();
    private final Stack<Shape> undoStack = new Stack<>();
    private final String version;

    // standard getters setters
    public PaintModel(String version) {
        this.version = version;
    }
    public String getVersion() {return this.version;}
    public void addShape(Shape shape) {
        this.shapes.add(shape);
    }
    public void addCopiedShape(Shape shape) {
        this.copiedShape.add(shape);
    }
    public ArrayList<Shape> getShapes() {
        return this.shapes;
    }
    public ArrayList<Shape> getCopiedShape() {
        return this.copiedShape;
    }
    public void clearCopiedShape() {
        this.copiedShape.clear();
    }
    public void clearShapes() {
        this.shapes.clear();
    }
    public void addShapes(ArrayList<Shape> shapes) {
        this.shapes.addAll(shapes);
    }
    public Stack<Shape> getundoStack() {
        return this.undoStack;
    }


    public void update() {
        this.setChanged();
        this.notifyObservers();
    }

    public void pushUndoShape(Shape s) {
        if (s != null) {
            undoStack.push(s);
        }
    }


}
