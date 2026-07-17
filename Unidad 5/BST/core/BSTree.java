package core;

import java.util.*;

public class BSTree<T extends Comparable<? super T>> implements BSTreeInterface<T>{

    private NodeT<T> root = null;
    private Traversal traversal;


    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public void insert(T myData) {
        if (root == null){
            root = new NodeT<>(myData);
        }
        else {
            root.insert(myData);
        }
    }


    public void delete(T myData){
        if (myData == null){
            throw new NullPointerException("myData is null");
        }

        if (root != null){
            root = root.delete(myData);
        }
    }

    @Override
    public void preOrder() {

    }

    @Override
    public void postOrder() {

    }

    @Override
    public void inOrder() {

    }

    // VERSION ITERATIVA
    public void inOrderIter(){
        Stack<NodeT<T>> stack = new Stack<>();

        NodeT<T> current = root;
        while ( ! stack.isEmpty() || current != null){
            if (current != null){
                stack.push(current);
                current = current.getLeft();
            }
            else {
                NodeT<T> element = stack.pop();
                System.out.print(element.getData() + " ");
                current = element.getRight();
            }
        }
    }

    // VERSION ITERABLE

    class BSTInOrderIter implements Iterator<T> {

        Stack<NodeT<T>> stack;
        NodeT<T> current;

        public BSTInOrderIter(){
            stack = new Stack<>();
            current = root;
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current !=  null){
                stack.push(current);
                current = current.getLeft();
            }

            NodeT<T> element = stack.pop();
            current = element.getRight();
            return element.getData();
        }
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        return root.getHeight();
    }

    @Override
    public Iterator<T> iterator() {
        switch (traversal){
            case INORDER:
                return new BSTInOrderIter();
            case BYLEVELS:
                return new BSTIterator();
            default:
                return null;
        }

    }

    class BSTIterator implements Iterator<T>{
        private Queue<NodeT<T>> queue;

        private BSTIterator(){
            queue = new LinkedList<NodeT<T>>();

            if (root != null){
                queue.add(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()){
                throw new NoSuchElementException();
            }

            NodeT<T> currentNode = queue.remove();

            if (currentNode.getLeft() != null){
                queue.add(currentNode.getLeft());
            }

            if (currentNode.getRight() != null){
                queue.add(currentNode.getRight());
            }

            return currentNode.getData();
        }
    }
}
