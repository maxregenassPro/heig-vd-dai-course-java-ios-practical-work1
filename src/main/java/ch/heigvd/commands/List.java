package ch.heigvd.commands;

import java.util.concurrent.Callable;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "List all tasks.")
public class List implements Callable<Integer> {
	@CommandLine.ParentCommand protected Root parent;

	@Override
	public Integer call() {
		TaskService ts = new TaskService(parent.getGlobalFlag());
		java.util.List<Task> tasks = ts.getTasks();

		System.out.print(ts.formattedTasksString(tasks));

		return 0;
	}
}
