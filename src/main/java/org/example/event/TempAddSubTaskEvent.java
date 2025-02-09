package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.example.TaskButtons;

public class TempAddSubTaskEvent implements EventHandler<ActionEvent> {

    private final TaskButtons taskButtons;
    private final TaskAppender taskAppender;

    public TempAddSubTaskEvent(
            final TaskButtons taskButtons,
            final TaskAppender taskAppender) {
        this.taskButtons = taskButtons;
        this.taskAppender = taskAppender;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        taskAppender.addSubtask("새 하위 업무");

        taskButtons.globalStart.setDisable(false);
        taskButtons.globalComplete.setDisable(true);
        taskButtons.globalSubtask.setDisable(true);
    }
}
