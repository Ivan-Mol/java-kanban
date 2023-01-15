package ru.yandex.practicum.kanban.manager;

import java.io.IOException;
import java.net.URISyntaxException;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() throws IOException, URISyntaxException, InterruptedException {
        return new HttpTaskManager("http://localhost:8078");
    }
}
