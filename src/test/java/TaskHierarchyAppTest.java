import javafx.scene.Node;
import javafx.stage.Stage;
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

    @Test
    void first_test() {
        FxAssert.verifyThat("#addTaskButton", LabeledMatchers.hasText("Add Task"));
    }

    @Test
    void when_button_is_clicked_bar_is_created(FxRobot robot) {
        robot.clickOn("#addTaskButton");
        FxAssert.verifyThat("#new-task-bar-0", NodeMatchers.isVisible());
    }
}