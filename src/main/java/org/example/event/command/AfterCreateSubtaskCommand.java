package org.example.event.command;

import javafx.scene.layout.StackPane;
import org.example.event.TaskHandler;

public record AfterCreateSubtaskCommand(
        String parentId,
        StackPane stackPane,
        TaskHandler parent,
        int baseX,
        int baseY,
        int indent
) {
}
