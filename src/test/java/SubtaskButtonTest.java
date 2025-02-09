import javafx.stage.Stage;
import org.example.TaskButtons;
import org.example.TaskHierarchyScene;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;

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

        robot.clickOn(subtaskButton);

        FxAssert.verifyThat("#new-subtask-bar-0-0", NodeMatchers.isVisible());
        FxAssert.verifyThat("#new-subtask-bar-0-0-rect", NodeMatchers.isVisible());
        FxAssert.verifyThat("#new-subtask-bar-0-0-label", NodeMatchers.isVisible());

        FxAssert.verifyThat("#new-subtask-bar-0-0-label", LabeledMatchers.hasText("새 하위 업무"));
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}
