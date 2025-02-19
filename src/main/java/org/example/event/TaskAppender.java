package org.example.event;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.TaskButtons;
import org.example.component.task.TaskBarCreator;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender implements TaskHandler, RootTaskAppender {

    private final TaskBarCreator taskBarCreator;
    private final AtomicInteger barCount = new AtomicInteger();
    private final Pane taskPane;
    private final TaskButtons taskButtons;
    private int baseY;

    public TaskAppender(TaskBarCreator taskBarCreator, TaskButtons taskButtons, Pane taskPane) {
        this.taskBarCreator = taskBarCreator;
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
        this.baseY = 50;
    }

    public void drawTaskBar(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = taskBarCreator.createTaskBar(taskId(), taskName, baseY);
        taskButtons.handlerAfterCreateTask(taskId(), taskBar, this, baseY, 1);

        // 작업 패널에 추가
        this.appendNewTask(taskBar);

        updateBaseY();
        barCount.incrementAndGet();

        taskButtons.disableTaskButton();
        taskButtons.enableStartButton();
    }

    public void updateBaseY() {
        baseY += 50;
    }

    @Override
    public RootTaskAppender findRoot() {
        return this;
    }

    @Override
    public void updateBaseX(int x) {    }

    private String taskId() {
        return "new-task-bar-" + barCount.get();
    }

    @Override
    public void appendNewTask(StackPane taskBar) {
        taskPane.getChildren().addAll(taskBar);
    }

}
