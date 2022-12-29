package ru.yandex.practicum.kanban.manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.kanban.model.Epic;
import ru.yandex.practicum.kanban.model.Status;
import ru.yandex.practicum.kanban.model.Subtask;
import ru.yandex.practicum.kanban.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @Test
    public void shouldReturnStatusNewWhenEmptySubtaskListIsGiven() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Assertions.assertEquals(Status.NEW, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusNewWhenAllSubtasksNew() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.NEW, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusDoneWhenAllSubtasksDone() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        testSubtask.setStatus(Status.DONE);
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.DONE, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusInProgressWhenSubtasksNewAndDone() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "epicTestDesc", testEpic.getId());
        testSubtask.setStatus(Status.DONE);
        taskManager.createSubtask(testSubtask);
        Subtask testSubtask2 = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask2);
        Assertions.assertEquals(Status.IN_PROGRESS, testEpic.getStatus());
    }

    @Test
    public void shouldReturnStatusInProgressWhenSubtasksInProgress() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        testSubtask.setStatus(Status.IN_PROGRESS);
        taskManager.createSubtask(testSubtask);
        Assertions.assertEquals(Status.IN_PROGRESS, testEpic.getStatus());
    }

    @Test
    public void shouldDoNothingWhenSubtaskWithNonExistentEpicIdIsGiven() {
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", Integer.MAX_VALUE);
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
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
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
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        List<Epic> actualEpics = taskManager.getAllEpics();
        List<Epic> expectedEpics = List.of(testEpic);
        Assertions.assertEquals(actualEpics, expectedEpics);
    }

    @Test
    public void getAllSubtasks_withOneSubtaskList() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        List<Subtask> actualSubtasks = taskManager.getAllSubtasks();
        List<Subtask> expectedSubtasks = List.of(testSubtask);
        Assertions.assertEquals(actualSubtasks, expectedSubtasks);
    }

    @Test
    public void getAllTasks_withOneTaskList() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
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
        Task testTask = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(testTask);
        taskManager.removeAllTasks();
        Assertions.assertTrue(taskManager.getAllTasks().isEmpty());
    }

    @Test
    public void removeAllSubtasks_SingleSubtask() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeAllSubtasks();
        Assertions.assertTrue(taskManager.getAllSubtasks().isEmpty());
        List<Epic> actualEpics = taskManager.getAllEpics();
        List<Epic> expectedEpics = List.of(testEpic);
        Assertions.assertEquals(actualEpics, expectedEpics);

    }

    @Test
    public void removeAllEpics_SingleEpic() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
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
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        taskManager.removeEpic(testEpic.getId());
        Assertions.assertNull(taskManager.getEpic(testEpic.getId()));
        Assertions.assertNull(taskManager.getSubtask(testSubtask.getId()));

    }

    @Test
    public void removeTask_oneTask() {
        Task testTask = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(testTask);
        taskManager.removeTask(testTask.getId());
        Assertions.assertNull(taskManager.getTask(testTask.getId()));

    }

    @Test
    public void removeSubtask_oneSubtask() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestDesc", "subTestDesc", testEpic.getId());
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
        Task testTask = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(testTask);
        testTask.setStatus(Status.DONE);
        taskManager.updateTask(testTask);
        taskManager.getTask(testTask.getId());
        Assertions.assertEquals(testTask, taskManager.getTask(testTask.getId()));
    }

    @Test
    public void updateTask_oneEpic() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Epic testEpicNew = Epic.fromString(testEpic.getId() + ",TASK,epicTestName,NEW,epicTestDesc,,,");
        taskManager.updateTask(testEpicNew);
        Assertions.assertEquals(testEpicNew, taskManager.getEpic(testEpicNew.getId()));
    }

    @Test
    public void updateTask_oneSubtask() {
        Epic testEpic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(testEpic);
        Subtask testSubtask = new Subtask("subTestName", "subTestDesc", testEpic.getId());
        taskManager.createSubtask(testSubtask);
        Subtask testSubNew = Subtask.fromString(testSubtask.getId() + ",TASK,newSubTestName,NEW,newSubTestDesc," + testEpic.getId() + ",,");
        taskManager.updateTask(testSubNew);
        Assertions.assertEquals(testSubNew, taskManager.getSubtask(testSubNew.getId()));
    }

    @Test
    public void calEpicDataWithEpicWithoutSubtasks() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        assertNull(taskManager.calcEpicStartTime(epic));
        assertNull(taskManager.calcEpicEndTime(epic));
        assertNull(taskManager.calcEpicDuration(epic));
    }

    @Test
    public void caEpicDataWithEpicWithSubtasks() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        taskManager.createSubtask(subtask);
        assertNull(taskManager.calcEpicStartTime(epic));
        assertNull(taskManager.calcEpicEndTime(epic));
        assertNull(taskManager.calcEpicDuration(epic));
    }

    @Test
    public void caEpicDataWithEpicWithTwoSubtasks() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        taskManager.createSubtask(subtask);
        Subtask subtask2 = new Subtask("subTestName2", "subTestDesc2", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(123);
        subtask2.setStartTime(dateTime);
        subtask2.setDuration(duration);
        taskManager.createSubtask(subtask2);
        assertEquals(subtask2.getStartTime(), taskManager.calcEpicStartTime(epic));
        assertEquals(subtask2.getDuration(), taskManager.calcEpicDuration(epic));
        assertEquals(subtask2.getEndTime(), taskManager.calcEpicEndTime(epic));
    }

    @Test
    public void caEpicDataWithEpicWithTwoSubtasksWithData() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now().minusDays(5);
        Duration duration = Duration.ofMinutes(37);
        subtask.setStartTime(dateTime);
        subtask.setDuration(duration);
        taskManager.createSubtask(subtask);
        Subtask subtask2 = new Subtask("subTestName2", "subTestDesc2", epic.getId());
        LocalDateTime dateTime2 = LocalDateTime.now();
        Duration duration2 = Duration.ofMinutes(123);
        subtask2.setStartTime(dateTime2);
        subtask2.setDuration(duration2);
        taskManager.createSubtask(subtask2);
        assertEquals(subtask.getStartTime(), taskManager.calcEpicStartTime(epic));
        assertEquals(duration.plus(duration2), taskManager.calcEpicDuration(epic));
        assertEquals(subtask2.getEndTime(), taskManager.calcEpicEndTime(epic));
    }

    @Test
    public void calcEpicDataAddSubtasksData() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(123);
        subtask.setStartTime(dateTime);
        subtask.setDuration(duration);
        subtask.setStatus(Status.IN_PROGRESS);
        taskManager.createSubtask(subtask);
        Epic epicFromManager = (Epic) taskManager.getEpic(epic.getId());
        assertEquals(subtask.getStartTime(), epicFromManager.getStartTime());
        assertEquals(subtask.getDuration(), epicFromManager.getDuration());
        assertEquals(subtask.getEndTime(), epicFromManager.getEndTime());
        assertEquals(subtask.getStatus(), epicFromManager.getStatus());
    }

    @Test
    public void calcEpicDataUpdateSubtasksData() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(123);
        subtask.setStartTime(dateTime);
        subtask.setDuration(duration);
        subtask.setStatus(Status.IN_PROGRESS);
        taskManager.createSubtask(subtask);
        subtask.setDuration(Duration.ofMinutes(23));
        subtask.setStartTime(LocalDateTime.now().minusDays(3));
        subtask.setStatus(Status.DONE);
        taskManager.updateTask(subtask);
        Epic epicFromManager = (Epic) taskManager.getEpic(epic.getId());
        assertEquals(subtask.getStartTime(), epicFromManager.getStartTime());
        assertEquals(subtask.getDuration(), epicFromManager.getDuration());
        assertEquals(subtask.getEndTime(), epicFromManager.getEndTime());
        assertEquals(subtask.getStatus(), epicFromManager.getStatus());
    }
    @Test
    public void calcEpicDataRemoveSubtaskWithData() {
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        LocalDateTime dateTime = LocalDateTime.now();
        Duration duration = Duration.ofMinutes(123);
        subtask.setStartTime(dateTime);
        subtask.setDuration(duration);
        subtask.setStatus(Status.IN_PROGRESS);
        taskManager.createSubtask(subtask);
        taskManager.removeSubtask(subtask.getId());
        Epic epicFromManager = (Epic) taskManager.getEpic(epic.getId());
        assertNull(epicFromManager.getStartTime());
    }
    @Test
    public void getEmptyPrioritizedTasks(){
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty());
    }
    @Test
    public void getPrioritizedTasksTestWithOneTaskWithoutData(){
        Task task = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(task);
        Assertions.assertIterableEquals(List.of(task),taskManager.getPrioritizedTasks());
    }
    @Test
    public void getPrioritizedTasksTestWithOneTaskWithData(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(2));
        taskManager.createTask(task);
        Assertions.assertIterableEquals(List.of(task),taskManager.getPrioritizedTasks());
    }
    @Test
    public void getPrioritizedTasksTestWithTaskWithDataAndTaskWithoutData(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(2));
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertEquals(List.of(task,subtask),taskManager.getPrioritizedTasks());
    }
    @Test
    public void getPrioritizedTasksTestWithTwoTasksWithoutData(){
        Task task = new Task("taskTestName", "taskTestDesc");
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertEquals(List.of(task,subtask),taskManager.getPrioritizedTasks());
    }
    @Test
    public void getPrioritizedTasksTestWithTwoTasksWithData(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now().plusDays(1));
        task.setDuration(Duration.ofMinutes(30));
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofMinutes(20));
        taskManager.createSubtask(subtask);
        Assertions.assertEquals(List.of(subtask,task),taskManager.getPrioritizedTasks());
        System.out.println(taskManager.getPrioritizedTasks());
    }
    @Test
    public void getPrioritizedTasksTestWithUpdatedTask(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now().plusDays(1));
        task.setDuration(Duration.ofMinutes(30));
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofMinutes(20));
        taskManager.createSubtask(subtask);
        task.setStartTime(LocalDateTime.now().minusDays(1));
        taskManager.updateTask(task);
        Assertions.assertEquals(List.of(task,subtask),taskManager.getPrioritizedTasks());
    }
    @Test
    public void RemoveOneTaskWithDataFromPrioritizedTasks(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(2));
        taskManager.createTask(task);
        taskManager.removeTask(task.getId());
        Assertions.assertTrue(taskManager.getPrioritizedTasks().isEmpty());
    }
    @Test
    public void removeOneSubtaskWithDataFromPrioritizedTasksWithTwoTasks(){
        Task task = new Task("taskTestName", "taskTestDesc");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofMinutes(2));
        taskManager.createTask(task);
        Epic epic = new Epic("epicTestName", "epicTestDesc");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("subTestName", "subTestDesc", epic.getId());
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofMinutes(20));
        taskManager.createSubtask(subtask);
        taskManager.removeSubtask(subtask.getId());
        Assertions.assertEquals(List.of(task),taskManager.getPrioritizedTasks());
    }

}
