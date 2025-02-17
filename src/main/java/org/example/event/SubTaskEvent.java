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

    private final AtomicInteger subtaskBarCount = new AtomicInteger();
    private final TaskButtons taskButtons;
    private final TaskHandler parent;
    private int baseX;
    private final int baseY;
    private final int depth;
    private StackPane stackPane;
    private int xPadding;

    public SubTaskEvent(
            final TaskButtons taskButtons,
            final TaskHandler parent,
            int baseX,
            int baseY,
            int depth) {
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

    public void addSubtask(String subtaskName) {
        final int width = 70;
        StackPane subtaskPane = createSubTaskBarWithLabel(subtaskName, baseX, baseY + depth * 50, width, 30);
        this.stackPane = subtaskPane;

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

    @Override
    public String taskBarId() {
        return stackPane.getId();
    }

    private StackPane createSubTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        Rectangle rect = createSubtaskBar(x, y, width, height, Color.BLUE);
        Label label = createSubtaskLabel(taskName);

        taskButtons.handlerAfterCreateSubtask(rect, label, this,
                x + 30, baseY, depth + 1);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        stackPane.setId(parentTaskBarId() + "-" + subtaskBarCount.get());
        return stackPane;
    }

    private String parentTaskBarId() {
        return parent.taskBarId();
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
