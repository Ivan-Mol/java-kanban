package ru.yandex.practicum.kanban;

import ru.yandex.practicum.kanban.manager.FileBackedTasksManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        //fromFile
        FileBackedTasksManager fromFile = FileBackedTasksManager
                .loadFromFile(new File("C:\\Users\\abyss\\OneDrive\\Рабочий стол\\test\\loadFile.csv"));
        fromFile.save();
    }
}