package ru.yandex.practicum.kanban;

import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;
import ru.yandex.practicum.kanban.manager.Managers;
import ru.yandex.practicum.kanban.manager.TaskManager;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {


    public static void main(String[] args) throws IOException, TaskNotFoundException, URISyntaxException, InterruptedException {
        new KVServer().start();

        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("taskTest", "DescrTask");
        task.setStartTime(LocalDateTime.now().plusHours(1));
        task.setDuration(Duration.ofMinutes(2));
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        taskManager.createSubtask(subtask);
        Subtask subtask2 = new Subtask("subTestName2", "subTestDesc2", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now().plusDays(2);
        Duration duration = Duration.ofMinutes(123);
        subtask2.setStartTime(dateTime);
        subtask2.setDuration(duration);
        taskManager.createSubtask(subtask2);
        taskManager.getEpic(epic.getId());
        taskManager.getSubtask(subtask.getId());
    }
}