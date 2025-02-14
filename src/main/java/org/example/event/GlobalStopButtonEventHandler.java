package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

public class GlobalStopButtonEventHandler implements EventHandler<ActionEvent> {

    private final Rectangle taskBarRectangle;
    private final Label taskBarLabel;
    private final TaskButtons taskButtons;

    public GlobalStopButtonEventHandler(final Rectangle taskBarRectangle,
                                         final Label taskBarLabel,
                                         final TaskButtons taskButtons) {
        this.taskBarRectangle = taskBarRectangle;
        this.taskBarLabel = taskBarLabel;
        this.taskButtons = taskButtons;
    }
    @Override
    public void handle(ActionEvent actionEvent) {
        taskButtons.removeLastComplete();
        taskBarRectangle.setFill(Color.GRAY);
        taskBarLabel.setTextFill(Color.WHITE);

        taskButtons.disableCompleteButton();
        taskButtons.enableAddTaskButton();
        taskButtons.disableSubtaskButton();
    }
}
