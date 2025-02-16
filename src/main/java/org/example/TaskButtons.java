package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.event.*;

import java.util.ArrayList;
import java.util.List;

public class TaskButtons {
    public static final String taskButtonId= "addTaskButton";
    public static final String startButtonId= "globalStartButton";
    public static final String completeButtonId = "globalCompleteButton";
    public static final String subtaskButtonId= "globalSubtaskButton";

    private final Button addTaskButton = new Button("Add Task");
    private final Button globalStart = new Button("Start");
    private final Button globalComplete = new Button("Complete");
    private final Button globalSubtask = new Button("Subtask");

    private final List<EventHandler<ActionEvent>> completeEventHandlers = new ArrayList<>();
    private final List<EventHandler<ActionEvent>> subtaskEventHandlers = new ArrayList<>();

    public TaskButtons() {
        addTaskButton.setId(taskButtonId);

        globalStart.setId(startButtonId);
        disableStartButton();

        globalComplete.setId(completeButtonId);
        disableCompleteButton();

        globalSubtask.setId(subtaskButtonId);
        disableSubtaskButton();
    }

    public void handleTaskButton(TextField taskNameField,
                                 Pane taskPane) {
        addTaskButton.setOnAction(
            new AddTaskEventHandler(
                taskNameField,
                new TaskAppender(this, taskPane)
            )
        );
    }

    public void disableTaskButton() {
        this.addTaskButton.setDisable(true);
    }

    public void enableStartButton() {
        this.globalStart.setDisable(false);
    }

    public void draw(VBox root) {
        root.getChildren().addAll(
                addTaskButton,
                globalStart,
                globalComplete,
                globalSubtask
        );
    }

    public void handlerAfterCreateTask(Rectangle rect,
                                       Label label,
                                       TaskHandler parent,
                                       int baseY,
                                       int indent) {

        this.globalStart.setOnAction(
            new GlobalStartButtonEventHandler(rect, label, this)
        );

        completeEventHandlers.add(new GlobalStopButtonEventHandler(rect, label, this));
        this.globalComplete.setOnAction( e -> completeEventHandlers.getLast().handle(e));

        subtaskEventHandlers.add(new SubTaskEvent(this, parent, 50 + 30,  baseY, indent));
        this.globalSubtask.setOnAction(e -> subtaskEventHandlers.getLast().handle(e));
    }

    public void handlerAfterCreateSubtask(
            Rectangle rect, Label label,
            TaskHandler parent,
            int baseX,
            int baseY,
            int indent
    ) {

        globalStart.setOnAction(
            e -> {
                rect.setFill(Color.YELLOW);
                label.setTextFill(Color.BLACK);

                disableStartButton();
                enableCompleteButton();
                enableSubtaskButton();
            }
        );

        EventHandler<ActionEvent> completeEventHandler = e -> {
            subtaskEventHandlers.removeLast();
            completeEventHandlers.removeLast();

            rect.setFill(Color.GRAY);
            label.setTextFill(Color.WHITE);

            enableSubtaskButton();
        };

        completeEventHandlers.add(completeEventHandler);

        subtaskEventHandlers.add(new SubTaskEvent(this, parent, baseX, baseY, indent));
    }

    public void enableSubtaskButton() {
        globalSubtask.setDisable(false);
    }

    public void enableCompleteButton() {
        globalComplete.setDisable(false);
    }

    public void disableStartButton() {
        globalStart.setDisable(true);
    }

    public void removeLastComplete() {
        completeEventHandlers.removeLast();
    }

    public void enableAddTaskButton() {
        addTaskButton.setDisable(false);
    }

    public void disableSubtaskButton() {
        globalSubtask.setDisable(true);
    }

    public void disableCompleteButton() {
        globalComplete.setDisable(true);
    }
}
