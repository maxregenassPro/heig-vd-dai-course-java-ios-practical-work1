# Java IOs - Practical work 1

## Todo list
The application stores the task list in a text file. This file is fully readable by all users.

##  Install and setup

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Clone
```bash
$ git clone https://github.com/maxregenassPro/heig-vd-dai-course-java-ios-practical-work1.git dai-todo-list
$ cd dai-todo-list
```

### Build
Make sure you have **JDK 21** installed and set as your active Java version.

```bash
# you can build the project using the maven wrapper (recommended):
$ ./mvnw clean compile
$ ./mvnw package

# alternatively, if you already have maven installed locally:
$ mvn clean compile
$ mvn package
```

### Run
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar [command] [options]
```

## Usage

### Example file

You can find an example file on the root of this directory (`.todo.tlst`)

### Commands

List of commands:

- [Add](#add)
- [Doing](#doing)
- [Done](#done)
- [Clear](#clear)
- [Delete](#delete)
- [List](#list)


### Add
Create a new task. When a task is created, an id is automatically attribute

``java -jar target/java-ios-1.0-SNAPSHOT.jar add <task name>``

Options:

- -d, --due=\<date> (Set a due date for the task (e.g. 2025-01-31))
- -p, --priority=\<priority> (set priority: LOW, MEDIUM, HIGH)

Example:
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar add Complete documentation --due 2025-10-10 --priority MEDIUM
Created task with id 2.
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | TODO
```

### Doing
Change status of a task on doing

``java -jar target/java-ios-1.0-SNAPSHOT.jar doing <task id>``

Example:
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar doing 2
Changed status from task with id 2 to DOING.
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DOING
```

### Done
Change status of a task on done 

``java -jar target/java-ios-1.0-SNAPSHOT.jar done <task id>``

Example:
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar done 2
Changed status from task with id 2 to DONE.
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DONE
```

### Clear
Delete all task with status done

``java -jar target/java-ios-1.0-SNAPSHOT.jar clear``

Example:
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar clear
Cleared 1 completed task(s):
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DONE
```

### Delete
Delete task with specified id

``java -jar target/java-ios-1.0-SNAPSHOT.jar delete <task id>``

Example:
```bash
$ java -jar target/java-ios-1.0-SNAPSHOT.jar delete 2
Deleted task 2 successfully.
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DONE
```


### List
Show all task

``java -jar target/java-ios-1.0-SNAPSHOT.jar list``

Options:

- -d, --direction=\<sortDir> (Direction to sort tasks: ASC, DESC) 
- -f, --filter=\<filter> (STATUS, PRIORITY, DUEDATE_BEFOR, DUEDATE_AFTER)
- -g, --global (put file in home directory)
- -h, --help (Show help message)
- -s, --sorter=\<sorter> (ID, DESCRIPTION, CREATEDAT, DUEDATE, PRIORITY, STATUS)
- -v, --filter-value=\<filterValue> (Value to filter by)
- V, --version (print version information)

Example:
```bash
# list all tasks
$ java -jar target/java-ios-1.0-SNAPSHOT.jar list
1 | Finish POC             | 2025-10-07 | 2025-10-08 | MEDIUM | DONE
2 | Complete documentation | 2025-10-08 | 2025-10-10 | MEDIUM | DOING
3 | Review code            | 2025-10-08 | 2025-10-09 | HIGH   | DONE
4 | Find name for project  | 2025-10-08 |            | LOW    | DOING
```

```bash
# with filters and sorting
$ java -jar target/java-ios-1.0-SNAPSHOT.jar list -s DUEDATE -f STATUS -v DONE -d DESC
3 | Review code | 2025-10-08 | 2025-10-09 | HIGH   | DONE
1 | Finish POC  | 2025-10-07 | 2025-10-08 | MEDIUM | DONE
```

### Authors

- [Maxime Regenass](https://github.com/maxregenassPro)
- [Santiago Sugranes](https://github.com/santettebtw)
