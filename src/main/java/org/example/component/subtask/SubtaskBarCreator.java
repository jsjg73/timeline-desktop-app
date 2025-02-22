package org.example.component.subtask;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.example.component.task.TaskLabel;
import org.example.component.task.TaskRectangle;
import org.example.event.command.CreateSubTaskCommand;

public class SubtaskBarCreator {

    private static final String PLACE_HOLDER = "새 하위 업무";

    public static StackPane createSubTaskBarWithLabel(CreateSubTaskCommand c) {

        Rectangle rect = TaskRectangle.create(c.subtaskId(), c.baseX(), currentY(c.baseY(), c.depth()));
        Label label = TaskLabel.create(c.subtaskId(), PLACE_HOLDER);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(c.baseX());
        stackPane.setLayoutY(currentY(c.baseY(), c.depth()));

        stackPane.setId(c.subtaskId());
        return stackPane;
    }

    private static int currentY(int y, int depth) {
        return y + depth * 50;
    }
}
