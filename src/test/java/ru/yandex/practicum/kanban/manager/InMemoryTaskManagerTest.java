package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.BeforeEach;

public class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    public void init() {
        taskManager = new InMemoryTaskManager();
    }
}

