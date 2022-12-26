package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.exceptions.ManagerSaveException;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private final String temporaryFileName = "src/test/resources/testFile.csv";
    private final String emptyFileName = "src/test/resources/loadFromFile_EmptyFile.csv";
    private final String withEpicFileName = "src/test/resources/loadFromFile_FileTest.csv";

    @BeforeEach
    public void init() {
        taskManager = new FileBackedTasksManager(temporaryFileName);
    }

    @Test
    public void shouldThrowExceptionWhenFileIsNotFound() {
        Assertions.assertThrows(
                ManagerSaveException.class,
                () -> FileBackedTasksManager.loadFromFile(new File("")));
    }

    @Test
    public void shouldReturnInitializedFileBackedTaskManagerWhenFileIsCorrect() {
        FileBackedTasksManager actualManager =
                FileBackedTasksManager.loadFromFile(new File(withEpicFileName));
        Task testEpic = actualManager.getEpic(1);
        Assertions.assertEquals(new Epic(1, "Магазин", Status.NEW, "Записаться в спортзал"), testEpic);
        Assertions.assertTrue(actualManager.getAllSubtasks().isEmpty());
        Assertions.assertTrue(actualManager.getAllTasks().isEmpty());
        List<Task> actualHistory = actualManager.getHistory();
        List<Task> expectedHistory = List.of(testEpic);
        Assertions.assertEquals(expectedHistory, actualHistory);
        actualManager.save();
    }

    @Test
    public void shouldReturnInitializedFileBackedTaskManagerWhenFileIsEmpty() {
        FileBackedTasksManager actualManager =
                FileBackedTasksManager.loadFromFile(new File(emptyFileName));
        Assertions.assertTrue(actualManager.getHistory().isEmpty());
        Assertions.assertTrue(actualManager.getAllSubtasks().isEmpty());
        Assertions.assertTrue(actualManager.getAllTasks().isEmpty());
        Assertions.assertTrue(actualManager.getAllEpics().isEmpty());
        actualManager.save();
    }

    @Test
    public void shouldSaveFileBackedTasksManagerWhenNoTasksCreated() {
        taskManager.save();
        try {
            byte[] actualFile = Files.readAllBytes(Path.of(temporaryFileName));
            byte[] expectedFile = Files.readAllBytes(Path.of(emptyFileName));
            Assertions.assertArrayEquals(expectedFile, actualFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldSaveFileBackedTasksManagerWhenOneEpicCreated() {
        Epic testEpic = new Epic(1, "Магазин", Status.NEW, "Записаться в спортзал");
        taskManager.createEpic(testEpic);
        taskManager.getEpic(testEpic.getId());
        taskManager.save();
        try {
            byte[] actualFile = Files.readAllBytes(Path.of(temporaryFileName));
            byte[] expectedFile = Files.readAllBytes(Path.of(withEpicFileName));
            Assertions.assertArrayEquals(expectedFile, actualFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldReturnTaskFromString() {
        Epic testEpic = new Epic("тестЭпикИмя", "ЭпикОписание");
        Task testTask = new Task("тестТаскИмя", "ТаскОписание");
        Subtask testSubtask = new Subtask("тестСабИмя", "СабОписание", testEpic.getId());
        Epic fromStringEpic = Epic.fromString(testEpic.getId() + ",EPIC,тестЭпикИмя,NEW,ЭпикОписание,");
        Task fromStringTask = Task.fromString(testTask.getId() + ",TASK,тестТаскИмя,NEW,ТаскОписание,");
        Subtask fromStringSubtask = Subtask.fromString(testSubtask.getId() + ",SUBTASK,тестСабИмя,NEW,СабОписание," + testEpic.getId() + ",");
        Assertions.assertEquals(testEpic, fromStringEpic);
        Assertions.assertEquals(testTask, fromStringTask);
        Assertions.assertEquals(testSubtask, fromStringSubtask);
    }

    @AfterEach
    public void cleanUp() {
        try {
            Files.deleteIfExists(Path.of(temporaryFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
