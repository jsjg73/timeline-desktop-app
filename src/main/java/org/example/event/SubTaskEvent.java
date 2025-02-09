package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

import java.util.concurrent.atomic.AtomicInteger;

public class SubTaskEvent implements EventHandler<ActionEvent>, TaskHandler{

    private final AtomicInteger subtaskBarCount = new AtomicInteger();
    private final TaskButtons taskButtons;
    private final TaskHandler parent;
    private final int indent;
    private final Pane taskPane;
    private StackPane stackPane;
    private SubTaskEvent subTaskEvent;

    public SubTaskEvent(
            final TaskButtons taskButtons,
            final TaskHandler parent,
            int indent, Pane taskPane) {
        this.taskButtons = taskButtons;
        this.parent = parent;
        this.indent = indent;
        this.taskPane = taskPane;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        addSubtask("새 하위 업무");

        taskButtons.globalStart.setDisable(false);
        taskButtons.globalComplete.setDisable(true);
        taskButtons.globalSubtask.setDisable(true);
    }

    public void addSubtask(String subtaskName) {
        StackPane subtaskPane = createSubTaskBarWithLabel(subtaskName, 50 + (indent * 30), nextTaskY(), 200, 30);
        this.stackPane = subtaskPane;

        taskPane.getChildren().addAll(subtaskPane);

        plusNextTaskY(50);
    }

    @Override
    public int nextTaskY() {
        return parent.nextTaskY();
    }

    @Override
    public void plusNextTaskY(int value) {
        parent.plusNextTaskY(value);
    }

    @Override
    public StackPane getTaskBar() {
        return stackPane;
    }

    @Override
    public SubTaskEvent subtaskEvent() {
        return this.subTaskEvent;
    }

    private StackPane createSubTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        Rectangle rect = createSubtaskBar(x, y, width, height, Color.BLUE);
        Label label = createSubtaskLabel(taskName);

        taskButtons.globalStart.setOnAction(
            e -> {
                rect.setFill(Color.YELLOW);
                label.setTextFill(Color.BLACK);

                taskButtons.globalStart.setDisable(true);
                taskButtons.globalComplete.setDisable(false);
                taskButtons.globalSubtask.setDisable(false);
            }
        );

        taskButtons.globalComplete.setOnAction(
            e -> {
                rect.setFill(Color.GRAY);
                label.setTextFill(Color.WHITE);

                taskButtons.globalSubtask.setDisable(false);
                taskButtons.globalSubtask.setOnAction(
                    parent.subtaskEvent()
                );

                taskButtons.globalComplete.setOnAction(
                        new GlobalStopButtonEventHandler(
                                (Rectangle) parent.getTaskBar().getChildren().get(0),
                                (Label) parent.getTaskBar().getChildren().get(1),
                                taskButtons)
                );
            }
        );

        subTaskEvent = new SubTaskEvent(taskButtons, this, indent + 1, taskPane);
        taskButtons.globalSubtask.setOnAction(subTaskEvent);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        stackPane.setId(parentTaskBarId() + "-" + subtaskBarCount.get());

        subtaskBarCount.incrementAndGet();
        return stackPane;
    }

    private String parentTaskBarId() {
        return parent.getTaskBar().getId();
    }

    private Rectangle createSubtaskBar(int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId(parentTaskBarId() + "-" + subtaskBarCount.get() + "-rect");
        return rect;
    }

    private Label createSubtaskLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId(parentTaskBarId() + "-" + subtaskBarCount.get() +"-label");
        return label;
    }
}
