package org.example.event;

import javafx.scene.layout.StackPane;

public interface TaskHandler {
    void updateBaseY();
    void updateBaseX(int x);
    void drawBar(StackPane taskBar);
}
