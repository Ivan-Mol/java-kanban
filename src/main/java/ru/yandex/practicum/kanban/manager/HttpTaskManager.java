package ru.yandex.practicum.kanban.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.kanban.KVClient;
import ru.yandex.practicum.kanban.exceptions.ManagerSaveException;
import ru.yandex.practicum.kanban.model.Task;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

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
        subtasks.putAll(gson.fromJson(kvClient.load(SUBTASKS_KEY), new TypeToken<>() {
        }));
        tasks.putAll(gson.fromJson(kvClient.load(TASKS_KEY), new TypeToken<>() {
        }));
        epics.putAll(gson.fromJson(kvClient.load(EPICS_KEY), new TypeToken<>() {
        }));
        prioritizedTasks.addAll(gson.fromJson(kvClient.load(PRIORITIZED_TASKS_KEY), new TypeToken<>() {
        }));
        List<Task> tempHistory = gson.fromJson(kvClient.load(HISTORY_KEY), new TypeToken<>() {
        });
        for (Task task : tempHistory) {
            getHistoryManager().add(task);
        }
    }

}
