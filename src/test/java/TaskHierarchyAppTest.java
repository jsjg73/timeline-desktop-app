import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.example.TaskButtons;
import org.example.TaskHierarchyScene;
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
        stage.show();
    }

    final String buttonId = "#" + TaskButtons.taskButtonId;
    final String startButton = "#" + TaskButtons.startButtonId;
    final String completeButton = "#" + TaskButtons.completeButtonId;
    final String subtaskButton = "#" + TaskButtons.subtaskButtonId;

    @Test
    void first_test() {
        verifyThat(buttonId, LabeledMatchers.hasText("Add Task"));

        verifyThat(startButton, LabeledMatchers.hasText("Start"));
        verifyThat(startButton, NodeMatchers.isDisabled());

        verifyThat(completeButton, LabeledMatchers.hasText("Complete"));
        verifyThat(completeButton, NodeMatchers.isDisabled());

        verifyThat(subtaskButton, LabeledMatchers.hasText("Subtask"));
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    @Test
    void when_button_is_clicked_bar_is_created(FxRobot robot) {
        robot.clickOn(buttonId);

        verifyThat("#new-task-bar-0", NodeMatchers.isVisible());
        verifyThat(buttonId, NodeMatchers.isDisabled());

        verifyThat(startButton, NodeMatchers.isEnabled());
        verifyThat(completeButton, NodeMatchers.isDisabled());
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    @Test
    void when_start_button_is_clicked_bar_color_is_changed(FxRobot robot) {
        robot.clickOn(buttonId);
        verifyThat("#new-task-bar-0", NodeMatchers.isVisible());

        robot.clickOn(startButton);

        verifyThat(buttonId, NodeMatchers.isDisabled());

        verifyThat(startButton, NodeMatchers.isDisabled());
        Rectangle rect = lookup("#new-task-bar-0-rect").query();
        verifyThat((Color) rect.getFill(), ColorMatchers.isColor(Color.YELLOW));

        Label label = lookup("#new-task-bar-0-label").query();
        verifyThat((Color) label.getTextFill(), ColorMatchers.isColor(Color.BLACK));

        verifyThat(completeButton, NodeMatchers.isEnabled());
        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    @Test
    void when_stop_button_is_clicked_bar_color_rollback(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(startButton);

        robot.clickOn(completeButton);

        verifyThat(completeButton, NodeMatchers.isDisabled());
        verifyThat(buttonId, NodeMatchers.isEnabled());

        Rectangle rect = lookup("#new-task-bar-0-rect").query();
        verifyThat((Color) rect.getFill(), ColorMatchers.isColor(Color.GRAY));

        Label label = lookup("#new-task-bar-0-label").query();
        verifyThat((Color) label.getTextFill(), ColorMatchers.isColor(Color.WHITE));

        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    @Test
    void when_add_task_button_is_twice_clicked(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(startButton);
        robot.clickOn(completeButton);

        robot.clickOn(buttonId);

        verifyThat("#new-task-bar-1", NodeMatchers.isVisible());
        verifyThat(buttonId, NodeMatchers.isDisabled());

        verifyThat(startButton, NodeMatchers.isEnabled());
        verifyThat(completeButton, NodeMatchers.isDisabled());
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    @Test
    void when_start_button_is_clicked_subtask_button_is_enabled(FxRobot robot) {
        robot.clickOn(buttonId);

        robot.clickOn(startButton);

        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}