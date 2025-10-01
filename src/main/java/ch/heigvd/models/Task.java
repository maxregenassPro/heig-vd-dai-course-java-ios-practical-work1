package ch.heigvd.models;

import java.time.LocalTime;

public class Task {
	private int m_id;
	private String m_description;
	private Priority m_priority;
	private Status m_status;
	private LocalTime m_createdAt;
	private LocalTime m_dueDate;

	public enum Priority { LOW, MEDIUM, HIGH };
	public enum Status { TODO, DOING, DONE };

	public int getId() { return m_id; };
	public void setId(int id) { m_id = id; }

	public String getDescription() { return m_description; }
	public void setDescription(String description) { m_description = description; }

	public Priority getPriority() { return m_priority; }
	public void setPriority(Priority priority) { m_priority = priority; }

	public Status getStatus() { return m_status; }
	public void setStatus(Status status) { m_status = status; }

	public LocalTime getCreatedAt() { return m_createdAt; }
	public void setCreatedAt(LocalTime createdAt) { m_createdAt = createdAt; }

	public LocalTime getDueDate() { return m_dueDate; }
	public void setDueDate(LocalTime dueDate) { m_dueDate = dueDate; }
}
