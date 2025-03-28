package org.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.example.global.button.ButtonLocator;

public class TaskHierarchyScene {

    public TaskHierarchyScene() {
        taskPane = new Pane();
    }

    public static Pane taskPane;// 작업이 표시될 패널

    public Scene draw() {
        VBox root = new VBox(10);
        root.setAlignment(Pos.TOP_CENTER);

        // 작업 추가를 위한 입력 필드와 버튼
        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Enter Task Name");

        TextField parentTaskField = new TextField();
        parentTaskField.setPromptText("Enter Parent Task (optional)");

        // 기본 레이아웃 설정
        root.getChildren().addAll(taskNameField, parentTaskField);
        root.getChildren().add(taskPane);

        ButtonLocator.init(root, taskNameField);

        return new Scene(root, 800, 600);
    }
}
