package model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, Status status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, Status status, String description, int epicId) {
        super(id, name, status, description);
        this.epicId = epicId;
    }

    public static Subtask fromString(String value) {
        //id,type,name,status,description,epic
        //2,EPIC,Epic2,DONE,Description epic2,
        //3,SUBTASK,Sub Task2,DONE,Description sub task3,2
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        return new Subtask(id, name, stat, decription, Integer.parseInt(values[5]));
    }

    @Override
    public String toString() {
        return getId() + "," + Type.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getEpicId() + "," + "\n";
    }

    public int getEpicId() {
        return epicId;
    }
}
