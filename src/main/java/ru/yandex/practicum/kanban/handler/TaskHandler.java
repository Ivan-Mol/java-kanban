package ru.yandex.practicum.kanban.handler;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.kanban.exceptions.IncorrectTaskException;
import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;
import ru.yandex.practicum.kanban.manager.TaskManager;
import ru.yandex.practicum.kanban.model.Task;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class TaskHandler extends BaseHandler {
    private final TaskManager manager;

    public TaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected Object handleInner(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod(); //метод запроса GET POST DELETE
        String query = httpExchange.getRequestURI().getQuery(); //param1=val1&param2=val2...

        if (requestMethod.equals("GET")) {
            if (query != null) {
                return manager.getTask(getIdFromQuery(query));
            } else {
                return manager.getAllTasks();
            }
        }
        if (requestMethod.equals("POST")) {
            return postTaskToManager(httpExchange);
        }
        if (requestMethod.equals("DELETE")) {
            if (query != null) {
                manager.removeTask(getIdFromQuery(query));
                return "Task removed";
            } else {
                manager.removeAllTasks();
                return "All Tasks removed";
            }
        }
        throw new UnexpectedException("Method " + requestMethod + " is not supported");
    }

    private String postTaskToManager(HttpExchange httpExchange) throws IOException {
        Task taskFromJson = null;
        String reqBody = new String(httpExchange.getRequestBody().readAllBytes());
        try {
            taskFromJson = gson.fromJson(reqBody, Task.class);
        } catch (JsonSyntaxException e) {
            throw new IncorrectTaskException("Incorrect body of Epic");
        }
        try {
            manager.getTask(taskFromJson.getId());
            manager.updateTask(taskFromJson);
            return "Task updated";
        } catch (TaskNotFoundException e) {
            manager.createTask(taskFromJson);
            return "Task Added";
        }
    }
}
