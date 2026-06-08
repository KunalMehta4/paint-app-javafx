package ca.utoronto.utm.assignment2.paint.command;

import ca.utoronto.utm.assignment2.paint.AttributesModel;
import ca.utoronto.utm.assignment2.paint.Mode;
import ca.utoronto.utm.assignment2.paint.PaintModel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Save the following items in a .ser file in /paint/saves
 * - PaintModel.shapes
 * - PaintModel.undoStack
 * - PaintModel.copiedShapes
 * - Mode.currentMode
 * - AttributesModel
 * - Paint.version
 * The file version is the current highest + 1
 * return CommandResult
 */
public class SaveCmd implements Command {
    private final PaintModel paintModel;
    private final Mode modeHandler;
    private final AttributesModel attributesModel;
    private final Path SAVE_DIR = Paths.get("paint", "saves");

    public SaveCmd(PaintModel paintModel, Mode modeHandler, AttributesModel attributesModel) {
        this.paintModel = paintModel;
        this.modeHandler = modeHandler;
        this.attributesModel = attributesModel;
    }

    // return highest version + 1
    private String findFileVersionNumber() throws IOException {
        Files.createDirectories(SAVE_DIR);

        Pattern filePattern = Pattern.compile("paint(\\d+)\\.ser");
        int maxVersion;
        try (Stream<Path> paths = Files.list(SAVE_DIR)) {
            maxVersion = paths
                    .map(path -> filePattern.matcher(path.getFileName().toString()))
                    .filter(Matcher::matches)
                    .mapToInt(matcher -> Integer.parseInt(matcher.group(1)))
                    .max()
                    .orElse(0);
        }
        return Integer.toString(++maxVersion);
    }

    /**
     * See documentation above
     * @return CommandResult
     */
    @Override
    public CommandResult execute() {
        try {
            String fileVersionNumber = this.findFileVersionNumber();
            String fileName = "paint" + fileVersionNumber + ".ser";
            Path fullPath = SAVE_DIR.resolve(fileName);

            try (FileOutputStream fos = new FileOutputStream(fullPath.toFile());
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {

                oos.writeObject(this.paintModel.getShapes());
                oos.writeObject(this.paintModel.getundoStack());
                oos.writeObject(this.paintModel.getCopiedShape());
                oos.writeObject(this.modeHandler.getCurrentMode());
                oos.writeObject(this.attributesModel);
                oos.writeObject(this.paintModel.getVersion());

                return new CommandResult("Save", true, null);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResult("Save", false, null);
        }
    }
}
