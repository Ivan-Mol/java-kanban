import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();
    private final Map<Integer, Task> tasks = new HashMap<>();

    public ArrayList<Task> getAllTasks() {
        //Получение списка всех задач.
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        //Получение списка всех задач.
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        //Получение списка всех задач.
        return new ArrayList<>(subtasks.values());
    }

    public void removeAllTasks() {
        //Удаление всех задач.
        tasks.clear();
    }

    public void removeAllEpics() {
        //Удаление всех задач.
        for (Epic epic: epics.values()) {
            ArrayList<Integer> epicSubtasks = epic.getSubtasksId();
            for (Integer epicSubtask : epicSubtasks) {
                removeById(epicSubtask);
            }
        }
        epics.clear();
    }

    public void removeAllSubtasks() {
        //Удаление всех задач.
        for (Subtask subtask: subtasks.values()) {
            removeById(subtask.getEpicId());
        }
        subtasks.clear();
    }

    public void createTask(Task newTask) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newTask != null) {
            tasks.put(newTask.getId(), newTask);
        }
    }

    public void createEpic(Epic newEpic) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newEpic != null) {
            epics.put(newEpic.getId(), newEpic);
        }
    }

    public void createSubtask(Subtask newSubtask) {
        //Создание. Сам объект должен передаваться в качестве параметра.
        if (newSubtask != null) {
            subtasks.put(newSubtask.getId(), newSubtask);
            Epic epicOfThisSubtask = epics.get(newSubtask.getEpicId());
            epicOfThisSubtask.addSubtaskId(newSubtask.getId());
            epicOfThisSubtask.setStatus(calcEpicStatus(epicOfThisSubtask));
        }
    }
    //Должен ли в updateTask, при обновлении сабтаска обновляться статус у связанного с ним эпика?
    public void updateTask(Task updatedTask) {
        //Обновление. Новая версия объекта с верным идентификатором передаётся в виде параметра.
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

    public Task getById(int id) {
        //Получение по идентификатору.
        if (epics.containsKey(id)) {
            return epics.get(id);
        } else if (tasks.containsKey(id)) {
            return tasks.get(id);
        } else return subtasks.get(id);
    }

    //Правильно ли работает removeById? Из описания задачи не очень понятно как должно быть правильно.
    // Например, сейчас он не удаляет связанные сабтаски при удалении эпика.
    // Или если удаляешь последний сабтакс у эпика, нужно ли удалять сам эпик? Ведь эпик может быть и без сабтасков.
    //Должен ли обновляться статус эпика если удаляется сабтаск?
    //Стоит ли его сделать этот метод отдельным для каждого класса?
    public void removeById(int id) {
        //Удаление по идентификатору.
        if (epics.containsKey(id)) {
            epics.remove(id);
        } else if (tasks.containsKey(id)) {
            epics.remove(id);
        } else subtasks.remove(id);
    }

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
}
