package ca.utoronto.utm.assignment2.paint;

import java.util.Observable;

/**
 * The View observes this model to update the status bar.
 */
public class StatusModel extends Observable {
    private String message = "Ready";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.setChanged();
        this.notifyObservers();
    }
}

