package manager;

import exceptions.ManagerSaveException;
import model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file.toString());
            HashMap<String, Task> temporaryTasks = new HashMap<>();
            bufferedReader.readLine();
            while (bufferedReader.ready()) {
                String stringTask = bufferedReader.readLine();
                if (!stringTask.isEmpty()) {
                    Task task = fileBackedTasksManager.getTaskFromString(stringTask);
                    temporaryTasks.put(Integer.toString(task.getId()), task);
                } else {
                    String[] historyString = bufferedReader.readLine().split(",");
                    for (String id : historyString) {
                        fileBackedTasksManager.getHistoryManager().add(temporaryTasks.get(id));
                    }
                }
            }
            return fileBackedTasksManager;
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    public Task getTaskFromString(String stringTask) {
        String[] dataFromString = stringTask.split(",");
        Type type = Type.valueOf(dataFromString[1]);
        switch (type) {
            case EPIC:
                Epic epic = Epic.fromString(stringTask);
                createEpic(epic);
                return epic;
            case SUBTASK:
                Subtask subtask = Subtask.fromString(stringTask);
                createSubtask(subtask);
                return subtask;
            case TASK:
                Task task = Task.fromString(stringTask);
                createTask(task);
                return task;
            default:
                return null;
        }
    }

    public void save() {
        // Создайте метод save без параметров — он будет сохранять текущее состояние менеджера в указанный файл.
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, StandardCharsets.UTF_8))) {
            bufferedWriter.write("id,type,name,status,description,epic\n");
            for (Task task : super.getAllTasks()) {
                bufferedWriter.write(task.toString());
            }
            for (Task epic : super.getAllEpics()) {
                bufferedWriter.write(epic.toString());
            }
            for (Task subTask : super.getAllSubtasks()) {
                bufferedWriter.write(subTask.toString());
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(InMemoryHistoryManager.historyToString(super.getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    @Override
    public List<Epic> getAllEpics() {
        return super.getAllEpics();
    }

    @Override
    public List<Task> getAllTasks() {
        return super.getAllTasks();
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return super.getAllSubtasks();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllTasks() {
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
    public Task getEpic(int id) {
        return super.getEpic(id);
    }

    @Override
    public Task getTask(int id) {
        return super.getTask(id);
    }

    @Override
    public Task getSubtask(int id) {
        return super.getSubtask(id);
    }

    @Override
    public void removeEpic(int id) {
        super.removeEpic(id);
        save();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeSubtask(int id) {
        super.removeSubtask(id);
        save();
    }

    @Override
    public Status calcEpicStatus(Epic epic) {
        return super.calcEpicStatus(epic);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}
