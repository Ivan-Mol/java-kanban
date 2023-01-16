package ru.yandex.practicum.kanban.manager;


import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void removeAllTasks() throws TaskNotFoundException;

    void removeAllEpics();

    void removeAllSubtasks();

    Task createTask(Task newTask);

    Epic createEpic(Epic newEpic);

    Subtask createSubtask(Subtask newSubtask);

    void updateTask(Task updatedTask);

    Task getEpic(int id) throws TaskNotFoundException;

    Task getTask(int id) throws TaskNotFoundException;

    Task getSubtask(int id) throws TaskNotFoundException;

    void removeEpic(int id);

    void removeTask(int id) throws TaskNotFoundException;

    void removeSubtask(int id);

    Status calcEpicStatus(Epic epic);

    LocalDateTime calcEpicEndTime(Epic epic);

    LocalDateTime calcEpicStartTime(Epic epic);

    Duration calcEpicDuration(Epic epic);

    Map<Integer, Subtask> getEpicSubtasks(int id) throws TaskNotFoundException;

    List<Task> getPrioritizedTasks();

    List<Task> getHistory();

    HistoryManager getHistoryManager();

    void setIdIfNull(Task task);
}
