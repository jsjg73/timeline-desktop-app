import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.TaskHierarchyScene;
import org.example.global.button.TestButtonLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

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

    final TimelineApplicationTestHelper testHelper = new TimelineApplicationTestHelper();

    private FxRobot robot;

    @Test
    void when_button_is_clicked_bar_is_created() {
        initiateNewTask();

        testHelper.assertTaskProgressDisplayed(0);

        testHelper.assertAdditionalTaskInitiationBlocked();
        testHelper.assertTaskCompletionAvailable();
        testHelper.assertSubTaskCreationAvailable();
    }

    private void initiateNewTask() {
        robot.clickOn(addTaskButton);
    }

    @Test
    void when_stop_button_is_clicked_bar_color_rollback() {
        initiateNewTask();
        completeTask();

        testHelper.assertTaskCompletionBlocked();
        testHelper.assertTaskInitiationAvailable();

        final String taskId = "#new-task-bar-0";
        testHelper.assertTaskAppearance(taskId, Color.GRAY, Color.WHITE);
        testHelper.assertSubTaskCreationBlocked();
    }

    private void completeTask() {
        robot.clickOn(completeButton);
    }

    @Test
    void when_add_task_button_is_twice_clicked(FxRobot robot) {
        initiateNewTask();
        completeTask();

        initiateNewTask();

        testHelper.assertTaskProgressDisplayed(1);

        testHelper.assertAdditionalTaskInitiationBlocked();
        testHelper.assertTaskCompletionAvailable();
        testHelper.assertSubTaskCreationAvailable();
    }
}