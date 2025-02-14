package org.example.component.task;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TaskRectangle {

    // 작업 막대를 생성하는 메서드
    public static Rectangle create(String idPrefix, int x, int y, int width, int height, Color color) {
        Rectangle rect = new Rectangle(x, y, width, height);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);
        rect.setId(idPrefix + "-rect");
        return rect;
    }
}
