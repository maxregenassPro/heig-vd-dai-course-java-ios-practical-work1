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
        Task done = service.updateStatus(id, Task.Status.DONE);

		if (done == null) {
			System.out.println("No task found with id " + id + ".");
			return 1;
		} else {
			System.out.println("Changed status from task with id " + id + " to DONE.");
			System.out.println(done.toString());
		}

        return 0;
    }
}
