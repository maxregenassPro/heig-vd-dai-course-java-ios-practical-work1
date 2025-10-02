package ch.heigvd.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ch.heigvd.models.Task;
import ch.heigvd.models.Task.Priority;
import ch.heigvd.models.Task.Status;

public class TaskService {
	private File tasksFile;

	public TaskService(boolean global) {
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

		try {
			BufferedReader br = new BufferedReader(new FileReader(tasksFile, StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null) {
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
			br.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to read tasks", e);
		}

		return tasks;
	}

	private void saveTasks(List<Task> tasks) {
		if (tasks.isEmpty()) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(tasksFile, StandardCharsets.UTF_8))) {
				// TODO: clear file?
			} catch (IOException e) {
				throw new RuntimeException("Failed to save tasks", e);
			}
			return;
		}

		// max lengths for alignment in file
		// NOTE: do we really want to handle formatting? This doesn't really follow the POSIX / KISS principle in the end
		int maxIdLen = 1;
		int maxDescLen = 1;
		int maxCreatedAtLen = 1;
		int maxDueDateLen = 1;
		int maxPriorityLen = 1;
		int maxStatusLen = 1;
		for(Task t : tasks) {
			maxIdLen = Integer.toString(t.getId()).length();
			maxDescLen = t.getDescription().length();
			maxCreatedAtLen = t.getCreatedAt() == null ? 1 : t.getCreatedAt().toString().length();
			maxDueDateLen = t.getDueDate() == null ? 1 : t.getDueDate().toString().length();
			maxPriorityLen = t.getPriority() == null ? 1 : t.getPriority().toString().length();
			maxStatusLen = t.getStatus() == null ? 1 : t.getStatus().toString().length();
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(tasksFile, StandardCharsets.UTF_8))) {
			for (Task t : tasks) {
				String line = String.format(
					"%-" + maxIdLen + "d"
					+ " | %-" + maxDescLen + "s" 
					+ " | %-" + maxCreatedAtLen + "s"
					+ " | %-" + maxDueDateLen + "s"
					+ " | %-" + maxPriorityLen + "s"
					+ " | %-" + maxStatusLen + "s",
					t.getId(), t.getDescription(),
					t.getCreatedAt() == null ? "" : t.getCreatedAt(),
					t.getDueDate() == null ? "" : t.getDueDate(),
					t.getPriority() == null ? "" : t.getPriority(),
					t.getStatus() == null ? "" : t.getStatus()
				);
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to save tasks", e);
		}
	}

	public List<Task> getTasks() { return loadTasks(); }

	public void addTask(Task task) {
		List<Task> tasks = loadTasks();
		int newId = tasks.isEmpty() ? 1 : tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
		task.setId(newId);
		task.setCreatedAt(LocalDate.now());
		task.setStatus(Status.TODO);
		System.out.println(task.toString());
		tasks.add(task);
		saveTasks(tasks);
	}
}
