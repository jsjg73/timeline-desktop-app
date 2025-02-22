package org.example.event.command;

public record CreateSubTaskCommand(
        String subtaskId,
        int baseX,
        int baseY,
        int depth,
        int width) {
}
