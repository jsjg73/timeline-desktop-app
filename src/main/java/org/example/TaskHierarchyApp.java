package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class TaskHierarchyApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        TaskHierarchyScene hierarchyScene = new TaskHierarchyScene();

        primaryStage.setTitle("Task Hierarchy Example");
        primaryStage.setScene(hierarchyScene.draw());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(TaskHierarchyApp.class);
    }
}
