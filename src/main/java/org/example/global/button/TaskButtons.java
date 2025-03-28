package org.example.global.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.component.task.TaskBarCreator;
import org.example.event.*;
import org.example.event.command.AfterCreateSubtaskCommand;

import java.util.ArrayList;
import java.util.List;

class TaskButtons {
    public static final String taskButtonId = "addTaskButton";
    public static final String completeButtonId = "globalCompleteButton";
    public static final String subtaskButtonId = "globalSubtaskButton";

    protected final Button addTaskButton = new Button("Add Task");
    protected final Button globalComplete = new Button("Complete");
    protected final Button globalSubtask = new Button("Subtask");

    protected final List<EventHandler<ActionEvent>> completeEventHandlers = new ArrayList<>();
    protected final List<EventHandler<ActionEvent>> subtaskEventHandlers = new ArrayList<>();

    private TaskButtons() {
    }

    public static TaskButtons init(VBox root, TextField taskNameField) {
        TaskButtons instance = new TaskButtons();
        instance.addTaskButton.setId(taskButtonId);

        instance.addTaskButton.setOnAction(
                new AddTaskEventHandler(
                        taskNameField,
                        new TaskAppender(
                                new TaskBarCreator()
                        )
                )
        );

        instance.globalComplete.setId(completeButtonId);
        instance.disableCompleteButton();

        instance.globalSubtask.setId(subtaskButtonId);
        instance.disableSubtaskButton();

        root.getChildren().addAll(
                instance.addTaskButton,
                instance.globalComplete,
                instance.globalSubtask
        );

        return instance;
    }

    public void disableTaskButton() {
        this.addTaskButton.setDisable(true);
    }


    public void drawnOn(VBox root) {
        root.getChildren().addAll(
                addTaskButton,
                globalComplete,
                globalSubtask
        );
    }

    public void handlerAfterCreateTask(String parentId,
                                       StackPane stackPane,
                                       TaskHandler parent,
                                       int baseY,
                                       int indent) {

        completeEventHandlers.add(new GlobalStopButtonEventHandler(stackPane));
        this.globalComplete.setOnAction(e -> completeEventHandlers.getLast().handle(e));

        subtaskEventHandlers.add(new SubTaskEvent(parentId, parent, 50 + 30, baseY, indent));
        this.globalSubtask.setOnAction(e -> subtaskEventHandlers.getLast().handle(e));
    }

    public void handlerAfterCreateSubtask(
            AfterCreateSubtaskCommand command
    ) {
        final String parentId= command.parentId();
        final StackPane stackPane= command.stackPane();
        final TaskHandler parent= command.parent();
        final int baseX= command.baseX();
        final int baseY= command.baseY();
        final int indent= command.indent();

        final Rectangle rect = (Rectangle) stackPane.getChildren().get(0);
        final Label label = (Label) stackPane.getChildren().get(1);

        EventHandler<ActionEvent> completeEventHandler = e -> {
            subtaskEventHandlers.removeLast();
            completeEventHandlers.removeLast();

            rect.setFill(Color.GRAY);
            label.setTextFill(Color.WHITE);

            enableSubtaskButton();
        };

        completeEventHandlers.add(completeEventHandler);

        subtaskEventHandlers.add(new SubTaskEvent(parentId, parent, baseX, baseY, indent));
    }

    public void enableSubtaskButton() {
        globalSubtask.setDisable(false);
    }

    public void enableCompleteButton() {
        globalComplete.setDisable(false);
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
