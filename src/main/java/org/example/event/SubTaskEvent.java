package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import org.example.TaskHierarchyScene;
import org.example.component.subtask.SubtaskBarCreator;
import org.example.event.command.AfterCreateSubtaskCommand;
import org.example.event.command.CreateSubTaskCommand;
import org.example.global.button.ButtonLocator;
import org.example.global.button.RootTaskLocator;

import java.util.concurrent.atomic.AtomicInteger;

public class SubTaskEvent implements EventHandler<ActionEvent>, TaskHandler{

    private final String parentId;
    private final AtomicInteger subtaskBarCount = new AtomicInteger();
    private final TaskHandler parent;
    private int baseX;
    private final int baseY;
    private final int depth;
    private final int width = 70;

    public SubTaskEvent(
            final String parentId,
            final TaskHandler parent,
            int baseX,
            int baseY,
            int depth) {
        this.parentId = parentId;
        this.parent = parent;
        this.baseX = baseX;
        this.baseY = baseY;
        this.depth = depth;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        addSubtask();

        ButtonLocator.enableCompleteButton();
        ButtonLocator.enableSubtaskButton();
    }

    private void addSubtask() {
        StackPane newSubtask =
            SubtaskBarCreator.createSubTaskBarWithLabel(buildCommand());

        var afterCreateSubtaskCommand = new AfterCreateSubtaskCommand(subtaskId(), newSubtask, this, baseX + 30, baseY, depth + 1);
        ButtonLocator.handlerAfterCreateSubtask(afterCreateSubtaskCommand);

        appendNewTask(newSubtask);
        updateBaseX(baseX + width + 10);

        if (subtaskBarCount.get() == 0) {
            RootTaskLocator.updateBaseY();
        }

        subtaskBarCount.incrementAndGet();
    }

    private CreateSubTaskCommand buildCommand() {
        return new CreateSubTaskCommand(
            subtaskId(),
            baseX, baseY,
            depth, width
        );
    }

    @Override
    public void updateBaseX(int x) {
        baseX = x;
        parent.updateBaseX(x);
    }

    public void appendNewTask(StackPane newTask) {
        TaskHierarchyScene.taskPane.getChildren().addAll(newTask);
    }

    private String subtaskId() {
        return parentId + "-" + subtaskBarCount.get();
    }
}
