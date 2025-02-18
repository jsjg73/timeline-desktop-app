package org.example.event;

import javafx.scene.layout.StackPane;

public interface RootTaskAppender {
    void updateBaseY();
    void appendNewTask(StackPane taskBar);
}
