package ch.heigvd.commands;

import ch.heigvd.models.Task;
import ch.heigvd.services.TaskService;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "clear", description = "clear all tasks with status done")
public class Clear implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        TaskService service = new TaskService(parent.getGlobalFlag());
        java.util.List<Task> clearedTasks = service.clearCompleted();

        if (clearedTasks.isEmpty()) {
            System.out.println("No completed tasks found to clear.");
			// we may want to return 1 here, but I think it's more usable this way
        } else {
            System.out.println("Cleared " + clearedTasks.size() + " completed task(s):");
            System.out.print(service.formattedTasksString(clearedTasks));
        }

        return 0;
    }
}
