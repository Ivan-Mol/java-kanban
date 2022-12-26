package ru.yandex.practicum.kanban.manager;


import ru.yandex.practicum.kanban.model.Task;

import java.util.List;

public interface HistoryManager {

    void add(Task task);

    void remove(int id);

    List<Task> getHistory();

}
