package org.example.event;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;

public interface TaskHandler {
    int nextTaskY();
    void plusNextTaskY(int value);
    StackPane getTaskBar();
    SubTaskEvent subtaskEvent();
    EventHandler<ActionEvent> completeEvent();
}
