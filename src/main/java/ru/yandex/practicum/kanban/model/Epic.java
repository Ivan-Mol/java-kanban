package ru.yandex.practicum.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksId = new ArrayList<>();
    private LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public static Epic fromString(String value) {
        String[] values = value.split(",", 9);
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        Epic newEpic = new Epic(id, name, stat, decription);
        if (!values[6].isEmpty()) {
            newEpic.setDuration(Duration.parse(values[6]));
        }
        if (!values[7].isEmpty()) {
            newEpic.setStartTime(LocalDateTime.parse(values[7]));
        }
        if (!values[8].isEmpty()) {
            newEpic.setEndTime(LocalDateTime.parse(values[8]));
        }
        return newEpic;
    }

    public void addSubtaskId(int subtaskId) {
        this.subtasksId.add(subtaskId);
    }

    @Override
    public String toString() {
        if (getStartTime() != null) {
            return getId() + "," + Type.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() + ","
                    + "," + getDuration().toString() + "," + getStartTime().toString() + ","+getEndTime().toString()+"\n";
        } else {
            return getId() + "," + Type.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() + ",,,,\n";
        }
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void removeSubtaskId(int id) {
        int index = subtasksId.indexOf(id);
        subtasksId.remove(index);
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(subtasksId, epic.subtasksId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksId);
    }
}
