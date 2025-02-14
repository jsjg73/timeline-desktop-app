package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class AddTaskEventHandler implements EventHandler<ActionEvent> {
    private final TextField taskNameField;
    private final TaskAppender taskAppender;

    public AddTaskEventHandler(final TextField taskNameField,
                               final TaskAppender taskAppender) {
        this.taskNameField = taskNameField;
        this.taskAppender = taskAppender;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        taskAppender.drawTaskBar(readTaskName());
    }

    private String readTaskName() {
        String taskName = taskNameField.getText().trim();
        taskNameField.clear();
        return taskName.isEmpty() ? "새 업무" : taskName;
    }
}
