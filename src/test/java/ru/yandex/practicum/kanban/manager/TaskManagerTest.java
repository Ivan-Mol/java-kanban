package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.util.List;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @Test
    public void shouldReturnStatusNewWhenEmptySubtaskListIsGiven() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Assertions.assertEquals(Status.NEW, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusNewWhenAllSubtasksNew() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.NEW, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusDoneWhenAllSubtasksDone() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        testSubtask.setStatus(Status.DONE);
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.DONE, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusInProgressWhenSubtasksNewAndDone() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        testSubtask.setStatus(Status.DONE);
        taskManager.createSubtask(testSubtask);
        Subtask testSubtask2 = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask2);
        Assertions.assertEquals(Status.IN_PROGRESS, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusInProgressWhenSubtasksInProgress() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        testSubtask.setStatus(Status.IN_PROGRESS);
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.IN_PROGRESS, testEpic.getStatus());
    }

    @Test
    public void shouldDoNothingWhenSubtaskWithNonExistentEpicIdIsGiven() {
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", Integer.MAX_VALUE);
        taskManager.createSubtask(testSubtask);
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldDoNothingWhenSubtaskIsNull() {
        taskManager.createSubtask(null);
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void shouldAddSubtaskWhenSubtaskWithExistentEpicIdIsGiven() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        List<Subtask> actualSubtasksList = taskManager.getAllSubtasks();
        List<Subtask> expectedSubtasksList = List.of(testSubtask);
        Assertions.assertEquals(expectedSubtasksList, actualSubtasksList);
        List<Integer> actualSubtasksIdList = testEpic.getSubtasksId();
        List<Integer> expectedSubtasksIdList = List.of(testSubtask.getId());
        Assertions.assertEquals(expectedSubtasksIdList, actualSubtasksIdList);

    }

    @Test
    public void getAllTasks_emptyList() {
        taskManager.createTask(null);
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void getAllSubtasks_emptyList() {
        taskManager.createSubtask(null);
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void getAllEpics_emptyList() {
        taskManager.createEpic(null);
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty());
    }

    @Test
    public void getAllEpics_withOneEpicList() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        List<Epic> actualEpics = taskManager.getAllEpics();
        List<Epic> expectedEpics = List.of(testEpic);
        Assertions.assertEquals(actualEpics, expectedEpics);
    }

    @Test
    public void getAllSubtasks_withOneSubtaskList() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        List<Subtask> actualSubtasks = taskManager.getAllSubtasks();
        List<Subtask> expectedSubtasks = List.of(testSubtask);
        Assertions.assertEquals(actualSubtasks, expectedSubtasks);
    }

    @Test
    public void getAllTasks_withOneTaskList() {
        Task testTask = new Task("TestTask", "Test Description");
        taskManager.createTask(testTask);
        List<Task> actualTask = taskManager.getAllTasks();
        List<Task> expectedTask = List.of(testTask);
        Assertions.assertEquals(actualTask, expectedTask);
    }

    @Test
    public void removeAllTasks_EmptyList() {
        taskManager.removeAllTasks();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void removeAllSubtasks_EmptyList() {
        taskManager.removeAllSubtasks();
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void removeAllEpics_EmptyList() {
        taskManager.removeAllEpics();
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty());
    }

    @Test
    public void removeAllTasks_SingleTask() {
        Task testTask = new Task("TestTask", "Test Description");
        taskManager.createTask(testTask);
        taskManager.removeAllTasks();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void removeAllSubtasks_SingleSubtask() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeAllSubtasks();
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
        List<Epic> actualEpics = taskManager.getAllEpics();
        List<Epic> expectedEpics = List.of(testEpic);
        Assertions.assertEquals(actualEpics, expectedEpics);

    }

    @Test
    public void removeAllEpics_SingleEpic() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeAllEpics();
        Assertions.assertTrue(taskManager.getAllEpics().isEmpty());
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
    }

    @Test
    public void removeEpic_emptyList() {
        int id = 100500;
        taskManager.removeEpic(id);
        Assertions.assertNull(taskManager.getEpic(id));

    }

    @Test
    public void removeTask_emptyList() {
        int id = 100500;
        taskManager.removeTask(id);
        Assertions.assertNull(taskManager.getTask(id));

    }

    @Test
    public void removeSubtask_emptyList() {
        int id = 100500;
        taskManager.removeSubtask(id);
        Assertions.assertNull(taskManager.getSubtask(id));

    }

    @Test
    public void removeEpic_oneEpic() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeEpic(testEpic.getId());
        Assertions.assertNull(taskManager.getEpic(testEpic.getId()));
        Assertions.assertNull(taskManager.getSubtask(testSubtask.getId()));

    }

    @Test
    public void removeTask_oneTask() {
        Task testTask = new Task("TestTask", "Test Description");
        taskManager.createTask(testTask);
        taskManager.removeTask(testTask.getId());
        Assertions.assertNull(taskManager.getTask(testTask.getId()));

    }

    @Test
    public void removeSubtask_oneSubtask() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("testSubtask", "Test Description", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeSubtask(testSubtask.getId());
        Assertions.assertNull(taskManager.getSubtask(testSubtask.getId()));
    }

    @Test
    public void updateTask_NonExistTask() {
        taskManager.updateTask(null);//doNothing
    }

    @Test
    public void updateTask_oneTask() {
        Task testTask = new Task("TestTask", "Test Description");
        taskManager.createTask(testTask);
        testTask.setStatus(Status.DONE);
        taskManager.updateTask(testTask);
        taskManager.getTask(testTask.getId());
        Assertions.assertEquals(testTask, taskManager.getTask(testTask.getId()));
    }

    @Test
    public void updateTask_oneEpic() {
        Epic testEpic = new Epic("TestEpic", "Test Description");
        taskManager.createEpic(testEpic);
        Epic testEpicNew = Epic.fromString(testEpic.getId() + ",TASK,тестТаскИмя,NEW,ТаскОписание,");
        taskManager.updateTask(testEpicNew);
        Assertions.assertEquals(testEpicNew, taskManager.getEpic(testEpicNew.getId()));
    }

    @Test
    public void updateTask_oneSubtask() {
        Epic testEpic = new Epic("тестЭпикИмя", "ЭпикОписание");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("тестСабИмя", "СабОписание", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        Subtask testSubNew = Subtask.fromString(testSubtask.getId() + ",TASK,тестТаскИмя,NEW,ТаскОписание," + testEpic.getId());
        taskManager.updateTask(testSubNew);
        Assertions.assertEquals(testSubNew, taskManager.getSubtask(testSubNew.getId()));
    }
}
