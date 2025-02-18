package org.example.component.task;

import  javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TaskRectangle {

    // 작업 막대를 생성하는 메서드
    public static Rectangle create(String idPrefix, int x, int y) {
        Rectangle rect = new Rectangle(x, y, 70, 30);
        rect.setFill(Color.BLUE);
        rect.setStroke(Color.BLACK);
        rect.setId(idPrefix + "-rect");
        return rect;
    }
}
