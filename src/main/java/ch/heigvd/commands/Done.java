package ch.heigvd.commands;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "done", description = "Change task status")

public class Done implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @CommandLine.Parameters(
            index = "0",
            description = "id of task to change status")
    protected int id;

    @Override
    public Integer call() {
        TaskService service = new TaskService(parent.getGlobalFlag());
        service.updateStatus(id, Task.Status.DONE);
        //TODO check si tout a bien fonctionné

        System.out.println("Task " + id + " has been change status");

        return 0;
    }
}
