package org.example.event;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.example.component.task.TaskBarCreator;
import org.example.global.button.ButtonLocator;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender implements TaskHandler, RootTaskAppender {

    private final TaskBarCreator taskBarCreator;
    private final AtomicInteger barCount = new AtomicInteger();
    private final Pane taskPane;
    private int baseY;

    public TaskAppender(TaskBarCreator taskBarCreator, Pane taskPane) {
        this.taskBarCreator = taskBarCreator;
        this.taskPane = taskPane;
        this.baseY = 50;
    }

    public void drawTaskBar(String taskName) {
        // 작업 막대 생성
        StackPane taskBar = taskBarCreator.createTaskBar(taskId(), taskName, baseY);
        ButtonLocator.handlerAfterCreateTask(taskId(), taskBar, this, baseY, 1);

        // 작업 패널에 추가
        this.appendNewTask(taskBar);

        updateBaseY();
        barCount.incrementAndGet();

        ButtonLocator.disableTaskButton();
        ButtonLocator.enableStartButton();
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
