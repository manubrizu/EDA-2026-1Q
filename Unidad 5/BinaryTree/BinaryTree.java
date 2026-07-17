package mabrizuela;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;

public class BinaryTree implements BinaryTreeService {

    private Node root;

    private Scanner inputScanner;

    public BinaryTree(String fileName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, FileNotFoundException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);

        if (is == null)
            throw new FileNotFoundException(fileName);

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }



    private void buildTree() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        String token;
        Queue<NodeHelper> pendingOps = new LinkedList<>();

        root = new Node();
        pendingOps.add(new NodeHelper(root, NodeHelper.Action.CONSUMIR));

        while (inputScanner.hasNext()) {
            token = inputScanner.next();
            System.out.println(token);

            NodeHelper aPendingOp = pendingOps.remove();
            Node current = aPendingOp.getNode();

            if (token.equals("?")) {
                System.out.println("aaaaaaaaaaaaaaaa");
                pendingOps.add(new NodeHelper(null, NodeHelper.Action.CONSUMIR));    /// left
                pendingOps.add(new NodeHelper(null, NodeHelper.Action.CONSUMIR));    /// right
            }
            else{
                switch (aPendingOp.getAction()){
                    case LEFT:
                        current = current.setLeftTree(new Node());
                        break;
                    case RIGHT:
                        current = current.setRightTree(new Node());
                        break;
                }

                current.data = token;

                pendingOps.add(new NodeHelper(current, NodeHelper.Action.LEFT));
                pendingOps.add(new NodeHelper(current, NodeHelper.Action.RIGHT));
            }
        }

        if (root.data == null){
            root = null;
        }
    }


    @Override
    public void preorder() {
        if (root == null)
            return;
        System.out.println(root.preorder(new StringBuilder()));
    }


    @Override
    public void postorder() {
        if (root == null)
            return;
        System.out.println(root.postorder(new StringBuilder()));
    }

    @Override
    public void printHierarchy(){
        printHierarchyRec("", root);
    }

    public void printHierarchyRec(String prev, Node current){
        if(current == null){
            System.out.println(prev + "└──" + "null");
            return;
        }
        System.out.println(prev + "└──" + current.data);

        if (!current.isLeaf()) {
            printHierarchyRec(prev + "   ", current.left);
            printHierarchyRec(prev + "   ", current.right);
        }
    }


    public void toFile(String name) throws IOException {
        String path = "C:/Users/vicen/Documents/EDA 2025 2Q/TP5/BinaryTree/src/main/resources/" + name;

        PrintWriter writer = new PrintWriter(path, StandardCharsets.UTF_8);
        writer.print(printTree());
        writer.close();

        //    A
        //   B C

        // A B C ? ? ? ?
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BinaryTree b))
            return false;
        return printTree().equals(b.printTree());
    }

    private String printTree() {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.remove();

            if (current == null) {
                sb.append("? ");
            } else {
                sb.append(current.data).append(" ");

                queue.add(current.left);
                queue.add(current.right);
            }
        }

        return sb.toString().trim();
    }


    public int getHeight() {
        if(root == null)
            return -1;
        return getHeightRec(root);
    }

    public int getHeightRec(Node current){
        if (current.isLeaf()){
            return 0;
        }
        else if (current.left == null){
            return 1 + getHeightRec(current.right);
        }

        else if (current.right == null){
            return 1 + getHeightRec(current.left);
        }

        return 1 + Math.max(getHeightRec(current.left), getHeightRec(current.right));
    }

    class Node {
        private String data;
        private Node left;
        private Node right;

        public Node setLeftTree(Node aNode) {
            left = aNode;
            return left;
        }


        public Node setRightTree(Node aNode) {
            right = aNode;
            return right;
        }



        public Node() {
            // TODO Auto-generated constructor stub
        }

        private String preorder(StringBuilder s) {
            s.append(data).append(" ");
            if(left!=null)
                left.preorder(s);
            if(right!=null)
                right.preorder(s);
            return s.toString();
        }

        private String postorder(StringBuilder s) {
            if(left!=null)
                left.postorder(s);
            if(right!=null)
                right.postorder(s);
            s.append(data).append(" ");
            return s.toString();
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }


    }  // end Node class


    static class NodeHelper {

        static enum Action { LEFT, RIGHT, CONSUMIR };


        private Node aNode;
        private Action anAction;

        public NodeHelper(Node aNode, Action anAction ) {
            this.aNode= aNode;
            this.anAction= anAction;
        }


        public Node getNode() {
            return aNode;
        }

        public Action getAction() {
            return anAction;
        }

    }



    public static void main(String[] args) throws Exception {

        System.out.println("=== TEST PRINT TREE ===");

        BinaryTree t1 = new BinaryTree("data0_3");

        System.out.println("Hierarchy:");
        t1.printHierarchy();

        int height1 = t1.getHeight();
        System.out.println("Height árbol 1: " + height1);

        // genero archivo con printTree
        t1.toFile("test_output");

        // vuelvo a leer ese archivo
        BinaryTree t2 = new BinaryTree("test_output");

        System.out.println("\nComparo árboles:");
        if (t1.equals(t2)) {
            System.out.println("✔ OK: el árbol reconstruido es igual");
        } else {
            System.out.println("✘ ERROR: los árboles no coinciden");
        }

        // debug extra
        System.out.println("\nString original:");
        System.out.println(t1.printTree());

        System.out.println("\nString reconstruido:");
        System.out.println(t2.printTree());
    }

}