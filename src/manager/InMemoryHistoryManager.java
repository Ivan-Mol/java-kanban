package manager;

import model.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> tasksHistory = new ArrayList<>();


    @Override
    public void add(Task task) {
        if (tasksHistory.size() < 10) {
            tasksHistory.add(task);
        } else {
            tasksHistory.remove(0);
            tasksHistory.add(task);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return tasksHistory;
    }
}
