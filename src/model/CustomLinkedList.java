package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CustomLinkedList {
    HashMap<Integer,Node> nodeMap = new HashMap<>();
    private Node head;
    private Node tail;
    public void linkLast(Task task){
        Node currNode = new Node(task);
        if (tail==null){
            head = currNode;
            tail = currNode;
        }else {
            currNode.setPrevNode(tail);
            tail.setNextNode(currNode);
            tail = currNode;
        }
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
