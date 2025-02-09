package org.example.event;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender implements TaskHandler{

    private final AtomicInteger barCount = new AtomicInteger();
    private final Pane taskPane;
    private final TaskButtons taskButtons;
    private final int indentLevel = 0;
    private int nextTaskY;

    private StackPane rootTask;

    public TaskAppender(TaskButtons taskButtons, Pane taskPane, int nextTaskY) {
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
        this. nextTaskY = nextTaskY;
    }

    public void addTask(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = createTaskBarWithLabel(taskName, 50, nextTaskY, 200, 30);

        // 작업 패널에 추가
        taskPane.getChildren().addAll(taskBar);
        nextTaskY += 50; // 다음 부모 작업 위치 갱신
    }

    private Label createLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId(getTaskBar().getId() + "-label");
        return label;
    }

    // 작업 막대를 생성하는 메서드
    private Rectangle createTaskBar(int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId(getTaskBar().getId() + "-rect");
        return rect;
    }

    // 작업 막대와 라벨을 겹쳐서 생성
    private StackPane createTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        StackPane stackPane = new StackPane();
        rootTask = stackPane;
        stackPane.setId("new-task-bar-" + barCount.get());

        Rectangle rect = createTaskBar(x, y, width, height, Color.BLUE);
        Label label = createLabel(taskName);

        taskButtons.globalStart.setOnAction(
            new GlobalStartButtonEventHandler(rect, label, taskButtons)
        );

        taskButtons.globalComplete.setOnAction(
            new GlobalStopButtonEventHandler(rect, label, taskButtons)
        );

        taskButtons.globalSubtask.setOnAction(
            new SubTaskEvent(taskButtons, this, indentLevel + 1, taskPane)
        );

        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        barCount.incrementAndGet();
        return stackPane;
    }

    @Override
    public int nextTaskY() {
        return this.nextTaskY;
    }

    @Override
    public void plusNextTaskY(int value) {
        nextTaskY+=value;
    }

    public StackPane getTaskBar() {
        return rootTask;
    }
}
