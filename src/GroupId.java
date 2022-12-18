import org.apache.tools.ant.Task;

public class GroupId extends Task{
    public String message;
    public void addText(String text) {
        message = getProject().replaceProperties(text);
    }
}
