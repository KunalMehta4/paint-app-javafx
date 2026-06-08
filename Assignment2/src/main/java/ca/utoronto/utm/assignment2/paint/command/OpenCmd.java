package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.AttributesModel;
import ca.utoronto.utm.assignment2.paint.Mode;
import ca.utoronto.utm.assignment2.paint.PaintModel;
import ca.utoronto.utm.assignment2.paint.shape.Shape;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Open the .ser file in /paint/saves
 * make sure the commit id matches or things MIGHT break
 * load the shapes, copiedshapes, current mode, selected fillcolor, selected strokecolor, selected strokewidth.
 * return CommandResult
 */
public class OpenCmd implements Command {
    private final PaintModel paintModel;
    private final Mode modeHandler;
    private final AttributesModel attributesModel;
    private final Path SAVE_DIR = Paths.get("paint", "saves");

    public OpenCmd(PaintModel model, Mode modeHandler, AttributesModel attributesModel) {
        this.paintModel = model;
        this.modeHandler = modeHandler;
        this.attributesModel = attributesModel;
    }

    // return highest version or -1 if not no file found
    public int getLatestFileVersionNumber() throws IOException {
        Files.createDirectories(SAVE_DIR);

        Pattern filePattern = Pattern.compile("paint(\\d+)\\.ser");
        int maxVersion;
        try (Stream<Path> paths = Files.list(SAVE_DIR)) {
            maxVersion = paths
                    .map(path -> filePattern.matcher(path.getFileName().toString()))
                    .filter(Matcher::matches)
                    .mapToInt(matcher -> Integer.parseInt(matcher.group(1)))
                    .max()
                    .orElse(-1);
        }
        return maxVersion;
    }

    /**
     * Open the highest version of paint#version.ser in /paint/saves/ (if any)
     * Read the file and load everything up
     * update canvas
     * @return CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            int fileVersion = getLatestFileVersionNumber();
            if (fileVersion == -1) {
                System.out.println("no save files");
                return new CommandResult("Open", false, null);
            }
            String fileName = "paint"  + fileVersion + ".ser";
            Path file = SAVE_DIR.resolve(fileName);


            try (FileInputStream fis = new FileInputStream(file.toFile());
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                 @SuppressWarnings("unchecked")
                 ArrayList<Shape> loadedShapes = (ArrayList<Shape>) ois.readObject();

                 // cannot implement since undo/redo is still broken!
                 @SuppressWarnings("unchecked")
                 Stack<Shape> loadedUndoShapes = (Stack<Shape>) ois.readObject();
                 // cannot uncomment because that would messed up readobject procedure

                 @SuppressWarnings("unchecked")
                 ArrayList<Shape> loadedCopiedShapes = (ArrayList<Shape>) ois.readObject();
                 String loadedCurrentMode = (String) ois.readObject();
                 AttributesModel loadedAttributesModel = (AttributesModel) ois.readObject();
                 String version = (String) ois.readObject();

                 // check the version in Paint.java to ensure everything loads properly
                 if (!Objects.equals(version, this.paintModel.getVersion())) {
                     System.out.println("LAST KNOWN COMPATIBLE VERSION (newer changes may or may not break): " + this.paintModel.getVersion());
                     return new CommandResult("Open", false, null);
                 }

                 this.paintModel.clearShapes();
                 this.paintModel.addShapes(loadedShapes);
                 this.paintModel.clearCopiedShape();
                 if (!loadedCopiedShapes.isEmpty()) {
                     this.paintModel.addCopiedShape(loadedCopiedShapes.getLast());
                 }

                 this.modeHandler.setMode(loadedCurrentMode);
                 this.attributesModel.setStrokeWidth(loadedAttributesModel.getStrokeWidth());
                 this.attributesModel.setFillColor((Color) loadedAttributesModel.getFillColor());
                 this.attributesModel.setStrokeColor(loadedAttributesModel.getStrokeColor());


                 this.paintModel.update();
                 return new CommandResult("Open", true, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error opening save file");
            System.out.println("LAST KNOWN COMPATIBLE VERSION (newer changes may or may not break): " + this.paintModel.getVersion());
            return new CommandResult("open", false, null);
        }
    }
}
