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
public class SubtaskButtonTest {
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
    void when_subtask_button_is_clicked_subtask_bar_is_created(FxRobot robot) {
        robot.clickOn(buttonId);

        robot.clickOn(startButton);

        final String rectId = "#new-subtask-bar-0-0-rect";
        final String labelId = "#new-subtask-bar-0-0-label";

        robot.clickOn(subtaskButton);

        verifyThat("#new-subtask-bar-0-0", NodeMatchers.isVisible());

        verifyThat(rectId, NodeMatchers.isVisible());
        verifyThat(labelId, NodeMatchers.isVisible());
        barIsCreated(rectId, labelId);

        verifyThat(labelId, LabeledMatchers.hasText("새 하위 업무"));

        verifyThat(buttonId, NodeMatchers.isDisabled());
        verifyThat(startButton, NodeMatchers.isEnabled());
        verifyThat(completeButton, NodeMatchers.isDisabled());
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }


    @Test
    void when_subtask_is_started_bar_color_is_changed(FxRobot robot) {
        final String rectId = "#new-subtask-bar-0-0-rect";
        final String labelId = "#new-subtask-bar-0-0-label";

        robot.clickOn(buttonId);
        robot.clickOn(startButton);
        robot.clickOn(subtaskButton);

        robot.clickOn(startButton);

        verifyRectColor(rectId, Color.YELLOW);
        verifyLabelTextColor(labelId, Color.BLACK);

        verifyThat(startButton, NodeMatchers.isDisabled());
        verifyThat(completeButton, NodeMatchers.isEnabled());
    }

    private void barIsCreated(String rectId, String labelId) {
        verifyRectColor(rectId, Color.BLUE);
        verifyLabelTextColor(labelId, Color.WHITE);
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
