package core;

public class AVLTreeConAyudas<T extends Comparable<? super T>> implements AVLTreeInterface<T> {
    private Node<T> root;
    private int size;

    @Override
    public void insert(T myData) {
        if (myData == null)
            throw new IllegalArgumentException("Data cannot be null");

        System.out.println("Insertando: " + myData);
        root = insert(root, myData);

        System.out.println("Arbol despues de insertar " + myData + ":");
        System.out.println(this);
        System.out.println("----------------------------");
    }

    private Node<T> insert(Node<T> currentNode, T myData) {
        if (currentNode == null)
            return new Node<>(myData);

        if (myData.compareTo(currentNode.data) <= 0)
            currentNode.left = insert(currentNode.left, myData);
        else
            currentNode.right = insert(currentNode.right, myData);

        currentNode.height = Math.max(height(currentNode.left), height(currentNode.right)) + 1;

        int balance = getBalance(currentNode);

        // Caso derecha pesada
        if (balance < -1) {

            // Caso Right-Left: rotacion doble
            if (getBalance(currentNode.right) > 0) {
                System.out.println("Rotacion DOBLE derecha-izquierda en nodo " + currentNode.data);

                System.out.println("Paso intermedio: rotacion simple derecha sobre " + currentNode.right.data);
                currentNode.right = rightRotate(currentNode.right);

                System.out.println("Subarbol luego del paso intermedio:");
                System.out.println(currentNode);

                System.out.println("Paso final: rotacion simple izquierda sobre " + currentNode.data);
                return leftRotate(currentNode);
            }

            // Caso Right-Right: rotacion simple izquierda
            System.out.println("Rotacion SIMPLE izquierda en nodo " + currentNode.data);
            return leftRotate(currentNode);
        }

        // Caso izquierda pesada
        if (balance > 1) {

            // Caso Left-Right: rotacion doble
            if (getBalance(currentNode.left) < 0) {
                System.out.println("Rotacion DOBLE izquierda-derecha en nodo " + currentNode.data);

                System.out.println("Paso intermedio: rotacion simple izquierda sobre " + currentNode.left.data);
                currentNode.left = leftRotate(currentNode.left);

                System.out.println("Subarbol luego del paso intermedio:");
                System.out.println(currentNode);

                System.out.println("Paso final: rotacion simple derecha sobre " + currentNode.data);
                return rightRotate(currentNode);
            }

            // Caso Left-Left: rotacion simple derecha
            System.out.println("Rotacion SIMPLE derecha en nodo " + currentNode.data);
            return rightRotate(currentNode);
        }

        return currentNode;
    }

    private int getBalance(Node<T> currentNode) {
        if(currentNode == null)
            return 0;
        return height(currentNode.left) - height(currentNode.right);
    }

    private int height(Node<T> currentNode) {
        if (currentNode == null)
            return 0;
        return currentNode.height;
    }

    public int getHeight() {
        return root.height;
    }

    private Node<T> rightRotate(Node<T> currentNode) {
        Node<T> r = currentNode.left;
        currentNode.left = r.right;
        r.right = currentNode;
        currentNode.height = Math.max(height(currentNode.left), height(currentNode.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }

    private Node<T> leftRotate(Node<T> currentNode) {
        Node<T> r = currentNode.right;
        currentNode.right = r.left;
        r.left = currentNode;
        currentNode.height = Math.max(height(currentNode.left), height(currentNode.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }


    public NodeTreeInterface<T> getRoot() {
        return root;
    }

    @Override
    public boolean find(T myData) {
        if(root==null)
            return false;
        return root.hasChildren(myData);
    }

    @Override
    public String toString() {
        if (root == null)
            return "";
        return root.toString();
    }

    private class Node<T extends Comparable<? super T>> implements NodeTreeInterface<T>{
        private T data;
        private Node left;
        private Node right;
        private int height;

        public Node(T data, Node<T> left, Node<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.height = 1;
        }

        public Node(T data) {
            this(data, null, null);
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

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            print(buffer, "", "");
            return buffer.toString();
        }

        private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
            buffer.append(prefix);
            buffer.append(data);
            buffer.append('\n');
            if(left!=null)
                left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
            if(right!=null)
                right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
        }

        private boolean hasChildren(T myData) {
            if(data.compareTo(myData)==0)
                return true;
            if(data.compareTo(myData)>0 && left!=null)
                return left.hasChildren(myData);
            if(data.compareTo(myData)<0 && right!=null)
                return right.hasChildren(myData);
            return false;
        }

    }

    public static void main(String[] args) {
        AVLTreeConAyudas<Integer> avl = new AVLTreeConAyudas<>();

        avl.insert(19);
        avl.insert(7);
        avl.insert(29);
        avl.insert(13);
        avl.insert(20);
        avl.insert(80);
        avl.insert(45);

        avl.insert(77);
        avl.insert(10);
        avl.insert(82);


        System.out.println("==== Arbol final ====");
        System.out.println(avl);
    }
}
