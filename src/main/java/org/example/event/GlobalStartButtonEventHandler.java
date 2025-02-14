package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

public class GlobalStartButtonEventHandler implements EventHandler<ActionEvent> {

    private final Rectangle taskBarRectangle;
    private final Label taskBarLabel;
    private final TaskButtons taskButtons;

    public GlobalStartButtonEventHandler(final Rectangle taskBarRectangle,
                                         final Label taskBarLabel,
                                         final TaskButtons taskButtons) {
        this.taskBarRectangle = taskBarRectangle;
        this.taskBarLabel = taskBarLabel;
        this.taskButtons = taskButtons;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        taskBarRectangle.setFill(Color.YELLOW);
        taskBarLabel.setTextFill(Color.BLACK);

        taskButtons.disableStartButton();
        taskButtons.enableCompleteButton();
        taskButtons.enableSubtaskButton();
    }
}
