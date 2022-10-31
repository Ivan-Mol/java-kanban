package manager;

import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.util.ArrayList;

public interface TaskManager {

    public ArrayList<Task> getAllTasks();

    public ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubtasks();

    void createTask(Task newTask);

    void createEpic(Epic newEpic);

    void createSubtask(Subtask newSubtask);

    void updateTask(Task updatedTask);

    Task getById(int id);

    void removeById(int id);

    Status calcEpicStatus(Epic epic);

    ArrayList<Task> getHistory();
}
