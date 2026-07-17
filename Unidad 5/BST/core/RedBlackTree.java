package core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

public class RedBlackTree<T extends Comparable<? super T>> implements BSTreeInterface<T>, AVLTreeInterface<T> {

    private enum Color { RED, BLACK }

    private class Node implements NodeTreeInterface<T> {
        private T data;
        private Node left;
        private Node right;
        private Node parent;
        private Color color;

        private Node(T data) {
            this.data = data;
            this.color = Color.RED;
        }

        @Override
        public T getData() {
            return data;
        }

        @Override
        public NodeTreeInterface<T> getLeft() {
            return left;
        }

        @Override
        public NodeTreeInterface<T> getRight() {
            return right;
        }
    }

    private Node root;
    private Traversal traversal = Traversal.INORDER;

    @Override
    public void setTraversal(Traversal traversal) {
        this.traversal = traversal;
    }

    @Override
    public void insert(T myData) {
        if (myData == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }

        Node newNode = new Node(myData);
        Node parent = null;
        Node current = root;

        while (current != null) {
            parent = current;
            if (myData.compareTo(current.data) <= 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (myData.compareTo(parent.data) <= 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        fixInsert(newNode);
    }

    @Override
    public boolean find(T data) {
        if (data == null) {
            return false;
        }

        Node current = root;
        while (current != null) {
            int cmp = data.compareTo(current.data);
            if (cmp == 0) {
                return true;
            }
            current = cmp < 0 ? current.left : current.right;
        }
        return false;
    }

    @Override
    public void preOrder() {
        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        System.out.println(sb.toString().trim());
    }

    @Override
    public void postOrder() {
        StringBuilder sb = new StringBuilder();
        postOrder(root, sb);
        System.out.print(sb.toString().trim());
    }

    @Override
    public void inOrder() {
        StringBuilder sb = new StringBuilder();
        inOrder(root, sb);
        System.out.print(sb.toString().trim());
    }

    @Override
    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        return height(root);
    }

    @Override
    public Iterator<T> iterator() {
        switch (traversal) {
            case INORDER:
                return new RBInOrderIter();
            case BYLEVELS:
                return new RBLevelOrderIter();
            default:
                throw new IllegalStateException("Unsupported traversal");
        }
    }

    private void fixInsert(Node node) {
        while (node != root && colorOf(node.parent) == Color.RED) {
            Node parent = node.parent;
            Node grandParent = parent.parent;

            if (grandParent == null) {
                break;
            }

            if (parent == grandParent.left) {
                Node uncle = grandParent.right;

                if (colorOf(uncle) == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    node = grandParent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        leftRotate(node);
                        parent = node.parent;
                        grandParent = parent == null ? null : parent.parent;
                    }

                    if (parent != null) {
                        parent.color = Color.BLACK;
                    }
                    if (grandParent != null) {
                        grandParent.color = Color.RED;
                        rightRotate(grandParent);
                    }
                }
            } else {
                Node uncle = grandParent.left;

                if (colorOf(uncle) == Color.RED) {
                    parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    grandParent.color = Color.RED;
                    node = grandParent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rightRotate(node);
                        parent = node.parent;
                        grandParent = parent == null ? null : parent.parent;
                    }

                    if (parent != null) {
                        parent.color = Color.BLACK;
                    }
                    if (grandParent != null) {
                        grandParent.color = Color.RED;
                        leftRotate(grandParent);
                    }
                }
            }
        }

        if (root != null) {
            root.color = Color.BLACK;
        }
    }

    private void leftRotate(Node pivot) {
        Node child = pivot.right;
        pivot.right = child.left;

        if (child.left != null) {
            child.left.parent = pivot;
        }

        child.parent = pivot.parent;

        if (pivot.parent == null) {
            root = child;
        } else if (pivot == pivot.parent.left) {
            pivot.parent.left = child;
        } else {
            pivot.parent.right = child;
        }

        child.left = pivot;
        pivot.parent = child;
    }

    private void rightRotate(Node pivot) {
        Node child = pivot.left;
        pivot.left = child.right;

        if (child.right != null) {
            child.right.parent = pivot;
        }

        child.parent = pivot.parent;

        if (pivot.parent == null) {
            root = child;
        } else if (pivot == pivot.parent.right) {
            pivot.parent.right = child;
        } else {
            pivot.parent.left = child;
        }

        child.right = pivot;
        pivot.parent = child;
    }

    private Color colorOf(Node node) {
        return node == null ? Color.BLACK : node.color;
    }

    private int height(Node node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private void preOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        sb.append(node.data).append(' ');
        preOrder(node.left, sb);
        preOrder(node.right, sb);
    }

    private void postOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        postOrder(node.left, sb);
        postOrder(node.right, sb);
        sb.append(node.data).append(' ');
    }

    private void inOrder(Node node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inOrder(node.left, sb);
        sb.append(node.data).append(' ');
        inOrder(node.right, sb);
    }

    private class RBInOrderIter implements Iterator<T> {
        private final Stack<Node> stack = new Stack<>();
        private Node current = root;

        @Override
        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        @Override
        public T next() {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            if (stack.isEmpty()) {
                throw new NoSuchElementException();
            }

            Node element = stack.pop();
            current = element.right;
            return element.data;
        }
    }

    private class RBLevelOrderIter implements Iterator<T> {
        private final Queue<Node> queue = new LinkedList<>();

        private RBLevelOrderIter() {
            if (root != null) {
                queue.add(root);
            }
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node currentNode = queue.remove();

            if (currentNode.left != null) {
                queue.add(currentNode.left);
            }

            if (currentNode.right != null) {
                queue.add(currentNode.right);
            }

            return currentNode.data;
        }
    }
}

