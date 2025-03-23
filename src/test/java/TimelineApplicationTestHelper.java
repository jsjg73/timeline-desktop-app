import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.global.button.TestButtonLocator;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.ColorMatchers;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.service.query.NodeQuery;

import static org.testfx.api.FxAssert.verifyThat;

public class TimelineApplicationTestHelper {


    final String addTaskButton = "#" + TestButtonLocator.taskButtonId;
    final String completeButton = "#" + TestButtonLocator.completeButtonId;
    final String subtaskButton = "#" + TestButtonLocator.subtaskButtonId;

    public void assertTaskProgressDisplayed(int index) {
        final String taskId = "#new-task-bar-" + index;
        verifyThat(taskId, NodeMatchers.isVisible());

        assertTaskAppearance(taskId, Color.YELLOW, Color.BLACK);
    }

    public void assertTaskAppearance(String taskId, Color backgroundColor, Color fontColor) {
        Rectangle rect = lookup(taskId + "-rect").query();
        Label label = lookup(taskId + "-label").query();
        verifyThat((Color) rect.getFill(), ColorMatchers.isColor(backgroundColor));
        verifyThat((Color) label.getTextFill(), ColorMatchers.isColor(fontColor));
    }

    private NodeQuery lookup(String query) {
        return FxAssert.assertContext()
                .getNodeFinder()
                .lookup(query);
    }



    public void assertTaskInitiationAvailable() {
        verifyThat(addTaskButton, NodeMatchers.isEnabled());
    }

    public void assertSubTaskCreationAvailable() {
        verifyThat(subtaskButton, NodeMatchers.isEnabled());
    }

    public void assertTaskCompletionAvailable() {
        verifyThat(completeButton, NodeMatchers.isEnabled());
    }

    public void assertAdditionalTaskInitiationBlocked() {
        verifyThat(addTaskButton, NodeMatchers.isDisabled());
    }

    public void assertTaskCompletionBlocked() {
        verifyThat(completeButton, NodeMatchers.isDisabled());
    }

    public void assertSubTaskCreationBlocked() {
        verifyThat(subtaskButton, NodeMatchers.isDisabled());
    }
}
