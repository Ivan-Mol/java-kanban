package ru.yandex.practicum.kanban.exceptions;

public class TaskNotFoundException extends Exception {

    public TaskNotFoundException(int id) {
        super("Task with id:" + id + " is not found");
    }
}
