import javafx.stage.Stage;
import org.example.TaskHierarchyScene;
import org.example.global.button.TestButtonLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;

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

    final String addTaskButton = "#" + TestButtonLocator.taskButtonId;
    final String completeButton = "#" + TestButtonLocator.completeButtonId;
    final String subtaskButton = "#" + TestButtonLocator.subtaskButtonId;

    private FxRobot robot;
    final TimelineApplicationTestHelper testHelper = new TimelineApplicationTestHelper();

    @Test
    void when_subtask_button_is_clicked_subtask_bar_is_created() {
        initiateNewTask();

        initiateSubTask();

        final String labelId = "#new-task-bar-0-0-label";
        verifyThat(labelId, LabeledMatchers.hasText("새 하위 업무"));

        testHelper.assertTaskAppearance("#new-task-bar-0-0", YELLOW, BLACK);

        testHelper.assertAdditionalTaskInitiationBlocked();
        testHelper.assertTaskCompletionAvailable();
        testHelper.assertSubTaskCreationAvailable();
    }


    @Test
    void when_subtask_is_started_bar_color_is_changed() {
        initiateNewTask();
        initiateSubTask();

        testHelper.assertTaskAppearance("#new-task-bar-0-0", YELLOW, BLACK);
        testHelper.assertTaskCompletionAvailable();
    }

    @Test
    void when_subtask_is_completed() {
        initiateNewTask();
        initiateSubTask();

        completeTask();

        testHelper.assertTaskAppearance("#new-task-bar-0-0", GRAY, WHITE);
        testHelper.assertSubTaskCreationAvailable();
    }

    @Test
    void when_subtask_is_completed_parent_task_could_be_completed() {
        initiateNewTask();
        initiateSubTask();
        completeTask();

        completeTask();

        testHelper.assertTaskAppearance("#new-task-bar-0", GRAY, WHITE);

        testHelper.assertSubTaskCreationBlocked();

        initiateNewTask();
        testHelper.assertTaskProgressDisplayed(1);
    }

    //    하위 업무 재귀 생성 테스트;
    @Test
    void create_deep_depth_subtask() {
        initiateNewTask();
        initiateSubTask();

        initiateSubTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0", YELLOW, BLACK);
        completeTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0", GRAY, WHITE);

        initiateSubTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-1", YELLOW, BLACK);
    }

    @Test
    void create_deep_depth_subtask_and_complete_all() {
        initiateNewTask();

        initiateSubTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0", YELLOW, BLACK);

        initiateSubTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0", YELLOW, BLACK);

        initiateSubTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0-0", YELLOW, BLACK);

        completeTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0-0", GRAY, WHITE);

        completeTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0-0", GRAY, WHITE);

        completeTask();
        testHelper.assertTaskAppearance("#new-task-bar-0-0", GRAY, WHITE);

        completeTask();
        testHelper.assertTaskAppearance("#new-task-bar-0", GRAY, WHITE);

        testHelper.assertTaskInitiationAvailable();
    }

    private void completeTask() {
        robot.clickOn(completeButton);
    }

    private void initiateNewTask() {
        robot.clickOn(addTaskButton);
    }
    
    private void initiateSubTask(){
        robot.clickOn(subtaskButton);
    }
}
