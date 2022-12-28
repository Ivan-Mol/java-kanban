package ru.yandex.practicum.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

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
        String[] values = value.split(",", 8);
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        int epicId = Integer.parseInt(values[5]);
        Subtask newSubtask = new Subtask(id, name, stat, decription, epicId);
        if (!values[6].isEmpty()) {
            newSubtask.setDuration(Duration.parse(values[6]));
        }
        if (!values[7].isEmpty()) {
            newSubtask.setStartTime(LocalDateTime.parse(values[7]));
        }
        return newSubtask;
    }

    @Override
    public String toString() {
        if (getDuration() != null & getStartTime() != null) {
            return getId() + "," + Type.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + ","
                    + getEpicId() + "," + getDuration() + "," + getStartTime() + "\n";
        } else {
            return getId() + "," + Type.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getEpicId() + ",,\n";
        }
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }
}
