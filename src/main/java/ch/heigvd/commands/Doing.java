package ch.heigvd.commands;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "doing", description = "Change task status in doing")

public class Doing implements Callable<Integer>  {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(
            index = "0",
            description = "id of task to change status")
    protected int id;

    @Override
    public Integer call() {
        TaskService service = new TaskService(parent.getGlobalFlag());
        service.updateStatus(id, Task.Status.DOING);
        //TODO check si tout a bien fonctionné

        System.out.println("Task " + id + " has been successfully changed status.");
        return 0;
    }
}
