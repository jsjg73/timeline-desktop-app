package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.TaskButtons;

public class ControlGlobalButtons implements EventHandler<ActionEvent> {
    private final EventHandler<ActionEvent> eventHandler;
    private final TaskButtons buttons;
    public ControlGlobalButtons(TaskButtons buttons,
                                final EventHandler<ActionEvent> eventHandler) {
        this.buttons = buttons;
        this.eventHandler = eventHandler;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        eventHandler.handle(actionEvent);
        buttons.addTaskButton.setDisable(true);
        buttons.globalStart.setDisable(false);
    }
}
