import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtaskId(int subtaskId) {
        this.subtasksId.add(subtaskId);
    }

    @Override
    public String toString() {
        return "Epic - " + this.getName() +
                " subtasks Id=" + subtasksId +
                "} " + super.toString();
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }
}
