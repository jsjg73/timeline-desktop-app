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

import static javafx.scene.paint.Color.*;
import static org.testfx.api.FxAssert.verifyThat;

@ExtendWith(ApplicationExtension.class)
public class SubtaskButtonTest {

    @Start
    public void start(Stage stage) {
        TaskHierarchyScene hierarchyScene = new TaskHierarchyScene();
        stage.setTitle("Task Hierarchy Example");
        stage.setScene(hierarchyScene.draw());
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    final String buttonId = "#" + TestButtonLocator.taskButtonId;
    final String completeButton = "#" + TestButtonLocator.completeButtonId;
    final String subtaskButton = "#" + TestButtonLocator.subtaskButtonId;

    @Test
    void when_subtask_button_is_clicked_subtask_bar_is_created(FxRobot robot) {
        robot.clickOn(buttonId);

        final String rectId = "#new-task-bar-0-0-rect";
        final String labelId = "#new-task-bar-0-0-label";

        robot.clickOn(subtaskButton);

        verifyThat("#new-task-bar-0-0", NodeMatchers.isVisible());

        verifyThat(rectId, NodeMatchers.isVisible());
        verifyThat(labelId, NodeMatchers.isVisible());

        verifyRectColor(rectId, YELLOW);
        verifyLabelTextColor(labelId, BLACK);

        verifyThat(labelId, LabeledMatchers.hasText("새 하위 업무"));

        verifyThat(buttonId, NodeMatchers.isDisabled());
        verifyThat(completeButton, NodeMatchers.isEnabled());
        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }


    @Test
    void when_subtask_is_started_bar_color_is_changed(FxRobot robot) {
        final String rectId = "#new-task-bar-0-0-rect";
        final String labelId = "#new-task-bar-0-0-label";

        robot.clickOn(buttonId);
        robot.clickOn(subtaskButton);

        verifyRectColor(rectId, Color.YELLOW);
        verifyLabelTextColor(labelId, Color.BLACK);

        verifyThat(completeButton, NodeMatchers.isEnabled());
    }

    @Test
    void when_subtask_is_completed(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(subtaskButton);

        final String rectId = "#new-task-bar-0-0-rect";
        final String labelId = "#new-task-bar-0-0-label";

        robot.clickOn(completeButton);

        verifyRectColor(rectId, GRAY);
        verifyLabelTextColor(labelId, Color.WHITE);

        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    @Test
    void when_subtask_is_completed_parent_task_could_be_completed(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(subtaskButton);
        robot.clickOn(completeButton);

        robot.clickOn(completeButton);

        verifyRectColor("#new-task-bar-0-rect", GRAY);
        verifyLabelTextColor("#new-task-bar-0-label", WHITE);

        verifyThat(subtaskButton, NodeMatchers.isDisabled());

        robot.clickOn(buttonId);
        verifyThat("#new-task-bar-1", NodeMatchers.isVisible());
    }

    //    하위 업무 재귀 생성 테스트;
    @Test
    void create_deep_depth_subtask(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(subtaskButton);

        robot.clickOn(subtaskButton);
        verifyThat("#new-task-bar-0-0-0", NodeMatchers.isVisible());
        robot.clickOn(completeButton);
        verifyRectColor("#new-task-bar-0-0-0-rect", GRAY);
        verifyLabelTextColor("#new-task-bar-0-0-0-label", WHITE);

        robot.clickOn(subtaskButton);
        verifyThat("#new-task-bar-0-0-1", NodeMatchers.isVisible());
        verifyRectColor("#new-task-bar-0-0-1-rect", YELLOW);
        verifyLabelTextColor("#new-task-bar-0-0-1-label", BLACK);
    }

    @Test
    void create_deep_depth_subtask_and_complete_all(FxRobot robot) {
        robot.clickOn(buttonId);

        robot.clickOn(subtaskButton);
        verifyThat("#new-task-bar-0-0", NodeMatchers.isVisible());

        robot.clickOn(subtaskButton);
        verifyThat("#new-task-bar-0-0-0", NodeMatchers.isVisible());

        robot.clickOn(subtaskButton);
        verifyThat("#new-task-bar-0-0-0-0", NodeMatchers.isVisible());

        robot.clickOn(completeButton);
        verifyRectColor("#new-task-bar-0-0-0-0-rect", GRAY);

        robot.clickOn(completeButton);
        verifyRectColor("#new-task-bar-0-0-0-rect", GRAY);

        robot.clickOn(completeButton);
        verifyRectColor("#new-task-bar-0-0-rect", GRAY);

        robot.clickOn(completeButton);
        verifyRectColor("#new-task-bar-0-rect", GRAY);
        verifyThat(buttonId, NodeMatchers.isEnabled());
    }

    private void verifyRectColor(String rectId, Color color) {
        Rectangle rect = lookup(rectId).query();
        verifyThat((Color) rect.getFill(), ColorMatchers.isColor(color));
    }

    private void verifyLabelTextColor(String labelId, Color color) {
        Label rect = lookup(labelId).query();
        verifyThat((Color) rect.getTextFill(), ColorMatchers.isColor(color));
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}
