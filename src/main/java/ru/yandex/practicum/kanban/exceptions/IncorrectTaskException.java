package ru.yandex.practicum.kanban.exceptions;

public class IncorrectTaskException extends RuntimeException {
    public IncorrectTaskException(String message) {
        super(message);
    }
}
