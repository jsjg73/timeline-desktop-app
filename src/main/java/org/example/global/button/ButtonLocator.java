package org.example.global.button;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.example.event.*;

public class ButtonLocator {
    private static TaskButtons taskButtons;

    public static void init() {
        taskButtons = new TaskButtons();
    }

    public static void handleTaskButton(TextField taskNameField) {
        taskButtons.handleTaskButton(taskNameField);
    }

    public static void disableTaskButton() {
        taskButtons.disableTaskButton();
    }

    public static void enableStartButton() {
        taskButtons.enableStartButton();
    }

    public static void drawGlobalButtonsOn(VBox root) {
        taskButtons.drawnOn(root);
    }

    public static void handlerAfterCreateTask(String parentId,
                                              StackPane stackPane,
                                              TaskHandler parent,
                                              int baseY,
                                              int indent) {
        taskButtons.handlerAfterCreateTask(parentId, stackPane, parent, baseY, indent);
    }

    public static void handlerAfterCreateSubtask(
            String parentId,
            StackPane stackPane,
            TaskHandler parent,
            int baseX,
            int baseY,
            int indent
    ) {
        taskButtons.handlerAfterCreateSubtask(
                parentId, stackPane, parent, baseX, baseY, indent
        );
    }

    public static void enableSubtaskButton() {
        taskButtons.enableSubtaskButton();
    }

    public static void enableCompleteButton() {
        taskButtons.enableCompleteButton();
    }

    public static void disableStartButton() {
        taskButtons.disableStartButton();
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
