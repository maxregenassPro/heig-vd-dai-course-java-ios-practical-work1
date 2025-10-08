package ch.heigvd.commands;

import java.util.concurrent.Callable;

import ch.heigvd.models.Task;
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
        Task deleted = service.deleteTask(id);

		if (deleted == null) {
			System.out.println("No task found with id " + id + ".");
			return 1;
		}

		System.out.println("Deleted task " + id + " successfully.");
		System.out.println(deleted.toString());

		return 0;
	}
}
