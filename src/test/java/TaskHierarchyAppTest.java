import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.TaskHierarchyScene;
import org.example.global.button.TestButtonLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.ColorMatchers;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;

import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
class TaskHierarchyAppTest {

    @Start
    private void start(Stage stage) {
        TaskHierarchyScene hierarchyScene = new TaskHierarchyScene();
        stage.setTitle("Task Hierarchy Example");
        stage.setScene(hierarchyScene.draw());
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    final String addTaskButton = "#" + TestButtonLocator.taskButtonId;
    final String completeButton = "#" + TestButtonLocator.completeButtonId;
    final String subtaskButton = "#" + TestButtonLocator.subtaskButtonId;

    @Test
    void first_test() {
        verifyThat(addTaskButton, LabeledMatchers.hasText("Add Task"));

        verifyThat(completeButton, LabeledMatchers.hasText("Complete"));
        verifyThat(completeButton, NodeMatchers.isDisabled());

        verifyThat(subtaskButton, LabeledMatchers.hasText("Subtask"));
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    private FxRobot robot;

    @Test
    void when_button_is_clicked_bar_is_created() {
        initiateNewTask();

        assertTaskProgressDisplayed(0);

        assertAdditionalTaskInitiationBlocked();
        assertTaskCompletionAvailable();
        assertSubTaskCreationAvailable();
    }

    private void assertTaskInitiationAvailable() {
        verifyThat(addTaskButton, NodeMatchers.isEnabled());
    }

    private void assertSubTaskCreationAvailable() {
        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    private void assertTaskCompletionAvailable() {
        verifyThat(completeButton, NodeMatchers.isEnabled());
    }

    private void assertAdditionalTaskInitiationBlocked() {
        verifyThat(addTaskButton, NodeMatchers.isDisabled());
    }

    private void assertTaskCompletionBlocked() {
        verifyThat(completeButton, NodeMatchers.isDisabled());
    }

    private void assertSubTaskCreationBlocked() {
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    private void assertTaskProgressDisplayed(int index) {
        final String taskId = "#new-task-bar-" + index;
        verifyThat(taskId, NodeMatchers.isVisible());

        assertTaskAppearance(taskId, Color.YELLOW, Color.BLACK);
    }

    private void assertTaskAppearance(String taskId, Color backgroundColor, Color fontColor) {
        Rectangle rect = lookup(taskId + "-rect").query();
        Label label = lookup(taskId + "-label").query();
        verifyThat((Color) rect.getFill(), ColorMatchers.isColor(backgroundColor));
        verifyThat((Color) label.getTextFill(), ColorMatchers.isColor(fontColor));
    }

    private void initiateNewTask() {
        robot.clickOn(addTaskButton);
    }

    @Test
    void when_stop_button_is_clicked_bar_color_rollback() {
        initiateNewTask();
        completeTask();

        assertTaskCompletionBlocked();
        assertTaskInitiationAvailable();

        final String taskId = "#new-task-bar-0";
        assertTaskAppearance(taskId, Color.GRAY, Color.WHITE);
        assertSubTaskCreationBlocked();
    }

    private void completeTask() {
        robot.clickOn(completeButton);
    }

    @Test
    void when_add_task_button_is_twice_clicked(FxRobot robot) {
        robot.clickOn(addTaskButton);
        robot.clickOn(completeButton);

        robot.clickOn(addTaskButton);

        verifyThat("#new-task-bar-1", NodeMatchers.isVisible());
        verifyThat(addTaskButton, NodeMatchers.isDisabled());

        verifyThat(completeButton, NodeMatchers.isEnabled());
        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    @Test
    void when_start_button_is_clicked_subtask_button_is_enabled(FxRobot robot) {
        robot.clickOn(addTaskButton);

        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}