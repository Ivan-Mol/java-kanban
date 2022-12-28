package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.util.List;

public class HistoryManagerTest {
    private HistoryManager historyManager;

    @BeforeEach
    public void init() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    public void add_withNonExistTask() {
        historyManager.add(null);
    }

    @Test
    public void add_withTwoSameTasks() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        List<Task> expectedTasks = List.of(testTask);
        historyManager.add(testTask);
        historyManager.add(testTask);
        Assertions.assertEquals(expectedTasks, historyManager.getHistory());
    }

    @Test
    public void remove_firstTask() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        historyManager.add(testTask);
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        historyManager.add(testEpic);
        Subtask testSubtask = new Subtask("subTestName", "subTestDesc", testEpic.getId());
        historyManager.add(testSubtask);
        historyManager.remove(testTask.getId());
        List<Task> expectedTasks = List.of(testEpic, testSubtask);
        Assertions.assertEquals(expectedTasks, historyManager.getHistory());
    }

    @Test
    public void remove_middleTask() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        historyManager.add(testTask);
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        historyManager.add(testEpic);
        Subtask testSubtask = new Subtask("subTestName", "subTestDesc", testEpic.getId());
        historyManager.add(testSubtask);
        historyManager.remove(testEpic.getId());
        List<Task> expectedTasks = List.of(testTask, testSubtask);
        Assertions.assertEquals(expectedTasks, historyManager.getHistory());
    }

    @Test
    public void remove_LastTask() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        historyManager.add(testTask);
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        historyManager.add(testEpic);
        Subtask testSubtask = new Subtask("subTestName", "subTestDesc", testEpic.getId());
        historyManager.add(testSubtask);
        historyManager.remove(testSubtask.getId());
        List<Task> expectedTasks = List.of(testTask, testEpic);
        Assertions.assertEquals(expectedTasks, historyManager.getHistory());
    }
}
