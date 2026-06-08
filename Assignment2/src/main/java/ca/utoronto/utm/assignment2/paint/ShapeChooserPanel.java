
package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.shape.*;
import ca.utoronto.utm.assignment2.paint.shape.Circle;
import ca.utoronto.utm.assignment2.paint.shape.Polyline;
import ca.utoronto.utm.assignment2.paint.shape.Rectangle;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;

/**
 * Panel that displays the available shape options.
 * Let the user select which shape to draw next.
 * Update the Mode handler when a new shape is chosen.
 */

public class ShapeChooserPanel extends GridPane implements Observer {

    private final Mode modeHandler;
    private final Map<String, Button> modeButtons = new HashMap<>();
    private final PaintPanel paintPanel;

    public ShapeChooserPanel(Mode modeHandler, PaintPanel paintPanel) {
        this.modeHandler = modeHandler;
        this.modeHandler.addObserver(this);
        this.paintPanel = paintPanel;

        // For each shape, create a UI button and register its
        // corresponding DrawableShape prototype with the mode handler.
        addModeButton("Circle", new javafx.scene.shape.Circle(10), new Circle());
        addModeButton("Rectangle", new javafx.scene.shape.Rectangle(21, 14), new Rectangle());
        addModeButton("Square", new javafx.scene.shape.Rectangle(17, 17), new Square());
        addModeButton("Triangle", new Polygon(8.5, 0, 17,  14, 0,   14), new Triangle());
        addModeButton("Oval", new Ellipse(8, 12), new Oval());
        addModeButton("Squiggle", squiggleIcon(), new Squiggle());
        addModeButton("Polyline", polylineIcon(), new Polyline());
    }

    /**
     * Add button to canvas. register button as a drawing option
     * @param modeName drawing mode
     * @param icon what the shape looks like
     * @param prototype base instance of shape
     */
    private void addModeButton(String modeName, Node icon, Shape prototype) {
        this.modeHandler.addMode(modeName, prototype);

        Button button = new Button();
        button.setGraphic(icon);
        button.setPrefSize(40, 40);

        button.setOnAction(e -> {
            if (this.modeHandler.getCurrentMode().equals(modeName)) {
                this.modeHandler.setMode("");
            } else {
                this.modeHandler.setMode(modeName);
                System.out.println("Mode set to: " + modeName);
            }
            this.paintPanel.requestFocus();
        });

        add(button, 0, getRowCount());
        this.modeButtons.put(modeName, button);
    }

    private Node squiggleIcon() {
        Path path = new Path();
        path.getElements().add(new MoveTo(5, 20));
        path.getElements().add(new QuadCurveTo(15, 5, 25, 20));
        path.getElements().add(new QuadCurveTo(35, 35, 45, 20));

        path.setStroke(Color.BLACK);
        path.setStrokeWidth(2);
        path.setFill(null);
        return path;
    }

    // to be changed
    private Node polylineIcon() {
        return new Text("Polyline");
    }

    /**
     * Toggle the active button when user click on a different drawing mode.
     * Toggle inactive when user click on active drawing mode.
     * @param o     the observable object.
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */
    @Override
    public void update(Observable o, Object arg) {
        String currentMode = this.modeHandler.getCurrentMode();

        for (Map.Entry<String, Button> entry : modeButtons.entrySet()) {
            String buttonMode = entry.getKey();
            Button button = entry.getValue();

            if (buttonMode.equals(currentMode)) {
                if (!button.getStyleClass().contains("active-button")) {
                    button.getStyleClass().add("active-button");
                }
            } else {
                button.getStyleClass().remove("active-button");
            }
        }
    }
}



