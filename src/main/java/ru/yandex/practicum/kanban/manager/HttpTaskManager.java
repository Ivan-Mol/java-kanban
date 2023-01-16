package ru.yandex.practicum.kanban.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.kanban.KVClient;
import ru.yandex.practicum.kanban.exceptions.ManagerSaveException;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class HttpTaskManager extends SavingTaskManager {
    private static final String SUBTASKS_KEY = "skey";
    private static final String TASKS_KEY = "tkey";
    private static final String EPICS_KEY = "eKey";
    private static final String PRIORITIZED_TASKS_KEY = "pkey";
    private static final String HISTORY_KEY = "hkey";
    private final KVClient kvClient;
    private final Gson gson = new Gson();

    public HttpTaskManager(String url) throws IOException, URISyntaxException, InterruptedException {
        this.kvClient = new KVClient(url);
    }

    @Override
    public void save() {
        try {
            String stringSubtasks = gson.toJson(subtasks);
            kvClient.put(SUBTASKS_KEY, stringSubtasks);

            String stringTasks = gson.toJson(tasks);
            kvClient.put(TASKS_KEY, stringTasks);

            String stringEpic = gson.toJson(epics);
            kvClient.put(EPICS_KEY, stringEpic);

            String stringPrioritizedtasks = gson.toJson(subtasks);
            kvClient.put(PRIORITIZED_TASKS_KEY, stringPrioritizedtasks);

            String stringHistory = gson.toJson(getHistoryManager().getHistory());
            kvClient.put(HISTORY_KEY, stringHistory);

        } catch (IOException | InterruptedException | URISyntaxException e) {
            throw new ManagerSaveException(e);
        }
    }

    public void load() throws IOException, URISyntaxException, InterruptedException {
        Map<Integer, Subtask> tempSubtasks = gson.fromJson(kvClient.load(SUBTASKS_KEY), new TypeToken<>() {
        });
        subtasks.putAll(Optional.ofNullable(tempSubtasks).orElse(new HashMap<>()));
        Map<Integer, Task> tempTasks = gson.fromJson(kvClient.load(TASKS_KEY), new TypeToken<>() {
        });
        tasks.putAll(Optional.ofNullable(tempTasks).orElse(new HashMap<>()));
        Map<Integer, Epic> tempEpics = gson.fromJson(kvClient.load(EPICS_KEY), new TypeToken<>() {
        });
        epics.putAll(Optional.ofNullable(tempEpics).orElse(new HashMap<>()));

        Set<Task> priorTasks = gson.fromJson(kvClient.load(PRIORITIZED_TASKS_KEY), new TypeToken<>() {
        });
        prioritizedTasks.addAll(Optional.ofNullable(priorTasks).orElse(new HashSet<>()));
        List<Task> history = gson.fromJson(kvClient.load(HISTORY_KEY), new TypeToken<>() {
        });
        List<Task> tempHistory = Optional.ofNullable(history).orElse(new ArrayList<>());
        for (Task task : tempHistory) {
            getHistoryManager().add(task);
        }
    }

}
