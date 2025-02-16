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
import org.testfx.service.query.NodeQuery;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class TaskLayoutTest {
    @Start
    public void start(Stage stage) {
        TaskHierarchyScene hierarchyScene = new TaskHierarchyScene();
        stage.setTitle("Task Hierarchy Example");
        stage.setScene(hierarchyScene.draw());
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    final String buttonId = "#" + TaskButtons.taskButtonId;
    final String startButton = "#" + TaskButtons.startButtonId;
    final String completeButton = "#" + TaskButtons.completeButtonId;
    final String subtaskButton = "#" + TaskButtons.subtaskButtonId;

    @Test
    void 깊이가_같은_subtask는_가로로_추가됨(FxRobot robot) {
        newStartRootTask(robot);
        Rectangle task = lookup("#new-task-bar-0-rect").query();
        assertThat(task.getX()).isEqualTo(50);
        assertThat(task.getY()).isEqualTo(50);

        completeNewSubtask(robot);
        Rectangle subtask = lookup("#new-task-bar-0-0-rect").query();
        assertThat(subtask.getX()).isEqualTo(80);
        assertThat(subtask.getY()).isEqualTo(100);

        startNewSubTask(robot);
        Rectangle sub02 = lookup("#new-task-bar-0-1-rect").query();
        assertThat(sub02.getX()).isEqualTo(160);
        assertThat(sub02.getY()).isEqualTo(100);
    }

    @Test
    void 깊이_2이상_하위_업무의_레이아웃(FxRobot robot) {
        newStartRootTask(robot);

        startNewSubTask(robot);

        startNewSubTask(robot);
        Rectangle dept2 = lookup("#new-task-bar-0-0-0-rect").query();
        assertThat(dept2.getX()).isEqualTo(110);
        assertThat(dept2.getY()).isEqualTo(150);

        startNewSubTask(robot);
        Rectangle depth3 = lookup("#new-task-bar-0-0-0-0-rect").query();
        assertThat(depth3.getX()).isEqualTo(140);
        assertThat(depth3.getY()).isEqualTo(200);

    }

    @Test
    void 최상위_업무의_레이아웃(FxRobot robot) {
        newStartRootTask(robot);
        completeNewSubtask(robot);
        robot.clickOn(completeButton);

        newStartRootTask(robot);

        Rectangle secondRect = lookup("#new-task-bar-1-rect").query();
        assertThat(secondRect.getX()).isEqualTo(50);
        assertThat(secondRect.getY()).isEqualTo(150);

        completeNewSubtask(robot);
        robot.clickOn(completeButton);

        Rectangle thirdRect = lookup("#new-task-bar-2-rect").query();
        assertThat(thirdRect.getX()).isEqualTo(50);
        assertThat(thirdRect.getY()).isEqualTo(250);

    }

    private void newStartRootTask(FxRobot robot) {
        robot.clickOn(buttonId);
        robot.clickOn(startButton);
    }

    private void completeRootTask(FxRobot robot) {
        newStartRootTask(robot);
        robot.clickOn(completeButton);
    }

    private void startNewSubTask(FxRobot robot) {
        robot.clickOn(subtaskButton);
        robot.clickOn(startButton);
    }

    private void completeNewSubtask(FxRobot robot) {
        startNewSubTask(robot);
        robot.clickOn(completeButton);
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}
