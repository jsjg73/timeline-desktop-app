package org.example.event;

import javafx.scene.layout.StackPane;

public interface TaskHandler {
    int nextTaskY();
    void updateNextTaskY();
    void drawBar(StackPane taskBar);
    String taskBarId();
}
