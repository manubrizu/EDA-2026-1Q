package mabrizuela;

public class SimpleLinkedList <T>{
    private Node root = null;
    private Node tail = null;

    public void dump() {
        Node current = root;

        while (current!=null ) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        Node  newNode = new Node(data, null);

        if (root == null) {
            root = newNode;
            tail = newNode;
        }
        else {
            tail.next = newNode;
            tail = newNode;
        }
    }
}
