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

    public final Button addTaskButton = new Button("Add Task");
    public final Button globalStart = new Button("Start");
    public final Button globalComplete = new Button("Complete");
    public final Button globalSubtask = new Button("Subtask");

    private final List<EventHandler<ActionEvent>> completeEventHandlers = new ArrayList<>();
    private final List<EventHandler<ActionEvent>> subtaskEventHandlers = new ArrayList<>();

    public TaskButtons() {
        addTaskButton.setId(taskButtonId);

        globalStart.setId(startButtonId);
        globalStart.setDisable(true);

        globalComplete.setId(completeButtonId);
        globalComplete.setDisable(true);

        globalSubtask.setId(subtaskButtonId);
        globalSubtask.setDisable(true);
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
                                       int indent) {

        this.globalStart.setOnAction(
            new GlobalStartButtonEventHandler(rect, label, this)
        );

        completeEventHandlers.add(new GlobalStopButtonEventHandler(rect, label, this));
        this.globalComplete.setOnAction(completeEventHandlers.getLast());

        subtaskEventHandlers.add(new SubTaskEvent(this, parent, indent));
        this.globalSubtask.setOnAction(
            subtaskEventHandlers.getLast()
        );
    }

    public void handlerAfterCreateSubtask(
            Rectangle rect, Label label,
            TaskHandler parent,
            int indent
    ) {

        globalStart.setOnAction(
            e -> {
                rect.setFill(Color.YELLOW);
                label.setTextFill(Color.BLACK);

                globalStart.setDisable(true);
                globalComplete.setDisable(false);
                globalSubtask.setDisable(false);
            }
        );

        EventHandler<ActionEvent> completeEventHandler = e -> {
            subtaskEventHandlers.removeLast();
            completeEventHandlers.removeLast();

            rect.setFill(Color.GRAY);
            label.setTextFill(Color.WHITE);

            globalSubtask.setDisable(false);
            globalSubtask.setOnAction(
                subtaskEventHandlers.getLast()
            );

            globalComplete.setOnAction(
                completeEventHandlers.getLast()
            );
        };

        completeEventHandlers.add(completeEventHandler);
        globalComplete.setOnAction(completeEventHandler);


        subtaskEventHandlers.add(new SubTaskEvent(this, parent, indent));
        globalSubtask.setOnAction(
            subtaskEventHandlers.getLast()
        );
    }

    public void removeLastComplete() {
        completeEventHandlers.removeLast();
    }
}
