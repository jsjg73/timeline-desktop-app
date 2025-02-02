package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ButtonDisableEvent implements EventHandler<ActionEvent> {
    private final Button button;
    private final EventHandler<ActionEvent> eventHandler;

    public ButtonDisableEvent (final Button button,
                               final EventHandler<ActionEvent> eventHandler) {
        this.button = button;
        this.eventHandler = eventHandler;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        eventHandler.handle(actionEvent);
        button.setDisable(true);
    }
}
