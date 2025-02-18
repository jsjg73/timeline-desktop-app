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
    private int nextY;
    private StackPane rootTask;

    public TaskAppender(TaskButtons taskButtons, Pane taskPane) {
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
        this.baseY = nextY = 50;
    }

    public void drawTaskBar(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = createTaskBar(taskName);

        // 작업 패널에 추가
        this.drawBar(taskBar);

//        nextTaskY += 50; // 다음 부모 작업 위치 갱신
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
        rootTask = stackPane;
        stackPane.setId("new-task-bar-" + barCount.get());

        Rectangle rect =
            TaskRectangle.create(
                rootTask.getId(),
                x, y, 70, 30,
                Color.BLUE
            );
        Label label = TaskLabel.create(rootTask.getId(), taskName);

        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        taskButtons.handlerAfterCreateTask(
                rootTask.getId(),
            rect, label, this, baseY,1
        );

        return stackPane;
    }

    @Override
    public void drawBar(StackPane taskBar) {
        taskPane.getChildren().addAll(taskBar);
    }

}
