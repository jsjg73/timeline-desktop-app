package org.example.component.subtask;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.event.command.CreateSubTaskCommand;

public class SubtaskBarCreator {

    private static final String PLACE_HOLDER = "새 하위 업무";

    public static StackPane createSubTaskBarWithLabel(CreateSubTaskCommand c) {

        Rectangle rect = createSubtaskBar(
            c.subtaskId(), c.baseX(), currentY(c.baseY(), c.depth()), c.width()
        );
        Label label = createSubtaskLabel(c.subtaskId(), PLACE_HOLDER);

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

    private static Rectangle createSubtaskBar(String id, int x, int y, int width) {
        final int height = 30;
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.BLACK);
        rect.setId(id + "-rect");
        return rect;
    }

    private static Label createSubtaskLabel(String id, String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId(id + "-label");
        return label;
    }
}
