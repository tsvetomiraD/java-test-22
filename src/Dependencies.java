import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;

import java.util.ArrayList;
import java.util.List;

public class Dependencies extends Task {

    List<Dependency> dependencies = new ArrayList<Dependency>();

    public Dependency createDependency() {
        Dependency dependency = new Dependency();
        dependencies.add(dependency);
        return dependency;
    }

    public void execute() {
        for(Dependency dependency : dependencies) {
            dependency.execute();
        }
    }
}
