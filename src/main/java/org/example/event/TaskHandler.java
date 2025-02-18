package org.example.event;

import javafx.scene.layout.StackPane;

public interface TaskHandler {
    RootTaskAppender findRoot();
    void updateBaseX(int x);
}
