package model;

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

    public Task(int id, String name, Status status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public static Task fromString(String value) {
        //id,type,name,status,description,epic
        //2,EPIC,Epic2,DONE,Description epic2,
        //3,SUBTASK,Sub Task2,DONE,Description sub task3,2
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        return new Task(id, name, stat, decription);
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
        return getId() + "," + Type.TASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + "\n";
    }

}
