package ru.yandex.practicum.kanban.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.kanban.manager.TaskManager;
import ru.yandex.practicum.kanban.model.Subtask;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class SubtaskHandler extends BaseHandler {
    private final TaskManager manager;

    public SubtaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected Object handleInner(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod(); //метод запроса GET POST DELETE
        String path = httpExchange.getRequestURI().getPath(); //путь запроса от /tasks/
        String query = httpExchange.getRequestURI().getQuery(); //param1=val1&param2=val2...
        String[] pathParts = path.split("/");
        if (requestMethod.equals("GET")) {
            if (query != null && pathParts.length == 4) {
                return manager.getEpicSubtasks(getIdFromQuery(query));
            }
            if (query != null) {
                return manager.getSubtask(getIdFromQuery(query));
            } else {
                return manager.getAllSubtasks();
            }
        }
        if (requestMethod.equals("POST")) {
            return postSubtaskToManager(httpExchange);
        }
        if (requestMethod.equals("DELETE")) {
            if (query != null) {
                manager.removeSubtask(getIdFromQuery(query));
                return "Subtask removed";
            } else {
                manager.removeAllSubtasks();
                return "All Subtasks removed";
            }
        }
        throw new UnexpectedException("Method " + requestMethod + " is not supported");
    }

    private String postSubtaskToManager(HttpExchange httpExchange) throws IOException {
        Subtask subtaskFromJson = null;
        String reqBody = new String(httpExchange.getRequestBody().readAllBytes());
        subtaskFromJson = gson.fromJson(reqBody, Subtask.class);
        manager.createSubtask(subtaskFromJson);
        return "Subtask Added";
    }
}
