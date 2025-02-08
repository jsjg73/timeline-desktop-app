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
    final String stopButton = "#" + TaskButtons.stopButtonId;
    final String subtaskButton = "#" + TaskButtons.subtaskButtonId;

    @Test
    void first_test() {
        FxAssert.verifyThat(buttonId, LabeledMatchers.hasText("Add Task"));

        FxAssert.verifyThat(startButton, LabeledMatchers.hasText("Start"));
        FxAssert.verifyThat(startButton, NodeMatchers.isDisabled());

        FxAssert.verifyThat(stopButton, LabeledMatchers.hasText("Stop"));
        FxAssert.verifyThat(stopButton, NodeMatchers.isDisabled());

        FxAssert.verifyThat(subtaskButton, LabeledMatchers.hasText("Subtask"));
        FxAssert.verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }

    @Test
    void when_button_is_clicked_bar_is_created(FxRobot robot) {
        robot.clickOn(buttonId);

        FxAssert.verifyThat("#new-task-bar-0", NodeMatchers.isVisible());
        FxAssert.verifyThat(buttonId, NodeMatchers.isDisabled());

        FxAssert.verifyThat(startButton, NodeMatchers.isEnabled());
        FxAssert.verifyThat(stopButton, NodeMatchers.isDisabled());
        FxAssert.verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }
}