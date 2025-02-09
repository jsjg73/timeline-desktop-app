package org.example;

import javafx.scene.control.Button;

public class TaskButtons {
    public static final String taskButtonId= "addTaskButton";
    public static final String startButtonId= "globalStartButton";
    public static final String completeButtonId = "globalCompleteButton";
    public static final String subtaskButtonId= "globalSubtaskButton";

    public final Button addTaskButton = new Button("Add Task");
    public final Button globalStart = new Button("Start");
    public final Button globalComplete = new Button("Complete");
    public final Button globalSubtask = new Button("Subtask");

    public TaskButtons() {
        addTaskButton.setId(taskButtonId);

        globalStart.setId(startButtonId);
        globalStart.setDisable(true);

        globalComplete.setId(completeButtonId);
        globalComplete.setDisable(true);

        globalSubtask.setId(subtaskButtonId);
        globalSubtask.setDisable(true);
    }
}
