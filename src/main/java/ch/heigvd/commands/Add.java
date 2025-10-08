package ch.heigvd.commands;

import java.time.LocalDate;
import java.util.concurrent.Callable;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "add a task")
public class Add implements Callable<Integer> {
	@CommandLine.ParentCommand protected Root parent;

	@CommandLine.Parameters(index = "0", description = "The description of the task")
	private String description;

	@CommandLine.Option(
		names = {"-d", "--due"},
		description = "Set a due date for the task (e.g. 2025-01-31)",
		required = false)
	private LocalDate dueDate;

	@CommandLine.Option(
		names = {"-p", "--priority"},
		description = "Set a priority to the task (possible values: ${COMPLETION-CANDIDATES}).",
		required = false)
	private Task.Priority priority;

	@Override
	public Integer call() {
		Task task = new Task();
        task.setDescription(description.trim());
        task.setDueDate(dueDate);
        task.setPriority(priority);

        TaskService service = new TaskService(parent.getGlobalFlag());
        Task created = service.addTask(task);
		System.out.println("Created task with id " + created.getId() + ".");
		System.out.println(created.toString());
		return 0;
	}
}

