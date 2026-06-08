package ca.utoronto.utm.assignment2.paint;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Observable;
import java.util.Observer;

/**
 * A panel that allows users to configure a shape's attribute such as
 * stroke width, stroke color, fill color. This panel observes an AttributesModel and
 * updates the UI components when the model changes!
 */
public class ShapeAttributeChooserPanel extends VBox implements Observer {
    private final AttributesModel attributes;

    // Slider for stroke width
    private final Slider strokeWidthSlider = new Slider(0, 20, 1);
    private final Label strokeWidthLabel = new Label("Stroke Width: 1.0 px");

    // Color pickers
    private final ColorPicker strokeColorPicker = new ColorPicker(Color.BLACK);
    private final Label strokeColorLabel = new Label("Stroke Color:");

    private final ColorPicker fillColorPicker = new ColorPicker(Color.BLACK);
    private final CheckBox transparentFillCheckbox = new CheckBox("Transparent Fill");
    private final Label fillColorLabel = new Label("Fill Color:");

    /**
     * constructs a ShapeAttributeChooserPanel with the specified attributes model.
     * Initializes UI components, sets up event listeners
     * @param attributes the AttributesModel to observe and modify
     */
    public ShapeAttributeChooserPanel(AttributesModel attributes) {
        this.attributes = attributes;
        this.attributes.addObserver(this);

        setSpacing(10);
        setPadding(new Insets(10));
        getStyleClass().add("side-panel");

        Label title = new Label("Attributes");
        title.getStyleClass().add("panel-title");

        strokeWidthSlider.setShowTickMarks(true);
        strokeWidthSlider.setShowTickLabels(true);
        strokeWidthSlider.setMajorTickUnit(5);
        strokeWidthSlider.setMinorTickCount(1);
        strokeWidthSlider.setBlockIncrement(1);

        strokeWidthSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();
            attributes.setStrokeWidth(width);
            strokeWidthLabel.setText("Stroke Width: " + String.format("%.1f", width) + " px");
        });

        // pick stroke colour
        strokeColorPicker.setOnAction(e -> {
            Color color = strokeColorPicker.getValue();
            attributes.setStrokeColor(color);
        });

        // pick fill colour
        fillColorPicker.setOnAction(e -> {
            if (!transparentFillCheckbox.isSelected()) {
                Color color = fillColorPicker.getValue();
                attributes.setFillColor(color);
            }
        });

        // checkbox for transparent fill:
        transparentFillCheckbox.setSelected(true); // Start with transparent
        transparentFillCheckbox.setOnAction(e -> {
            if (transparentFillCheckbox.isSelected()) {
                attributes.setFillColor(Color.TRANSPARENT);
                fillColorPicker.setDisable(true);
            } else {
                attributes.setFillColor(fillColorPicker.getValue());
                fillColorPicker.setDisable(false);
            }
        });

        // disable fill color picker at the beginning!
        fillColorPicker.setDisable(true);

        // Add all components to the panel
        getChildren().addAll(
                title,
                strokeWidthLabel,
                strokeWidthSlider,
                strokeColorLabel,
                strokeColorPicker,
                fillColorLabel,
                transparentFillCheckbox,
                fillColorPicker
        );

        // Initialize from model defaults
        update(null, null);
    }

    /**
     * updates UI components to reflect the user's selection - current state
     * of attribute model
     * this method is called automatically when the observed AttributeModel changes
     * @param o     the observable object (AttributesModel)
     * @param arg   an argument passed to the {@code notifyObservers}
     *                 method.
     */

    @Override
    public void update(Observable o, Object arg) {
        // Update slider and label
        double width = attributes.getStrokeWidth();
        strokeWidthSlider.setValue(width);
        strokeWidthLabel.setText("Stroke Width: " + String.format("%.1f", width) + " px");

        // Update color pickers
        if (attributes.getStrokeColor() instanceof Color) {
            strokeColorPicker.setValue((Color) attributes.getStrokeColor());
        }

        // Update fill color or transparent checkbox
        if (attributes.getFillColor() == Color.TRANSPARENT) {
            transparentFillCheckbox.setSelected(true);
            fillColorPicker.setDisable(true);
        } else if (attributes.getFillColor() instanceof Color) {
            transparentFillCheckbox.setSelected(false);
            fillColorPicker.setValue((Color) attributes.getFillColor());
            fillColorPicker.setDisable(false);
        }
    }
}