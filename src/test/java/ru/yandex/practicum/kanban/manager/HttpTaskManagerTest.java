package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.KVServer;

import java.io.IOException;
import java.net.URISyntaxException;

public class HttpTaskManagerTest extends TaskManagerTest<HttpTaskManager> {

    private KVServer server;

    @BeforeEach
    public void init() throws IOException, URISyntaxException, InterruptedException {
        server = new KVServer();
        server.start();
        taskManager = new HttpTaskManager("http://localhost:8078");
    }

    @Test
    public void testLoad() throws IOException, URISyntaxException, InterruptedException {
        taskManager.load();
    }


    @AfterEach
    public void afterEach() {
        server.stop();
    }
}