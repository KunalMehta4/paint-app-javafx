package ca.utoronto.utm.assignment2.paint;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The paint application. This class wires up MVC components
 */
public class Paint extends Application {

        PaintModel model; // Model
        View view; // View + Controller


    /**
     * launches application
     * @param args command line arguments
     */
    public static void main(String[] args) {
                launch(args);
        }

    /**
     * initializes the paint model and view
     * @param stage
     * @throws Exception
     */
    @Override
        public void start(Stage stage) throws Exception {
                final String VERSION = "e9c75b501afbe862560e52f5c73f0d4a259792e2";
            // COMMIT ID OF LAST KNOWN COMPATIBLE VERSION WITH SAVE COMMAND
                this.model = new PaintModel(VERSION);

                // View + Controller
                this.view = new View(model, stage);
        }
}
