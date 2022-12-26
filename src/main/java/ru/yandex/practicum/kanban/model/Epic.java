package ru.yandex.practicum.kanban.model;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public static Epic fromString(String value) {
        //id,type,name,status,description,epic
        //2,EPIC,Epic2,DONE,Description epic2,
        //3,SUBTASK,Sub Task2,DONE,Description sub task3,2
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        return new Epic(id, name, stat, decription);
    }

    public void addSubtaskId(int subtaskId) {
        this.subtasksId.add(subtaskId);
    }

    @Override
    public String toString() {
        return getId() + "," + Type.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() + "," + "\n";
        //id,type,name,status,description,epic
        //1,TASK,Task1,NEW,Description task1,
        //2,EPIC,Epic2,DONE,Description epic2
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return subtasksId.equals(epic.subtasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksId);
    }
}
