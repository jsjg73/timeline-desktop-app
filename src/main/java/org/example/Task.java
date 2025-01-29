package org.example;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

// 작업 클래스
public class Task {
    public String name;
    public StackPane taskBar;
    public Button startButton;
    public Button completeButton;
    public int y; // 현재 작업의 Y 좌표
    public int nextChildY; // 다음 자식 작업의 Y 좌표
    public int indentLevel; // 작업의 들여쓰기 수준

    public Task(String name, StackPane taskBar, Button startButton, Button completeButton, int y, int indentLevel) {
        this.name = name;
        this.taskBar = taskBar;
        this.startButton = startButton;
        this.completeButton = completeButton;
        this.y = y;
        this.nextChildY = y + 50; // 자식 작업은 부모 아래 50px
        this.indentLevel = indentLevel; // 부모보다 한 단계 더 들여쓰기
    }
}