package ch.heigvd.commands;

import java.util.concurrent.Callable;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;

@CommandLine.Command(name = "delete", description = "delete task")
public class Delete implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(
            index = "0",
            description = "id of task to delete")
    protected int id;

    @Override
    public Integer call() {
        TaskService service = new TaskService(parent.getGlobalFlag());
        service.deleteTask(id);
        //TODO check si tout a bien fonctionné

        return 0;
    }
}
