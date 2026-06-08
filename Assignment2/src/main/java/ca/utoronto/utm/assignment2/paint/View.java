package ca.utoronto.utm.assignment2.paint;

import ca.utoronto.utm.assignment2.paint.command.*;
import ca.utoronto.utm.assignment2.paint.AttributesModel;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.Observable;
import java.util.Observer;

import java.util.Objects;

/**
 * Main view class for the paint application.
 * Create and arrange all UI components, including menus and panels.
 * Connect the view to the PaintModel so updates refresh the display.
 */

public class View implements Observer{
        private final PaintModel paintModel;
        private final CommandHandler commandHandler;
        private final StatusModel statusModel;
        private final Label statusBar;
        private final StatusModelController statusModelController;
        private final Shortcuts shortcut;
        //new:
        //private final AttributesModel attributesModel;

        public View(PaintModel model, Stage stage) {
            this.paintModel = model;
            //new:
            //this.attributesModel = new AttributesModel();

            AttributesModel attributesModel = new AttributesModel();

            // modeHandler
            Mode modeHandler = new Mode();
            PaintPanel paintPanel = new PaintPanel(this.paintModel, modeHandler, attributesModel);
            ShapeChooserPanel shapeChooserPanel = new ShapeChooserPanel(modeHandler, paintPanel);

            ShapeAttributeChooserPanel attributesPanel = new ShapeAttributeChooserPanel(attributesModel);

            // commandHandler
            this.commandHandler = new CommandHandler();
            this.commandHandler.registerCommand("Cut", new CutCmd(model, paintPanel));
            this.commandHandler.registerCommand("Copy", new CopyCmd(model, paintPanel));
            this.commandHandler.registerCommand("Paste", new PasteCmd(model, modeHandler));

            this.commandHandler.registerCommand("New", new NewCmd(model, paintPanel));
            this.commandHandler.registerCommand("Save", new SaveCmd(model, modeHandler, attributesModel));
            this.commandHandler.registerCommand("Open", new OpenCmd(paintModel, modeHandler, attributesModel));
            this.commandHandler.registerCommand("Exit", new ExitCmd());

            this.commandHandler.registerCommand("Undo", new UndoCmd(model, paintPanel));
            this.commandHandler.registerCommand("Redo", new RedoCmd(model));

            this.shortcut = new Shortcuts(this.commandHandler);

            // statusModel
            this.statusModel = new StatusModel();
            this.statusModel.addObserver(this);
            // statusBar
            this.statusBar = new Label(this.statusModel.getMessage());
            this.statusBar.setPadding(new javafx.geometry.Insets(5));
            // hook up status model controller
            this.statusModelController = new StatusModelController(this.paintModel, modeHandler, this.statusModel, this.commandHandler);

            // GUI display
            BorderPane root = new BorderPane();
            root.setTop(createMenuBar());
            root.setCenter(paintPanel);
            root.setLeft(shapeChooserPanel);
            root.setRight(attributesPanel);
            root.setBottom(statusBar);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            scene.setOnKeyPressed(event -> {this.shortcut.handleKeyPress(event);});

            stage.setScene(scene);
            stage.setTitle("Paint");
            stage.show();

            // fix issue with circle mode highlighted
            Platform.runLater(paintPanel::requestFocus);
        }

        private MenuBar createMenuBar() {

                MenuBar menuBar = new MenuBar();
                Menu menu;
                MenuItem menuItem;

                // A menu for File

                menu = new Menu("File");

                menuItem = new MenuItem("New");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("New"));
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Open");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Open"));
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Save");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Save"));
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Exit");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Exit"));
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                // Another menu for Edit

                menu = new Menu("Edit");

                menuItem = new MenuItem("Cut");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Cut"));
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Copy");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Copy"));
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Paste");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Paste"));
                menu.getItems().add(menuItem);

                menu.getItems().add(new SeparatorMenuItem());

                menuItem = new MenuItem("Undo");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Undo"));
                menu.getItems().add(menuItem);

                menuItem = new MenuItem("Redo");
                menuItem.setOnAction(e -> this.commandHandler.handleCommand("Redo"));
                menu.getItems().add(menuItem);

                menuBar.getMenus().add(menu);

                return menuBar;
        }

        @Override
        public void update(Observable o, Object arg) {
            if (o == this.statusModel) {
                this.statusBar.setText(this.statusModel.getMessage());
            }
        }
}
