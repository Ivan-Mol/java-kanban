package ru.yandex.practicum.kanban.handler;

import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.kanban.exceptions.IncorrectTaskException;
import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;
import ru.yandex.practicum.kanban.manager.TaskManager;
import ru.yandex.practicum.kanban.model.Epic;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class EpicHandler extends BaseHandler {

    private final TaskManager manager;

    public EpicHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected Object handleInner(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod(); //метод запроса GET POST DELETE
        String query = httpExchange.getRequestURI().getQuery(); //param1=val1&param2=val2...

        if (requestMethod.equals("GET")) {
            if (query != null) {
                return manager.getEpic(getIdFromQuery(query));
            } else {
                return manager.getAllEpics();
            }
        }
        if (requestMethod.equals("POST")) {
            return postEpicToManager(httpExchange);
        }
        if (requestMethod.equals("DELETE")) {
            if (query != null) {
                manager.removeEpic(getIdFromQuery(query));
                return "Epic removed";
            } else {
                manager.removeAllEpics();
                return "All Epics removed";
            }
        }
        throw new UnexpectedException("Method " + requestMethod + " is not supported");
    }

    private Object postEpicToManager(HttpExchange httpExchange) throws IOException {
        Epic epicFromJson = null;
        String reqBody = new String(httpExchange.getRequestBody().readAllBytes());
        try {
            epicFromJson = gson.fromJson(reqBody, Epic.class);
        } catch (JsonSyntaxException e) {
            throw new IncorrectTaskException("Incorrect body of Epic");
        }
        try {
            manager.getEpic(epicFromJson.getId());
            manager.updateTask(epicFromJson);
            return reqBody;
        } catch (TaskNotFoundException e) {
            return manager.createEpic(epicFromJson);
        }
    }
}