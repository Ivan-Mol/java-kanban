package ru.yandex.practicum.kanban.manager;

import ru.yandex.practicum.kanban.exceptions.ManagerSaveException;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;
import ru.yandex.practicum.kanban.model.Type;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends SavingTaskManager {
    private final String fileName;

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
                    String historyLine = bufferedReader.readLine();
                    if (historyLine != null) {
                        String[] historyString = historyLine.split(",");
                        for (String id : historyString) {
                            fileBackedTasksManager.getHistoryManager().add(temporaryTasks.get(id));
                        }
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
            bufferedWriter.write("id,type,name,status,description,epic,duration,startTime,endTime\n");
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
            bufferedWriter.write(historyToString(super.getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
    }

    private String historyToString(HistoryManager manager) {
        List<String> idList = new ArrayList<>();
        for (Task task : manager.getHistory()) {
            idList.add(Integer.toString(task.getId()));
        }
        return String.join(",", idList);
    }

}
