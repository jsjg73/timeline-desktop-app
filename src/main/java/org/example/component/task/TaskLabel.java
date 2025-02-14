package org.example.component.task;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class TaskLabel {
    public static Label create(String idPrefix, String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId(idPrefix + "-label");
        return label;
    }
}
