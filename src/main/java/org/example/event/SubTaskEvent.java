package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

import java.util.concurrent.atomic.AtomicInteger;

public class SubTaskEvent implements EventHandler<ActionEvent>, TaskHandler{

    private final String parentId;
    private final AtomicInteger subtaskBarCount = new AtomicInteger();
    private final TaskButtons taskButtons;
    private final TaskHandler parent;
    private int baseX;
    private final int baseY;
    private final int depth;
    private final int width = 70;

    public SubTaskEvent(
            final String parentId,
            final TaskButtons taskButtons,
            final TaskHandler parent,
            int baseX,
            int baseY,
            int depth) {
        this.parentId = parentId;
        this.taskButtons = taskButtons;
        this.parent = parent;
        this.baseX = baseX;
        this.baseY = baseY;
        this.depth = depth;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        addSubtask("새 하위 업무");

        taskButtons.enableStartButton();
        taskButtons.disableCompleteButton();
        taskButtons.disableSubtaskButton();
    }

    private int currentY() {
        return baseY + depth * 50;
    }

    public void addSubtask(String subtaskName) {
        StackPane subtaskPane = createSubTaskBarWithLabel(subtaskName);

        this.drawBar(subtaskPane);
        updateBaseX(baseX + width + 10);

        if (subtaskBarCount.get() == 0) {
            updateBaseY();
        }
        subtaskBarCount.incrementAndGet();
    }

    public void updateBaseY() {
        parent.updateBaseY();
    }

    @Override
    public void updateBaseX(int x) {
        baseX = x;
        parent.updateBaseX(x);
    }

    @Override
    public void drawBar(StackPane taskBar) {
        parent.drawBar(taskBar);
    }

    private StackPane createSubTaskBarWithLabel(String taskName) {
        Rectangle rect = createSubtaskBar();
        Label label = createSubtaskLabel(taskName);

        taskButtons.handlerAfterCreateSubtask(
                subtaskId(),
                rect, label, this,
                baseX + 30, baseY, depth + 1);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(baseX);
        stackPane.setLayoutY(currentY());

        stackPane.setId(subtaskId());
        return stackPane;
    }

    private String subtaskId() {
        return parentId + "-" + subtaskBarCount.get();
    }

    private Rectangle createSubtaskBar() {
        final int height = 30;
        Rectangle rect = new Rectangle(baseX, currentY(), width, height);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.BLACK);
        rect.setId(subtaskId() + "-rect");
        return rect;
    }

    private Label createSubtaskLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId(subtaskId() + "-label");
        return label;
    }
}
