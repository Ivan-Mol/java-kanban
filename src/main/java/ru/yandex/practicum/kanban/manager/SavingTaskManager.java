package ru.yandex.practicum.kanban.manager;

import ru.yandex.practicum.kanban.exceptions.TaskNotFoundException;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

public abstract class SavingTaskManager extends InMemoryTaskManager {

    public abstract void save();

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllTasks() throws TaskNotFoundException {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void createTask(Task newTask) {
        super.createTask(newTask);
        save();
    }

    @Override
    public void createEpic(Epic newEpic) {
        super.createEpic(newEpic);
        save();
    }

    @Override
    public void createSubtask(Subtask newSubtask) {
        super.createSubtask(newSubtask);
        save();
    }

    @Override
    public void updateTask(Task updatedTask) {
        super.updateTask(updatedTask);
        save();
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeTask(int id) throws TaskNotFoundException {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }
}
