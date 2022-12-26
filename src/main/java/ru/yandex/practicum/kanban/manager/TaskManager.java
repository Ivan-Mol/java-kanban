package ru.yandex.practicum.kanban.manager;


import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    void createTask(Task newTask);

    void createEpic(Epic newEpic);

    void createSubtask(Subtask newSubtask);

    void updateTask(Task updatedTask);

    Task getEpic(int id);

    Task getTask(int id);

    Task getSubtask(int id);

    void removeEpic(int id);

    void removeTask(int id);

    void removeSubtask(int id);

    Status calcEpicStatus(Epic epic);

    List<Task> getHistory();

    HistoryManager getHistoryManager();
}
