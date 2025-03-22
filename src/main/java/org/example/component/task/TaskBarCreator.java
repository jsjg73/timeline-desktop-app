package org.example.component.task;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TaskBarCreator {
    public StackPane createTaskBar(String taskId, String taskName, int y) {
        final int x = 50;
        StackPane stackPane = new StackPane();

        Rectangle rect = TaskRectangle.create(taskId, x, y);
        rect.setFill(Color.YELLOW);
        Label label = TaskLabel.create(taskId, taskName);
        label.setTextFill(Color.BLACK);

        stackPane.getChildren().addAll(rect, label);

        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);
        stackPane.setId(taskId);

        return stackPane;
    }
}
