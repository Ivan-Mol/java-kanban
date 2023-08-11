# java-kanban

Technologies: Java (no frameworks) + JUnit + GSON + RESTful API

Description: This project was created in Java without the use of frameworks and aims to get acquainted with the limited principles of the work of JUnit, GSON and RESTful API.

The main idea of ​​the project is solved in the possibility of creating three types of tasks:

regular task - Task;
large tasks that include other subtasks - Epic;
subtasks that are part of large tasks - Subtask.

At the moment, these tasks can be: created, deleted, updated, store the history of interaction with them, and there is also the ability to sort task data by priority and search for intersections (when one task starts before the end of the previous one).

In addition to the above, KVServer was added to the project with access via an API token, which allows you to store tasks on a storage.
