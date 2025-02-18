package org.example.event;

import javafx.scene.layout.StackPane;

public interface TaskHandler {
    RootTask findRoot();
    void updateBaseX(int x);
    void drawBar(StackPane taskBar);
}
