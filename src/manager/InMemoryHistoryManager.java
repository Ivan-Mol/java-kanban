package manager;

import model.Node;
import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final CustomLinkedList tasksHistory = new CustomLinkedList();
    private final HashMap<Integer, Node> nodeMap = new HashMap<>();

    @Override
    public void add(Task task) {
        remove(task.getId());
        nodeMap.put(task.getId(),tasksHistory.linkLast(task));
    }

    @Override
    public void remove(int id) {
        Node node = nodeMap.get(id);
        if (node!=null){
            tasksHistory.removeNode(node);
            nodeMap.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return tasksHistory.getTasks();
    }

    public static class CustomLinkedList {
        private Node head;
        private Node tail;
        public Node linkLast(Task task){
            Node currNode = new Node(task);
            if (tail==null){
                head = currNode;
                tail = currNode;
            }else {
                currNode.setPrevNode(tail);
                tail.setNextNode(currNode);
                tail = currNode;
            }
            return currNode;
        }
        public void removeNode(Node node){
            Node prev = node.getPrevNode();
            Node next = node.getNextNode();
            if (prev!=null){
                prev.setNextNode(next);
            }else {
                head = next;
            }
            if (next!=null){
                node.setNextNode(prev);
            }else {
                tail = prev;
            }
        }

        public ArrayList<Task> getTasks(){
            ArrayList<Task> tasks = new ArrayList<>();
            tasks.add(head.getCurrentTask());
            Node currNode = head;
            while (currNode.getNextNode()!=null){
                tasks.add(currNode.getNextNode().getCurrentTask());
                currNode = currNode.getNextNode();
            }
            return tasks;
        }

    }

}
