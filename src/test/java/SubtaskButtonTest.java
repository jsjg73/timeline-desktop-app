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

        robot.clickOn(subtaskButton);

        verifyThat("#new-subtask-bar-0-0", NodeMatchers.isVisible());
        verifyThat("#new-subtask-bar-0-0-rect", NodeMatchers.isVisible());
        verifyThat("#new-subtask-bar-0-0-label", NodeMatchers.isVisible());

        verifyThat("#new-subtask-bar-0-0-label", LabeledMatchers.hasText("새 하위 업무"));

        verifyThat(buttonId, NodeMatchers.isDisabled());
        verifyThat(startButton, NodeMatchers.isEnabled());
        verifyThat(completeButton, NodeMatchers.isDisabled());
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }


    @Test
    void test(FxRobot robot) {
        //    하위 막대 생성 후 global buttons의 동작;
//        추가됐을 때는, 시작하기 전 상태, 시작 버튼을 누르면,task 처럼 노란색 배경에 검은 글자로 보임.
//
//        즉 subtask 버튼을 눌러 추가하면;
//            start를 누를 수 있는 상태가 된다. 나머지 버튼들은 비활성상태.
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }
}
