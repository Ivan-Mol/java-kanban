package ru.yandex.practicum.kanban.handler;

import com.sun.net.httpserver.HttpExchange;
import ru.yandex.practicum.kanban.manager.TaskManager;

import java.io.IOException;
import java.rmi.UnexpectedException;

public class AllHandler extends BaseHandler {
    private final TaskManager manager;

    public AllHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    protected Object handleInner(HttpExchange httpExchange) throws IOException {
        String requestMethod = httpExchange.getRequestMethod(); //метод запроса GET POST DELETE
        String path = httpExchange.getRequestURI().getPath(); //путь запроса от /tasks/
        String[] pathParts = path.split("/");

        if (requestMethod.equals("GET")) {
            if (pathParts.length == 2) {
                return manager.getPrioritizedTasks();
            }
            if (pathParts[2].equals("history") && pathParts.length == 3) {
                return manager.getHistory();
            }
        }
        throw new UnexpectedException("Method " + requestMethod + " is not supported");
    }
}
