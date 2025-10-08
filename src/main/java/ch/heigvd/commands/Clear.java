package ch.heigvd.commands;

import ch.heigvd.services.TaskService;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "clear", description = "clear all tasks with status done")
public class Clear implements Callable<Integer> {
    @CommandLine.ParentCommand protected Root parent;

    @Override
    public Integer call() {
        TaskService service = new TaskService(parent.getGlobalFlag());
        service.clearCompleted();

        System.out.println("Operation done with successfully");
        return 0;
    }
}
