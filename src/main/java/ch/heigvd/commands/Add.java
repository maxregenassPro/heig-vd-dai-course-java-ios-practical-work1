package ch.heigvd.commands;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "add a task")
public class Add implements Callable<Integer> {
	@CommandLine.ParentCommand protected Root parent;

	@CommandLine.Parameters(index = "0", description = "The description of the task")
	private String description;

	private enum AvailablePriorityTags {
		LOW,
		MEDIUM,
		HIGH
	}

	@CommandLine.Option(
		names = {"-d", "--due"},
		description = "Set a due date for the task (e.g. 2025-01-31)",
		required = false)
	private Optional<LocalDate> dueDate;

	@CommandLine.Option(
		names = {"-p", "--priority"},
		description = "Set a priority to the task (possible values: ${COMPLETION-CANDIDATES}).",
		required = false)
	private Optional<AvailablePriorityTags> priority;

	@Override
	public Integer call() {
		// TODO(sss): create a task
		// TODO(sss): implement with file IO once ready
		System.out.println(
			"Adding task:\n"
			+ "global: " + (parent.getGlobalFlag() ? "true\n" : "false\n")
			+ "description: " + description + "\n"
			+ "priority: " + (!priority.isEmpty() ? priority.get().toString() : "NULL") + "\n"
			+ "due: " + (!dueDate.isEmpty() ? dueDate.get().toString() : "NULL")
		);

		return 0;
	}
}
