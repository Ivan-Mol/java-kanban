public class Task {
    private static int idCounter = 0;
    private final String name;
    private final String description;
    private final int id;
    private Status status;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        idCounter = idCounter + 1;
        this.id = idCounter;
        this.status = Status.NEW;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        idCounter++;
        this.id = idCounter;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    @Override
    public String toString() {
        return "Task{" + "name = '" + name + '\'' + ", description='"
                + description + '\'' + ", status = " + status + '}';
    }
}
