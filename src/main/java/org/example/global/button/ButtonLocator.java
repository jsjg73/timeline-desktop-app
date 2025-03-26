package org.example.global.button;

import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.event.*;
import org.example.event.command.AfterCreateSubtaskCommand;

public class ButtonLocator {
    private static TaskButtons taskButtons;

    public static void init(VBox root, TextField taskNameField) {
        taskButtons = TaskButtons.init(root, taskNameField);
    }

    public static void disableTaskButton() {
        taskButtons.disableTaskButton();
    }

    public static void handlerAfterCreateTask(String parentId,
                                              StackPane stackPane,
                                              TaskHandler parent,
                                              int baseY,
                                              int indent) {
        taskButtons.handlerAfterCreateTask(parentId, stackPane, parent, baseY, indent);
    }

    public static void handlerAfterCreateSubtask(AfterCreateSubtaskCommand command) {
        taskButtons.handlerAfterCreateSubtask(command);
    }

    public static void enableSubtaskButton() {
        taskButtons.enableSubtaskButton();
    }

    public static void enableCompleteButton() {
        taskButtons.enableCompleteButton();
    }

    public static void removeLastComplete() {
        taskButtons.removeLastComplete();
    }

    public static void enableAddTaskButton() {
        taskButtons.enableAddTaskButton();
    }

    public static void disableSubtaskButton() {
        taskButtons.disableSubtaskButton();
    }

    public static void disableCompleteButton() {
        taskButtons.disableCompleteButton();
    }
}
