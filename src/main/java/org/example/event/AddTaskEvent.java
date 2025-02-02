package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class AddTaskEvent implements EventHandler<ActionEvent> {
    private final TextField taskNameField;
    private final TaskAppender taskAppender;

    public AddTaskEvent(final TextField taskNameField,
                        final TaskAppender taskAppender) {
        this.taskNameField = taskNameField;
        this.taskAppender = taskAppender;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        String taskName = taskNameField.getText().trim();
        taskName = taskName.isEmpty() ? "새 업무" : taskName;
            taskAppender.addTask(taskName);
            taskNameField.clear();
    }
}
