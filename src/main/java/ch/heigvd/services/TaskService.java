package ch.heigvd.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.heigvd.ios.text.TextFileReader;
import ch.heigvd.ios.text.TextFileWriter;
import ch.heigvd.models.Task;
import ch.heigvd.models.Task.Priority;
import ch.heigvd.models.Task.Status;

public class TaskService {
	public enum SorterType {
		ID(Comparator.comparingInt(Task::getId)),
		DESCRIPTION(Comparator.comparing(Task::getDescription, Comparator.nullsLast(String::compareToIgnoreCase))),
		CREATEDAT(Comparator.comparing(Task::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder()))),
		DUEDATE(Comparator.comparing(Task::getDueDate, Comparator.nullsLast(Comparator.naturalOrder()))),
		PRIORITY(Comparator.comparing(Task::getPriority, Comparator.nullsLast(Comparator.naturalOrder()))),
		STATUS(Comparator.comparing(Task::getStatus, Comparator.nullsLast(Comparator.naturalOrder())));

		private final Comparator<Task> comparator;

		SorterType(Comparator<Task> comparator) {
			this.comparator = comparator;
		}

		public Comparator<Task> getComparator() {
			return comparator;
		}
	}
	public enum SorterDirection { ASC, DESC }
	// NOTE: source is ChatGPT, asked him how to modularly filter tasks in getTasks()
	// A Predicate<Task> is an interface that takes a Task and returns a boolean.
	// It represents a kind of test: if it returns true, the task passes the filter,
	// if false, the task is excluded. 
	public enum Filter {
		STATUS {
			@Override
			public Predicate<Task> makeFilter(String value) {
				return t -> t.getStatus() != null && t.getStatus().name().equalsIgnoreCase(value);
			}
		},
		PRIORITY {
			@Override
			public Predicate<Task> makeFilter(String value) {
				return t -> t.getPriority() != null && t.getPriority().name().equalsIgnoreCase(value);
			}
		},
		DUEDATE_BEFORE {
			@Override
			public Predicate<Task> makeFilter(String value) {
				return t -> t.getDueDate() != null && t.getDueDate().isBefore(LocalDate.parse(value));
			}
		},
		DUEDATE_AFTER {
			@Override
			public Predicate<Task> makeFilter(String value) {
				return t -> t.getDueDate() != null && t.getDueDate().isAfter(LocalDate.parse(value));
			}
		};

		public abstract Predicate<Task> makeFilter(String value);
	}

	private File tasksFile;

	public TaskService(boolean global) {
		// TODO(sss): should we create the file in the file writer or smth?
		if(global) {
			tasksFile = Path.of(System.getProperty("user.home"), ".todo.tlst").toFile();
		} else {
			tasksFile = Path.of(System.getProperty("user.dir"), ".todo.tlst").toFile();
		}
		if (!tasksFile.exists()) {
			try {
				tasksFile.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Failed to create task storage file", e);
			}
		}
	}

	private List<Task> loadTasks() {
		List<Task> tasks = new ArrayList<Task>();
		TextFileReader fr = new TextFileReader();
		List<String> lines = fr.read(tasksFile.getPath());
		for (String line : lines) {
			String[] parts = line.split("\\|"); // TODO(sss): use something else than | for separator?
			if (parts.length < 6) continue; // TODO: notify user that there are lines that don't respect the format?

			Task task = new Task();
			task.setId(Integer.parseInt(parts[0].trim())); // TODO(sss): id validation ?
			task.setDescription(parts[1].trim());
			task.setCreatedAt(parts[2].trim().isEmpty() ? null : LocalDate.parse(parts[2].trim())); // TODO(sss): type / string validation without throwing?
			task.setDueDate(parts[3].trim().isEmpty() ? null : LocalDate.parse(parts[3].trim()));
			task.setPriority(parts[4].trim().isEmpty() ? null : Priority.valueOf(parts[4].trim()));
			task.setStatus(parts[5].trim().isEmpty() ? null :  Status.valueOf(parts[5].trim()));

			tasks.add(task);
		}
		return tasks;
	}

	public String formattedTasksString(List<Task> tasks) {
		// max lengths for alignment in file
		// NOTE: do we really want to handle formatting? This doesn't really follow the POSIX / KISS principle in the end
		int maxIdLen = 1;
		int maxDescLen = 1;
		int maxCreatedAtLen = 1;
		int maxDueDateLen = 1;
		int maxPriorityLen = 1;
		int maxStatusLen = 1;
		for(Task t : tasks) {
			maxIdLen = Math.max(maxIdLen, Integer.toString(t.getId()).length());
			maxDescLen = Math.max(maxDescLen, t.getDescription().length());
			maxCreatedAtLen = Math.max(maxCreatedAtLen, t.getCreatedAt() == null ? 1 : t.getCreatedAt().toString().length());
			maxDueDateLen = Math.max(maxDueDateLen, t.getDueDate() == null ? 1 : t.getDueDate().toString().length());
			maxPriorityLen = Math.max(maxPriorityLen, t.getPriority() == null ? 1 : t.getPriority().toString().length());
			maxStatusLen = Math.max(maxStatusLen, t.getStatus() == null ? 1 : t.getStatus().toString().length());
		}

		String formatted = "";
		for (Task t : tasks) {
			formatted += String.format(
				"%-" + maxIdLen + "d"
				+ " | %-" + maxDescLen + "s" 
				+ " | %-" + maxCreatedAtLen + "s"
				+ " | %-" + maxDueDateLen + "s"
				+ " | %-" + maxPriorityLen + "s"
				+ " | %-" + maxStatusLen + "s\n",
				t.getId(), t.getDescription(),
				t.getCreatedAt() == null ? "" : t.getCreatedAt(),
				t.getDueDate() == null ? "" : t.getDueDate(),
				t.getPriority() == null ? "" : t.getPriority(),
				t.getStatus() == null ? "" : t.getStatus()
			);
		}
		return formatted;
	}

	private void saveTasks(List<Task> tasks) {
		if (tasks.isEmpty()) {
			// TODO: clear file?
			return;
		}
		TextFileWriter fw = new TextFileWriter();
		fw.write(tasksFile.getPath(), formattedTasksString(tasks));
	}

	public List<Task> getTasks(SorterType sortType, SorterDirection sortDir,
		Filter filter, String filterValue) { 
		List<Task> tasks = loadTasks();

		// filtering (ChatGPT)
		if (filter != null && filterValue != null) {
			tasks = tasks.stream()
			.filter(filter.makeFilter(filterValue))
			.collect(Collectors.toCollection(ArrayList::new));
		}

		// sorting
		if (!tasks.isEmpty() && sortType != null && sortDir != null) {
			Comparator<Task> comp = sortType.getComparator();
			if (sortDir == SorterDirection.DESC) {
				comp = comp.reversed();
			}
			tasks.sort(comp);
		}

		return tasks;
	}
	public List<Task> getTasks() {
		return getTasks(null, null, null, null);
	}

	public Task addTask(Task task) {
		List<Task> tasks = loadTasks();
		int newId = tasks.isEmpty() ? 1 : tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
		task.setId(newId);
		task.setCreatedAt(LocalDate.now());
		task.setStatus(Status.TODO);
		tasks.add(task);
		saveTasks(tasks);
		return task;
	}

	public Task updateStatus(int taskId, Task.Status status) {
		List<Task> tasks = loadTasks();
		Task updatedTask = tasks.stream()
			.filter(t -> t.getId() == taskId)
			.findFirst()
			.orElse(null);
		if (updatedTask != null) {
			updatedTask.setStatus(status);
			saveTasks(tasks);
		}
		return updatedTask;
	}

	public Task deleteTask(int taskId) {
		List<Task> tasks = loadTasks();
		Task deletedTask = tasks.stream()
			.filter(t -> t.getId() == taskId)
			.findFirst()
			.orElse(null);
		if (deletedTask != null) {
			tasks.removeIf(t -> t.getId() == taskId);
			saveTasks(tasks);
		}
		return deletedTask;
	}

	public List<Task> clearCompleted() {
		List<Task> tasks = loadTasks();
		List<Task> clearedTasks = tasks.stream()
			.filter(t -> t.getStatus() == Task.Status.DONE)
			.collect(Collectors.toList());
		tasks.removeIf(t -> t.getStatus() == Task.Status.DONE);
		saveTasks(tasks);
		return clearedTasks;
	}
}
