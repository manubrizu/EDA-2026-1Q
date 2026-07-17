import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class TreeSum {

    private Node root;

    private Scanner inputScanner;

    private int maxChildren;

    public TreeSum(int numNodes, String treeStr)  {
        maxChildren = numNodes;

        inputScanner = new Scanner(treeStr);
        inputScanner.useDelimiter("\\s+");

        buildTree();

        inputScanner.close();
    }

    private void buildTree() {
        Queue<NodeHelper> pendingOps = new LinkedList<>();

        pendingOps.add(new NodeHelper(null, -1)); // lugar de la raíz

        while (inputScanner.hasNext()) {
            String token = inputScanner.next();

            NodeHelper pending = pendingOps.remove();

            if (token.equals("?")) {
                continue;
            }

            Node newNode = new Node();
            newNode.data = Integer.parseInt(token);

            if (pending.parent == null) {
                root = newNode;
            } else {
                pending.parent.children[pending.childIndex] = newNode;
            }

            for (int i = 0; i < maxChildren; i++) {
                pendingOps.add(new NodeHelper(newNode, i));
            }
        }
    }

    public void printHierarchy() {
        printHierarchy("", root);
    }

    public void printHierarchy(String initial, Node current) {
        if (current == null) {
            return;
        }

        System.out.println(initial + "|_______ " + current.data);

        for (Node child : current.children) {
            printHierarchy(initial + "          ", child);
        }
    }

    class Node {
        private Integer data;
        private Node[] children;

        public Node() {
            children = new Node[maxChildren];
        }

        private boolean isLeaf() {
            for (Node child : children) {
                if (child != null) {
                    return false;
                }
            }
            return true;
        }
    }

    static class NodeHelper {
        private TreeSum.Node parent;
        private int childIndex;

        public NodeHelper(TreeSum.Node parent, int childIndex) {
            this.parent = parent;
            this.childIndex = childIndex;
        }
    }

    LinkedList<String> sumLessPaths(int sum) {
        LinkedList<String> result = new LinkedList<>();

        if (root == null) {
            return result;
        }

        LinkedList<Integer> currentPath = new LinkedList<>();
        sumLessPaths(root, sum, 0, currentPath, result);

        return result;
    }

    private void sumLessPaths(Node current, int maxSum, int currentSum,
                              LinkedList<Integer> currentPath,
                              LinkedList<String> result) {
        if (current == null) {
            return;
        }

        currentPath.add(current.data);
        currentSum += current.data;

        if (current.isLeaf()) {
            if (currentSum < maxSum) {
                result.add(pathToString(currentPath));
            }
        } else {
            for (Node child : current.children) {
                sumLessPaths(child, maxSum, currentSum, currentPath, result);
            }
        }

        currentPath.removeLast();
    }

    private String pathToString(LinkedList<Integer> path) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(path.get(i));
        }

        return sb.toString();
    }

    public static void main1(String[] args){
        TreeSum rta = new TreeSum(3,"3 1 2 3 4 8 ? ? ? ? 3 1 30 1");
        rta.printHierarchy();
        LinkedList<String> res = rta.sumLessPaths(10);
        for ( String s: res )
            System.out.println(s);
    }

    public static void main2(String[] args){
        TreeSum rta = new TreeSum(2,"3 1 3 4 8 3 1 ? ? 2 ? 1 ? 0");
        rta.printHierarchy();
        LinkedList<String> res = rta.sumLessPaths(8);
        for ( String s: res )
            System.out.println(s);
    }

    public static void main3(String[] args){
        TreeSum rta = new TreeSum(4,"2 1 2 3 3 4 6 ? ? 3 1 30 ? 3 ? 1 ? ? ? 4");
        rta.printHierarchy();
        LinkedList<String> res = rta.sumLessPaths(8);
        for ( String s: res )
            System.out.println(s);
    }

    public static void main4(String[] args){
        TreeSum rta = new TreeSum(5,"1");
        rta.printHierarchy();
        LinkedList<String> res = rta.sumLessPaths(3);
        for ( String s: res )
            System.out.println(s);
    }

    public static void main5(String[] args){
        TreeSum rta = new TreeSum(2,"1 2 3 4 ? 5 6 ? ? ? ? 7 8");
        rta.printHierarchy();
        LinkedList<String> res = rta.sumLessPaths(100);
        for ( String s: res )
            System.out.println(s);
    }

    public static void main(String[] args){
        System.out.println("Ejemplo 1");
        main1(args);
        System.out.println("Ejemplo 2");
        main2(args);
        System.out.println("Ejemplo 3");
        main3(args);
        System.out.println("Ejemplo 4");
        main4(args);
        System.out.println("Ejemplo 5");
        main5(args);
    }


}