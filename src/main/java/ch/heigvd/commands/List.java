package ch.heigvd.commands;

import java.util.concurrent.Callable;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import ch.heigvd.services.TaskService.Filter;
import ch.heigvd.services.TaskService.SorterDirection;
import ch.heigvd.services.TaskService.SorterType;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "List all tasks.")
public class List implements Callable<Integer> {
	@CommandLine.ParentCommand protected Root parent;

	@CommandLine.Option(
		names = {"-f", "--filter"},
		description = "Filter tasks (possible values are: ${COMPLETION-CANDIDATES})",
		required = false
	)
	private Filter filter;

	@CommandLine.Option(
		names = {"-v", "--filter-value"},
		description = "Value to filter by (required when using --filter)",
		required = false
	)
	private String filterValue;

	@CommandLine.Option(
		names = {"-s", "--sorter"},
		description = "Naturally sor the tasks (possible values are ${COMPLETION-CANDIDATES}).",
		defaultValue = "ID",
		required = false
	)
	private SorterType sorter;

	@CommandLine.Option(
		names = { "-d", "--direction" },
		description = "Direction to sort tasks on (possible values: ${COMPLETION-CANDIDATES}).",
		defaultValue = "ASC",
		required = false)
	private SorterDirection sortDir;

	@Override
	public Integer call() {
		if (filter != null && (filterValue == null || filterValue.trim().isEmpty())) {
			System.err.println("Error: --filter-value is required when using --filter");
			return 1;
		}
		if (filter == null && filterValue != null && !filterValue.trim().isEmpty()) {
			System.err.println("Error: --filter-value can only be used with --filter");
			return 1;
		}

		TaskService ts = new TaskService(parent.getGlobalFlag());
		java.util.List<Task> tasks = ts.getTasks(sorter, sortDir, filter, filterValue);

		if (tasks.isEmpty()) {
			System.out.println("No tasks found.");
			// we may want to return 1 here, but I think it's more usable this way
		}

		System.out.print(ts.formattedTasksString(tasks));

		return 0;
	}
}
