# TODO List CLI

A command-line interface for managing tasks, built in Java to experiment with I/O operations.

## Overview

This CLI application provides a simple interface for managing personal tasks. It supports both local (current directory) and global (home directory) task storage, making it suitable for both project-specific and personal task management.

## Features

- **Task Management**: create, list, update, and delete tasks
- **Status Tracking**: three-state workflow (TODO -> DOING -> DONE)
- **Priority System**: three priority levels (LOW, MEDIUM, HIGH)
- **Due Dates**: optional due date tracking with date-based filtering
- **Filtering**: filter tasks by status, priority, or due date
- **Sorting**: sort tasks by various criteria in ascending or descending order

## Installation

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Build
```bash
mvn clean compile
mvn package
```

### Run
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar [command] [options]
```

## Usage

### Global Options

All commands support the following global option:

- `-g, --global`: Use the global todo file (`~/.todo.tlst`) instead of the local one (`.todo.tlst`)

### Commands

#### 1. Add Task
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar add "Task description" [options]
```

**Parameters:**
- `description` (required): The task description

**Options:**
- `-d, --due <date>`: Set due date (format: YYYY-MM-DD)
- `-p, --priority <level>`: Set priority (LOW, MEDIUM, HIGH)

**Examples:**
```bash
# Simple task
java -jar target/java-ios-1.0-SNAPSHOT.jar add "Complete project documentation"

# Task with due date and priority
java -jar target/java-ios-1.0-SNAPSHOT.jar add "Review code changes" --due 2024-12-31 --priority HIGH

# Global task
java -jar target/java-ios-1.0-SNAPSHOT.jar add "Personal goal" --global
```

#### 2. List Tasks
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar list [options]
```

**Options:**
- `-f, --filter <type>`: Filter tasks (STATUS, PRIORITY, DUEDATE_BEFORE, DUEDATE_AFTER)
- `-v, --filter-value <value>`: Filter value (required with --filter)
- `-s, --sorter <type>`: Sort by (ID, DESCRIPTION, CREATEDAT, DUEDATE, PRIORITY, STATUS)
- `-d, --direction <dir>`: Sort direction (ASC, DESC)

**Filter Examples:**
```bash
# Filter by status
java -jar target/java-ios-1.0-SNAPSHOT.jar list --filter STATUS --filter-value TODO

# Filter by priority
java -jar target/java-ios-1.0-SNAPSHOT.jar list --filter PRIORITY --filter-value HIGH

# Filter by due date
java -jar target/java-ios-1.0-SNAPSHOT.jar list --filter DUEDATE_BEFORE --filter-value 2024-12-31

# Sort by priority (descending)
java -jar target/java-ios-1.0-SNAPSHOT.jar list --sorter PRIORITY --direction DESC
```

#### 3. Update Task Status
```bash
# Mark as in progress
java -jar target/java-ios-1.0-SNAPSHOT.jar doing <task_id>

# Mark as completed
java -jar target/java-ios-1.0-SNAPSHOT.jar done <task_id>
```

**Examples:**
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar doing 5
java -jar target/java-ios-1.0-SNAPSHOT.jar done 3
```

#### 4. Delete Task
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar delete <task_id>
```

**Example:**
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar delete 7
```

#### 5. Clear Completed Tasks
```bash
java -jar target/java-ios-1.0-SNAPSHOT.jar clear
```

Removes all tasks with DONE status and shows what was cleared.

## Data Model

### Task Structure
Each task contains:
- **ID**: unique identifier (auto-generated)
- **Description**: task description
- **Created At**: creation timestamp (automatically set)
- **Due Date**: optional due date
- **Priority**: optional priority level (LOW, MEDIUM, HIGH)
- **Status**: current status (TODO, DOING, DONE)

**Note on Task IDs**: When adding a new task, the ID is automatically set to the highest existing ID + 1. This ensures predictable ID assignment and does not attempt to fill gaps in the sequence. For example, if tasks with IDs 1, 2, and 4 exist (task 3 was deleted), the next task will be assigned ID 5, not 3.

### File Format
Tasks are stored in a custom pipe-delimited text format, suited for reading as plain text:
```
ID | Description | CreatedAt | DueDate | Priority | Status
```

Example:
```
1 | Finish POC             | 2025-10-07 | 2025-10-08 | MEDIUM | DONE
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DOING
3 | Review code            | 2025-10-08 | 2025-10-09 | HIGH   | DONE
4 | Find name for project  | 2025-10-08 |            | LOW    | DOING
```

## Architecture

### Model/Service Structure
- **Models** (`Task.java`): Data representation with getters/setters
- **Services** (`TaskService.java`): Business logic layer handling task operations, file I/O, and data persistence
- **Commands**: CLI interface layer that delegates to services
- **I/O Layer**: Abstracted file operations for text data

This structure separates data representation from business logic, making the code more maintainable and testable.

## File Structure

```
src/main/java/ch/heigvd/
├── commands/           # CLI command implementations
│   ├── Add.java
│   ├── Clear.java
│   ├── Delete.java
│   ├── Doing.java
│   ├── Done.java
│   ├── List.java
│   └── Root.java
├── ios/                # I/O abstraction layer
│   ├── text/           # text file operations
├── models/             # data models
│   └── Task.java
├── services/           # business logic
│   └── TaskService.java
└── Main.java           # application entry point
```

## Future Enhancements

- **Categories**: add task categorization
- **Recurring Tasks**: support for recurring task patterns
- **Export/Import**: JSON/CSV export capabilities
- **Colors**: add colors relative to task status or closeness to due date
- **Configuration**: configuration options for task status, priorities, etc.
- [Search]: search across task descriptions (POSIX use grep ?)

## Notes
- Currently, we are displaying a message to the user on each action they do in addition to the resuted task(s) (e.g. when we add a task, we get a message that the task was created successfully + the row itself). We might want to just display the resulting task row and not the message to better comply with UNIX principles (better for piping into other programs).