package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.KVServer;
import ru.yandex.practicum.kanban.model.Task;

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
    public void testLoadFromEmpty() throws IOException, URISyntaxException, InterruptedException {
        taskManager.load();
    }

    @Test
    public void testLoadWithTasks() throws IOException, URISyntaxException, InterruptedException {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(testTask);
        taskManager.getTask(testTask.getId());
        taskManager.save();
        HttpTaskManager taskManager2 = new HttpTaskManager("http://localhost:8078");
        taskManager2.load();
        Assertions.assertEquals(taskManager.tasks, taskManager2.tasks);
        Assertions.assertEquals(taskManager.epics, taskManager2.epics);
        Assertions.assertEquals(taskManager.subtasks, taskManager2.subtasks);
        Assertions.assertEquals(taskManager.getPrioritizedTasks(), taskManager2.getPrioritizedTasks());
        Assertions.assertEquals(taskManager.getHistory(), taskManager2.getHistory());
    }


    @AfterEach
    public void afterEach() {
        server.stop();
    }
}