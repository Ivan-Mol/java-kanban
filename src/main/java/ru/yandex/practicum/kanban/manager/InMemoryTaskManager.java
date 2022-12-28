package ru.yandex.practicum.kanban.manager;


import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();

    private final HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public List<Epic> getAllEpics() {
        //Получение списка всех задач.
        return new ArrayList<>(epics.values());

    }

    @Override
    public List<Task> getAllTasks() {
        //Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        //Получение списка всех задач.
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public HistoryManager getHistoryManager() {
        return inMemoryHistoryManager;
    }

    @Override
    public void removeAllEpics() {
        //Удаление всех Эпиков.
        List<Integer> subtaskIds = new ArrayList<>();
        for (Epic epic : epics.values()) {
            subtaskIds.addAll(epic.getSubtasksId());
        }
        for (Integer id : subtaskIds) {
            removeSubtask(id);
        }
        for (Integer id : epics.keySet()) {
            removeEpic(id);
        }
    }

    @Override
    public void removeAllTasks() {
        //Удаление всех задач.
        for (Task task : tasks.values()) {
            removeTask(task.getId());
        }
    }

    @Override
    public void removeAllSubtasks() {
        //Удаление всех Сабтасков.
        for (Subtask subtask : subtasks.values()) {
            removeSubtask(subtask.getId());
        }
    }

    @Override
    public void createTask(Task newTask) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newTask != null) {
            tasks.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void createEpic(Epic newEpic) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newEpic != null) {
            epics.put(newEpic.getId(), newEpic);
        }
    }

    @Override
    public void createSubtask(Subtask newSubtask) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newSubtask != null) {
            Epic epicOfThisSubtask = epics.get(newSubtask.getEpicId());
            if (epicOfThisSubtask != null) {
                subtasks.put(newSubtask.getId(), newSubtask);
                epicOfThisSubtask.addSubtaskId(newSubtask.getId());
                recalcEpicData(epicOfThisSubtask.getId());
            }
        }
    }

    @Override
    public void updateTask(Task updatedTask) {
        if (updatedTask != null) {
            if (epics.containsKey(updatedTask.getId())) {
                epics.replace(updatedTask.getId(), (Epic) updatedTask);
            } else if (tasks.containsKey(updatedTask.getId())) {
                tasks.replace(updatedTask.getId(), updatedTask);
            } else if (subtasks.containsKey(updatedTask.getId())) {
                Subtask subtask = (Subtask) updatedTask;
                subtasks.replace(updatedTask.getId(), subtask);
                recalcEpicData(subtask.getEpicId());
            }
        }
    }

    public void recalcEpicData(int epicId) {
        Epic epic = epics.get((epicId));
        epic.setStartTime(calcEpicStartTime(epic));
        epic.setDuration(calcEpicDuration(epic));
        epic.setEndTime(calcEpicEndTime(epic));
        epic.setStatus(calcEpicStatus(epic));
        epics.put(epic.getId(), epic);
    }

    @Override
    public Task getEpic(int id) {
        //Получение по идентификатору.
        if (epics.get(id) != null) {
            inMemoryHistoryManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Task getTask(int id) {
        //Получение по идентификатору.
        if (tasks.get(id) != null) {
            inMemoryHistoryManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public Task getSubtask(int id) {
        //Получение по идентификатору.
        if (subtasks.get(id) != null) {
            inMemoryHistoryManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public void removeEpic(int id) {
        //Удаление по идентификатору.
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            epic.getSubtasksId().forEach(subtasks::remove);
            inMemoryHistoryManager.remove(id);
            epics.remove(id);
        }
    }

    @Override
    public void removeTask(int id) {
        //Удаление по идентификатору.
        tasks.remove(id);
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void removeSubtask(int id) {
        //Удаление по идентификатору.
        if (subtasks.containsKey(id)) {
            Epic currentEpic = epics.get(subtasks.get(id).getEpicId());
            currentEpic.removeSubtaskId(id);
            recalcEpicData(currentEpic.getId());
            subtasks.remove(id);
            inMemoryHistoryManager.remove(id);
        }
    }

    @Override
    public Status calcEpicStatus(Epic epic) {
        ArrayList<Status> statuses = new ArrayList<>();
        for (Integer id : epic.getSubtasksId()) {
            if (subtasks.containsKey(id)) {
                statuses.add(subtasks.get(id).getStatus());
            }
        }
        if (statuses.isEmpty()) {
            return Status.NEW;
        } else if (statuses.contains(Status.IN_PROGRESS)) {
            return Status.IN_PROGRESS;
        } else if (!statuses.contains(Status.NEW)) {
            return Status.DONE;
        } else if (statuses.contains(Status.DONE)) {
            return Status.IN_PROGRESS;
        } else {
            return Status.NEW;
        }
    }

    @Override
    public LocalDateTime calcEpicEndTime(Epic epic) {
        return epic.getSubtasksId().stream()
                .map(id -> subtasks.get(id))
                .map(sub -> sub.getEndTime())
                .filter(endT -> endT != null)
                .max(Comparator.naturalOrder()).orElse(null);
    }

    @Override
    public LocalDateTime calcEpicStartTime(Epic epic) {
        return epic.getSubtasksId().stream()
                .map(id -> subtasks.get(id))
                .map(sub -> sub.getStartTime())
                .filter(s -> s != null)
                .min(Comparator.naturalOrder()).orElse(null);
    }

    @Override
    public Duration calcEpicDuration(Epic epic) {
        return epic.getSubtasksId().stream()
                .map(id -> subtasks.get(id))
                .map(sub -> sub.getDuration())
                .filter(dur -> dur != null)
                .reduce((duration, duration2) -> duration.plus(duration2)).orElse(null);
    }

    @Override
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}
