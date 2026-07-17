package mabrizuela;

import java.util.ArrayList;


public class BST<T extends Comparable<? super T>> {

        private Node root;

        public void insert(T myData){
            if ( root == null ) {
                root = new Node( myData );
                return;
            }
            root.insert( myData );
        }

        private class Node {
            private T data;
            private Node left;
            private Node right;

            Node( T d ){ data = d; }

            public void insert(T myData){
                if ( data.compareTo(myData) >= 0 ){
                    if ( left == null )
                        left = new Node( myData );
                    else
                        left.insert( myData );
                } else {
                    if ( right == null )
                        right = new Node( myData );
                    else
                        right.insert( myData );
                }
            }
        }


    int getDiameter() {
        return getDiameter(root)[1];
    }

    /*
     * Devuelve:
     * pos 0 -> altura del subárbol
     * pos 1 -> diámetro del subárbol
     */
    private int[] getDiameter(Node current) {
        if (current == null) {
            return new int[]{-1, 0};
        }

        int[] left = getDiameter(current.left);
        int[] right = getDiameter(current.right);

        int leftHeight = left[0];
        int leftDiameter = left[1];

        int rightDiameter = right[1];
        int rightHeight = right[0];

        int currentHeight = Math.max(leftHeight, rightHeight) + 1;

        // Camino más largo que pasa por current
        int diameterThroughCurrent = leftHeight + rightHeight + 2;

        int currentDiameter = Math.max(diameterThroughCurrent, Math.max(leftDiameter, rightDiameter));

        return new int[]{currentHeight, currentDiameter};
    }

    ArrayList<T> getDiameterPath() {
        ArrayList<T> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Node common = getDiameterCommonNode(root);

        ArrayList<T> leftPath = getDeepestPath(common.left);
        ArrayList<T> rightPath = getDeepestPath(common.right);

        // El lado izquierdo tiene que ir desde la hoja hasta el nodo común
        for (int i = leftPath.size() - 1; i >= 0; i--) {
            result.add(leftPath.get(i));
        }

        // Nodo común
        result.add(common.data);

        // El lado derecho va desde el nodo común hacia la hoja
        result.addAll(rightPath);

        return result;
    }

    /*
     * Busca el nodo por donde pasa el diámetro.
     * Puede ser la raíz o algún nodo interno.
     */
    private Node getDiameterCommonNode(Node current) {
        if (current == null) {
            return null;
        }

        int leftHeight = getHeight(current.left);
        int rightHeight = getHeight(current.right);

        int diameterThroughCurrent = leftHeight + rightHeight + 2;

        int leftDiameter = getDiameter(current.left)[1];
        int rightDiameter = getDiameter(current.right)[1];

        if (diameterThroughCurrent >= leftDiameter && diameterThroughCurrent >= rightDiameter) {
            return current;
        }

        if (leftDiameter >= rightDiameter) {
            return getDiameterCommonNode(current.left);
        }

        return getDiameterCommonNode(current.right);
    }

    private int getHeight(Node current) {
        if (current == null) {
            return -1;
        }

        return Math.max(getHeight(current.left), getHeight(current.right)) + 1;
    }

    /*
     * Devuelve el camino desde current hasta una hoja lo más profunda posible.
     */
    private ArrayList<T> getDeepestPath(Node current) {
        ArrayList<T> path = new ArrayList<>();

        if (current == null) {
            return path;
        }

        path.add(current.data);

        int leftHeight = getHeight(current.left);
        int rightHeight = getHeight(current.right);

        if (leftHeight >= rightHeight) {
            path.addAll(getDeepestPath(current.left));
        } else {
            path.addAll(getDeepestPath(current.right));
        }

        return path;
    }

    public static void test1(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(70);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void test2(){
        BST<Integer> myTree = new BST<>();
        myTree.insert(50);
        myTree.insert(60);
        myTree.insert(80);
        myTree.insert(20);
        myTree.insert(40);
        myTree.insert(44);
        myTree.insert(10);
        myTree.insert(5);
        myTree.insert(8);
        myTree.insert(7);
        myTree.insert(42);
        myTree.insert(43);

        System.out.println( myTree.getDiameter() );

        ArrayList<Integer> path = myTree.getDiameterPath();
        System.out.println( "Path" );
        for( int i : path )
            System.out.println( i );
    }

    public static void main(String[] args) {
        System.out.println( "Test1" );
        test1();
        System.out.println( "Test2" );
        test2();
    }
}
