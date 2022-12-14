package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        for (Epic epic : epics.values()) {
            ArrayList<Integer> epicSubtasks = epic.getSubtasksId();
            for (Integer epicSubtask : epicSubtasks) {
                removeSubtask(epicSubtask);
            }
            removeEpic(epic.getId());
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
            removeSubtask(subtask.getEpicId());
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
            subtasks.put(newSubtask.getId(), newSubtask);
            Epic epicOfThisSubtask = epics.get(newSubtask.getEpicId());
            epicOfThisSubtask.addSubtaskId(newSubtask.getId());
            epicOfThisSubtask.setStatus(calcEpicStatus(epicOfThisSubtask));
        }
    }

    @Override
    public void updateTask(Task updatedTask) {
        if (epics.containsKey(updatedTask.getId())) {
            epics.replace(updatedTask.getId(), (Epic) updatedTask);
        } else if (tasks.containsKey(updatedTask.getId())) {
            tasks.replace(updatedTask.getId(), updatedTask);
        } else if (subtasks.containsKey(updatedTask.getId())) {
            subtasks.replace(updatedTask.getId(), (Subtask) updatedTask);
        } else {
            System.out.println("Что-то пошло не так");
        }
    }

    @Override
    public Task getEpic(int id) {
        //Получение по идентификатору.
        inMemoryHistoryManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getTask(int id) {
        //Получение по идентификатору.
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Task getSubtask(int id) {
        //Получение по идентификатору.
        inMemoryHistoryManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void removeEpic(int id) {
        //Удаление по идентификатору.
        epics.remove(id);
        inMemoryHistoryManager.remove(id);
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
        subtasks.remove(id);
        inMemoryHistoryManager.remove(id);
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
    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }
}
