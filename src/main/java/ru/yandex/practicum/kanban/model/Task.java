package ru.yandex.practicum.kanban.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private static int idCounter = 0;
    private final String name;
    private final String description;
    private int id;
    private Status status;
    private Duration duration;
    private LocalDateTime startTime;

    public Task(int id, String name, Status status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(String name, String description, Status status) {
        this(++idCounter, name, status, description);
    }

    public Task(String name, String description) {
        this(name, description, Status.NEW);
    }

    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this(name, description);
        this.duration = duration;
        this.startTime = startTime;
    }

    public static Task fromString(String value) {
        String[] values = value.split(",", 8);
        int id = Integer.parseInt(values[0]);
        String name = values[2];
        Status stat = Status.valueOf(values[3]);
        String decription = values[4];
        Task newTask = new Task(id, name, stat, decription);
        if (!values[6].isEmpty()) {
            newTask.setDuration(Duration.parse(values[6]));
        }
        if (!values[7].isEmpty()) {
            newTask.setStartTime(LocalDateTime.parse(values[7]));
        }
        return newTask;
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
        if (getDuration() != null & getStartTime() != null) {
            return getId() + "," + Type.TASK + "," + getName() + "," + getStatus() + "," + getDescription() + ","
                    + "," + getDuration().toString() + "," + getStartTime().toString() + "\n";
        } else {
            return getId() + "," + Type.TASK + "," + getName() + "," + getStatus() + "," + getDescription() + ",,,\n";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description)
                && status == task.status && Objects.equals(duration, task.duration)
                && Objects.equals(startTime, task.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, duration, startTime);
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null) {
            return startTime.plusMinutes(duration.toMinutes());
        }
        return null;
    }

    public void setId() {
        idCounter++;
        this.id = idCounter;
    }
}
