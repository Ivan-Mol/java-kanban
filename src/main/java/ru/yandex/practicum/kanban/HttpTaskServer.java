package ru.yandex.practicum.kanban;

import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.kanban.handler.AllHandler;
import ru.yandex.practicum.kanban.handler.EpicHandler;
import ru.yandex.practicum.kanban.handler.SubtaskHandler;
import ru.yandex.practicum.kanban.handler.TaskHandler;
import ru.yandex.practicum.kanban.manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public HttpTaskServer(TaskManager manager) throws IOException {
        HttpServer httpServer = HttpServer.create(); // создали веб-сервер
        httpServer.bind(new InetSocketAddress(PORT), 0); // привязали его к порту
        httpServer.createContext("/tasks/task", new TaskHandler(manager));
        httpServer.createContext("/tasks/epic", new EpicHandler(manager));
        httpServer.createContext("/tasks/subtask", new SubtaskHandler(manager));
        httpServer.createContext("/tasks", new AllHandler(manager));
        httpServer.start(); // запускаем сервер

        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }
}