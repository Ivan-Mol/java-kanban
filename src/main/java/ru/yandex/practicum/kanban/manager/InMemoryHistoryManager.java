package ru.yandex.practicum.kanban.manager;

import ru.yandex.practicum.kanban.model.Node;
import ru.yandex.practicum.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList tasksHistory = new CustomLinkedList();

    @Override
    public void add(Task task) {
        if (task != null) {
            tasksHistory.linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        tasksHistory.removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory.getTasks();
    }

    public static class CustomLinkedList {

        private final HashMap<Integer, Node> nodeMap = new HashMap<>();
        private Node head;
        private Node tail;

        public Node linkLast(Task task) {
            removeNode(task.getId());
            Node currNode = new Node(task);
            nodeMap.put(task.getId(), currNode);
            if (tail == null) {
                head = currNode;
                tail = currNode;
            } else {
                currNode.setPrevNode(tail);
                tail.setNextNode(currNode);
                tail = currNode;
            }
            return currNode;
        }

        public void removeNode(int id) {
            Node node = nodeMap.get(id);
            if (node != null) {
                Node prev = node.getPrevNode();
                Node next = node.getNextNode();
                if (prev != null) {
                    prev.setNextNode(next);
                } else {
                    head = next;
                }
                if (next != null) {
                    node.setNextNode(prev);
                } else {
                    tail = prev;
                }
            }

        }

        public ArrayList<Task> getTasks() {
            ArrayList<Task> tasks = new ArrayList<>();
            if (head != null) {
                tasks.add(head.getCurrentTask());
                Node currNode = head;
                while (currNode.getNextNode() != null) {
                    tasks.add(currNode.getNextNode().getCurrentTask());
                    currNode = currNode.getNextNode();
                }
            }
            return tasks;
        }

    }

}
