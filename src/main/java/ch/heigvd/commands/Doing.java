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
        Task doing = service.updateStatus(id, Task.Status.DOING);

		if (doing == null) {
			System.out.println("No task found with id " + id + ".");
			return 1;
		}

		System.out.println("Changed status from task with id " + id + " to DOING.");
		System.out.println(doing.toString());

        return 0;
    }
}
