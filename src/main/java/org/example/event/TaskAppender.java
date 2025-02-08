package org.example.event;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.TaskButtons;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskAppender {

    private AtomicInteger barCount = new AtomicInteger();
    private final Pane taskPane;
    private final TaskButtons taskButtons;
    private int nextTaskY = 50;

    public TaskAppender(TaskButtons taskButtons, Pane taskPane) {
        this.taskPane = taskPane;
        this.taskButtons = taskButtons;
    }

    public void addTask(String taskName) {

        int taskY;
        int indentLevel = 0; // 들여쓰기 수준 (depth)

        // 부모가 없을 경우 독립 작업으로 기본 위치 사용
        taskY = nextTaskY;
        nextTaskY += 50; // 다음 부모 작업 위치 갱신

        // 작업 막대 생성
        StackPane taskBar = createTaskBarWithLabel(taskName, 50 + (indentLevel * 30), taskY, 200, 30);



        Button startButton = createButton("Start", 260 + (indentLevel * 30), taskY);
        Button completeButton = createButton("Complete", 320 + (indentLevel * 30), taskY);
        Button subtaskButton = createButton("Subtask", 400 + (indentLevel * 30), taskY);

        // 작업 핸들러 추가
        attachTaskHandlers(taskBar, startButton, completeButton, subtaskButton);

        // 작업 패널에 추가
        taskPane.getChildren().addAll(taskBar, startButton, completeButton, subtaskButton);
    }
    private Label createLabel(String taskName) {
        Label label = new Label(taskName);
        label.setTextFill(Color.WHITE);
        label.setId("new-task-bar-label-"+barCount.get());
        return label;
    }

    // 작업 막대를 생성하는 메서드
    private Rectangle createTaskBar(int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId("new-task-bar-rect-"+barCount.get());
        return rect;
    }

    // 작업 막대와 라벨을 겹쳐서 생성
    private StackPane createTaskBarWithLabel(String taskName, int x, int y, int width, int height) {
        Rectangle rect = createTaskBar(x, y, width, height, Color.BLUE);
        Label label = createLabel(taskName);

        taskButtons.globalStart.setOnAction(
            new GlobalStartButtonEventHandler(rect, label, taskButtons)
        );

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, label);
        stackPane.setLayoutX(x);
        stackPane.setLayoutY(y);

        int idx = barCount.get();
        System.out.println("new-task-bar-" + idx);
        stackPane.setId("new-task-bar-" + idx);

        barCount.incrementAndGet();
        return stackPane;
    }

    // 버튼을 생성하는 메서드
    private Button createButton(String text, int x, int y) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    // 작업 이벤트 핸들러 추가
    private void attachTaskHandlers(StackPane taskBar,
                                    Button startButton,
                                    Button completeButton,
                                    Button subtaskButton) {
        Rectangle rect = (Rectangle) taskBar.getChildren().get(0);
        Label label = (Label) taskBar.getChildren().get(1);

        completeButton.setOnAction(e -> {
            rect.setFill(Color.GRAY);
            label.setTextFill(Color.WHITE);
            startButton.setDisable(false);
        }); // 작업 완료 시 색상 변경

        subtaskButton.setOnAction(new TempAddSubTaskEvent());
    }
}
