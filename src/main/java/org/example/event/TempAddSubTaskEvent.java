package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class TempAddSubTaskEvent implements EventHandler<ActionEvent> {

    private final TaskAppender taskAppender;

    public TempAddSubTaskEvent(
    final TaskAppender taskAppender) {
        this.taskAppender = taskAppender;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        taskAppender.addSubtask("새 하위 업무");
    }
}
