package org.example.event;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender {

    private AtomicInteger barCount = new AtomicInteger();
    private AtomicInteger subtaskBarCount = new AtomicInteger();
    private final Pane taskPane;
    private final TaskButtons taskButtons;
    private int nextTaskY;
    private int indentLevel;

    public TaskAppender(TaskButtons taskButtons, Pane taskPane, int nextTaskY, int indentLevel) {
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
        this. nextTaskY = nextTaskY;
        this.indentLevel = indentLevel;
    }

    public void addTask(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = createTaskBarWithLabel(taskName, 50 + (indentLevel * 30), nextTaskY, 200, 30);

        // 작업 패널에 추가
        taskPane.getChildren().addAll(taskBar);
        nextTaskY += 50; // 다음 부모 작업 위치 갱신
    }

    private Label createLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId("new-task-bar-label-"+barCount.get());
        return label;
    }

    // 작업 막대를 생성하는 메서드
    private Rectangle createTaskBar(int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId("new-task-bar-rect-"+barCount.get());
        return rect;
    }

    // 작업 막대와 라벨을 겹쳐서 생성
    private StackPane createTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        Rectangle rect = createTaskBar(x, y, width, height, Color.BLUE);
        Label label = createLabel(taskName);

        taskButtons.globalStart.setOnAction(
            new GlobalStartButtonEventHandler(rect, label, taskButtons)
        );

        taskButtons.globalComplete.setOnAction(
            new GlobalStopButtonEventHandler(rect, label, taskButtons)
        );

        taskButtons.globalSubtask.setOnAction(
            new TempAddSubTaskEvent(this)
        );

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        int idx = barCount.get();
        stackPane.setId("new-task-bar-" + idx);

        barCount.incrementAndGet();
        return stackPane;
    }

    public void addSubtask(String subtaskName) {
        int subtaskIndentLevel = indentLevel + 1;
        StackPane subtaskPane = createSubTaskBarWithLabel(subtaskName, 50 + (subtaskIndentLevel * 30), nextTaskY, 200, 30);

        taskPane.getChildren().addAll(subtaskPane);
        nextTaskY += 50;
    }

    private StackPane createSubTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        Rectangle rect = createSubtaskBar(x, y, width, height, Color.BLUE);
        Label label = createSubtaskLabel(taskName);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        int idx = parentTaskId();
        stackPane.setId("new-subtask-bar-" + idx + "-" + subtaskBarCount.get());

        subtaskBarCount.incrementAndGet();
        return stackPane;
    }

    private int parentTaskId() {
        return barCount.get() - 1;
    }

    private Rectangle createSubtaskBar(int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId("new-subtask-bar-" + parentTaskId() + "-" + subtaskBarCount.get() + "-rect");
        return rect;
    }

    private Label createSubtaskLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId("new-subtask-bar-" + parentTaskId() + "-" + subtaskBarCount.get() +"-label");
        return label;
    }
}
