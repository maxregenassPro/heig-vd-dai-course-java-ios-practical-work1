# Java IOs - Practical work 1

## Todo list
The application stores the task list in a text file. This file is fully readable by all users.

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

``todo add <task name>``

Options:

- -d, --due=\<date> (Set a due date for the task (e.g. 2025-01-31))
- -p, --priority=\<priority> (set priority: LOW, MEDIUM, HIGH)

### Doing
Change status of a task on doing

``todo doing <task id>``

### Done
Change status of a task on done 

``todo done <task id>``

### Clear
Delete all task with status done

``todo clear``

### Delete
Delete task with specified id

``todo delete <task id>``


### List
Show all task

``todo list``

Options:

- -d, --direction=\<sortDir> (Direction to sort tasks: ASC, DESC) 
- -f, --filter=\<filter> (STATUS, PRIORITY, DUEDATE_BEFOR, DUEDATE_AFTER)
- -g, --global (put file in home directory)
- -h, --help (Show help message)
- -s, --sorter=\<sorter> (ID, DESCRIPTION, CREATEDAT, DUEDATE, PRIORITY, STATUS)
- -v, --filter-value=\<filterValue> (Value to filter by)
- V, --version (print version information)




