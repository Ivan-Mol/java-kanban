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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    private final String tempFile = "src/test/resources/testFile.csv";
    private final String emptyFile = "src/test/resources/loadFromFile_EmptyFile.csv";
    private final String withTasksAndHistoryFile = "src/test/resources/loadFromFile_FileTest.csv";

    @BeforeEach
    public void init() {
        taskManager = new FileBackedTasksManager(tempFile);
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
                FileBackedTasksManager.loadFromFile(new File(withTasksAndHistoryFile));
        Task testEpic = actualManager.getEpic(1);
        Assertions.assertEquals(new Epic(1, "Sport", Status.NEW, "Buy subscription"), testEpic);
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
                FileBackedTasksManager.loadFromFile(new File(emptyFile));
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
            byte[] actualFile = Files.readAllBytes(Path.of(tempFile));
            byte[] expectedFile = Files.readAllBytes(Path.of(emptyFile));
            Assertions.assertArrayEquals(expectedFile, actualFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldSaveFileBackedTasksManagerWhenOneEpicCreated() {
        Epic testEpic = new Epic(1, "Sport", Status.NEW, "Buy subscription");
        taskManager.createEpic(testEpic);
        taskManager.getEpic(testEpic.getId());
        taskManager.save();
        try {
            byte[] actualFile = Files.readAllBytes(Path.of(tempFile));
            byte[] expectedFile = Files.readAllBytes(Path.of(withTasksAndHistoryFile));
            Assertions.assertArrayEquals(expectedFile, actualFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldReturnTaskFromString() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        Task testTask = new Task("taskTestName", "taskTestDesc");
        Subtask testSubtask = new Subtask("subTestName", "subTestDesc", testEpic.getId());
        Epic fromStringEpic = Epic.fromString(testEpic.getId() + ",EPIC,epicTestName,NEW,epicTestDesc,,,");
        Task fromStringTask = Task.fromString(testTask.getId() + ",TASK,taskTestName,NEW,taskTestDesc,,,");
        Subtask fromStringSubtask = Subtask.fromString(testSubtask.getId() + ",SUBTASK,subTestName,NEW,subTestDesc," + testEpic.getId() + ",,");
        Assertions.assertEquals(testEpic, fromStringEpic);
        Assertions.assertEquals(testTask, fromStringTask);
        Assertions.assertEquals(testSubtask, fromStringSubtask);
    }

    @Test
    public void saveAndLoadFromFileWithTasks() {
        Task testTask =
                new Task("taskTestName", "taskTestDesc", Duration.ofMinutes(30), LocalDateTime.now());
        taskManager.createTask(testTask);
        taskManager.save();
        FileBackedTasksManager fff = FileBackedTasksManager.loadFromFile(new File(tempFile));
        Task task = fff.getTask(testTask.getId());
        Assertions.assertEquals(testTask, task);
    }
    @Test
    public void isValidTest(){
        Task testTask = new Task("someName","some Desc");
        testTask.setStartTime(LocalDateTime.now().plusHours(1));
        testTask.setDuration(Duration.ofMinutes(2));
        Assertions.assertTrue(taskManager.isValidTask(testTask));
        taskManager.createTask(testTask);
    }

    @AfterEach
    public void cleanUp() {
        try {
            Files.deleteIfExists(Path.of(tempFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
