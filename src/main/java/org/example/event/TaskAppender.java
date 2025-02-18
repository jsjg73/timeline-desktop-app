package org.example.event;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;
import org.example.component.task.TaskLabel;
import org.example.component.task.TaskRectangle;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender implements TaskHandler{

    private final AtomicInteger barCount = new AtomicInteger();
    private final Pane taskPane;
    private final TaskButtons taskButtons;
    private int baseY;

    public TaskAppender(TaskButtons taskButtons, Pane taskPane) {
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
        this.baseY = 50;
    }

    public void drawTaskBar(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = createTaskBar(taskName);

        // 작업 패널에 추가
        this.drawBar(taskBar);

        updateBaseY();
        barCount.incrementAndGet();

        taskButtons.disableTaskButton();
        taskButtons.enableStartButton();
    }

    public void updateBaseY() {
        baseY += 50;
    }

    @Override
    public void updateBaseX(int x) {    }

    // 작업 막대와 라벨을 겹쳐서 생성
    private StackPane createTaskBar(String taskName) {
        final int x = 50;
        final int y = baseY;
        StackPane stackPane = new StackPane();

        Rectangle rect = TaskRectangle.create(taskId(), x, y);
        Label label = TaskLabel.create(taskId(), taskName);

        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);
        stackPane.setId(taskId());

        taskButtons.handlerAfterCreateTask(taskId(), stackPane, this, baseY, 1);

        return stackPane;
    }

    private String taskId() {
        return "new-task-bar-" + barCount.get();
    }

    @Override
    public void drawBar(StackPane taskBar) {
        taskPane.getChildren().addAll(taskBar);
    }

}
