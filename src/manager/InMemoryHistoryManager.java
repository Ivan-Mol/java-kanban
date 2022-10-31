package manager;

import model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final LinkedList<Task> tasksHistory = new LinkedList<>();


    @Override
    public void add(Task task) {
        int TASKS_LIMIT = 10;
        if (tasksHistory.size() < TASKS_LIMIT) {
            tasksHistory.add(task);
        } else {
            tasksHistory.removeFirst();
            tasksHistory.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory;
    }
}
