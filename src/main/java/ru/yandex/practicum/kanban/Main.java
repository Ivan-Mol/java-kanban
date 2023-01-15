package ru.yandex.practicum.kanban;

import ru.yandex.practicum.kanban.manager.Managers;
import ru.yandex.practicum.kanban.manager.TaskManager;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        new KVServer().start();
        TaskManager taskManager = Managers.getDefault();
        new HttpTaskServer(taskManager);
    }
}