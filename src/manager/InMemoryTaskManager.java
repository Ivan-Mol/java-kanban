package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();

    private final HistoryManager InMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getAllTasks() {
        //Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        //Получение списка всех задач.
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        //Получение списка всех задач.
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void removeAllTasks() {
        //Удаление всех задач.
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        //Удаление всех задач.
        for (Epic epic : epics.values()) {
            ArrayList<Integer> epicSubtasks = epic.getSubtasksId();
            for (Integer epicSubtask : epicSubtasks) {
                removeById(epicSubtask);
            }
        }
        epics.clear();
    }

    @Override
    public void removeAllSubtasks() {
        //Удаление всех задач.
        for (Subtask subtask : subtasks.values()) {
            removeById(subtask.getEpicId());
        }
        subtasks.clear();
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
    public Task getById(int id) {
        //Получение по идентификатору.
        if (epics.containsKey(id)) {
            InMemoryHistoryManager.add(epics.get(id));
            return epics.get(id);
        } else if (tasks.containsKey(id)) {
            InMemoryHistoryManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            InMemoryHistoryManager.add(tasks.get(id));
            return subtasks.get(id);
        }
    }

    @Override
    public void removeById(int id) {
        //Удаление по идентификатору.
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else if (tasks.containsKey(id)) {
            epics.remove(id);
        } else subtasks.remove(id);
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
        return InMemoryHistoryManager.getHistory();
    }
}
