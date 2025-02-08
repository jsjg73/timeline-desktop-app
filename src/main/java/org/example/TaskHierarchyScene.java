package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.event.AddTaskEvent;
import org.example.event.ButtonDisableEvent;
import org.example.event.TaskAppender;

public class TaskHierarchyScene {

    private final Pane taskPane = new Pane(); // 작업이 표시될 패널

    public Scene draw() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);

        // 작업 추가를 위한 입력 필드와 버튼
        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Enter Task Name");

        TextField parentTaskField = new TextField();
        parentTaskField.setPromptText("Enter Parent Task (optional)");

        Button addTaskButton = new Button("Add Task");
        addTaskButton.setId("addTaskButton");

        // 작업 추가 버튼 클릭 이벤트
        addTaskButton.setOnAction(
            new ButtonDisableEvent(
                addTaskButton,
                new AddTaskEvent(taskNameField,
                    new TaskAppender(taskPane, addTaskButton))
            )
        );

        Button globalStart = new Button("Start");
        Button globalStop = new Button("Stop");

        // 기본 레이아웃 설정
        root.getChildren().addAll(taskNameField, parentTaskField,
                addTaskButton,
                globalStart,
                globalStop,
                taskPane);
        return new Scene(root, 800, 600);
    }
}
