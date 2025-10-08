package ch.heigvd.models;

import java.time.LocalDate;

public class Task {
	private int m_id;
	private String m_description;
	private LocalDate m_createdAt;
	private LocalDate m_dueDate;
	private Priority m_priority;
	private Status m_status;

	public enum Priority { LOW, MEDIUM, HIGH };
	public enum Status { TODO, DOING, DONE };

	public int getId() { return m_id; };
	public void setId(int id) { m_id = id; }

	public String getDescription() { return m_description; }
	public void setDescription(String description) { m_description = description; }

	public LocalDate getCreatedAt() { return m_createdAt; }
	public void setCreatedAt(LocalDate createdAt) { m_createdAt = createdAt; }

	public LocalDate getDueDate() { return m_dueDate; }
	public void setDueDate(LocalDate dueDate) { m_dueDate = dueDate; }

	public Priority getPriority() { return m_priority; }
	public void setPriority(Priority priority) { m_priority = priority; }

	public Status getStatus() { return m_status; }
	public void setStatus(Status status) { m_status = status; }

	public String toString() {
		return String.join("|", 
			Integer.toString(m_id), m_description, 
			m_createdAt == null ? "" : m_createdAt.toString(),
			m_dueDate == null ? "" : m_dueDate.toString(),
			m_priority == null ? "" : m_priority.toString(),
			m_status == null ? "" : m_status.toString());
	}

}
