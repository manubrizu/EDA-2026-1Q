package core;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.Function;

public class BinaryTree {

    private Node root;
    private Scanner inputScanner;

    public BinaryTree(String fileName)
            throws IOException,
            InstantiationException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException,
            NoSuchMethodException,
            SecurityException {

        InputStream is = Files.newInputStream(Paths.get(fileName));

        inputScanner = new Scanner(is);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }

    private void buildTree() {

        Queue<NodeHelper> pendingOps = new LinkedList<>();
        String token;

        root = new Node();

        pendingOps.add(
                new NodeHelper(
                        root,
                        (Node n) -> n
                )
        );

        while (inputScanner.hasNext()) {

            token = inputScanner.next();

            NodeHelper aPendingOp = pendingOps.remove();
            Node currentNode = aPendingOp.getNode();

            if (token.equals("?")) {

                pendingOps.add(
                        new NodeHelper(
                                null,
                                (Node n) -> n
                        )
                );

                pendingOps.add(
                        new NodeHelper(
                                null,
                                (Node n) -> n
                        )
                );

            } else {

                Function<Node, Node> anAction =
                        aPendingOp.getAction();

                currentNode =
                        anAction.apply(currentNode);

                currentNode.data = token;

                pendingOps.add(
                        new NodeHelper(
                                currentNode,
                                (Node n) ->
                                        n.setLeftTree(new Node())
                        )
                );

                pendingOps.add(
                        new NodeHelper(
                                currentNode,
                                new Function<Node, Node>() {
                                    @Override
                                    public Node apply(Node t) {
                                        return t.setRightTree(new Node());
                                    }
                                }
                        )
                );
            }
        }

        if (root.data == null) {
            root = null;
        }
    }

  
    private static class NodeHelper {

        private Node node;
        private Function<Node, Node> action;

        public NodeHelper(
                Node node,
                Function<Node, Node> action
        ) {
            this.node = node;
            this.action = action;
        }

        public Node getNode() {
            return node;
        }

        public Function<Node, Node> getAction() {
            return action;
        }
    }

    private static class Node {

        private String data;
        private Node left;
        private Node right;

        public Node setLeftTree(Node left) {
            this.left = left;
            return this.left;
        }

        public Node setRightTree(Node right) {
            this.right = right;
            return this.right;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }


    public int cantInternalNodeFrom(boolean from) {
        return countInternalRec(root, from, null);
    }

    private int countInternalRec(Node current, boolean from, Boolean cameFrom) {
        if (current == null || current.isLeaf()) {
            return 0;
        }

        int count = 0;

        ///  EL CHEQUEO DE NULL ES SOLO PARA NO CONTAR EL ROOT
        if (cameFrom != null && cameFrom == from) {
            count++;
        }

        ///  IZQ ES VERDADERO Y DER ES FALSO
        return count + countInternalRec(current.left, from, true) + countInternalRec(current.right, from, false);
    }
    
  
    
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IOException {
    	// ejemplo de invocacion con path absoluto
    	BinaryTree b = new  BinaryTree("c://a.txt");
        System.out.println(b.cantInternalNodeFrom(false));
        System.out.println(b.cantInternalNodeFrom(true));
    }
}