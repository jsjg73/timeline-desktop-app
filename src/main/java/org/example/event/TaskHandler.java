package org.example.event;

import javafx.scene.layout.StackPane;

public interface TaskHandler {
    void updateBaseY();
    void drawBar(StackPane taskBar);
    String taskBarId();
}
