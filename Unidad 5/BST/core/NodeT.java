package core;

public class NodeT<T extends Comparable<? super T>> implements NodeTreeInterface<T>{

    private T data;
    private NodeT<T> left = null;
    private NodeT<T> right = null;
    private int height;

    public NodeT(T data){
        this.data = data;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public NodeT<T> getLeft() {
        return left;
    }

    @Override
    public NodeT<T> getRight() {
        return right;
    }

    public void insert(T myData) {
        if(myData.compareTo(data)<=0) {
            if(left == null)
                left = new NodeT<>(myData);
            else
                left.insert(myData);
        }
        else {
            if(right == null)
                right = new NodeT<>(myData);
            else
                right.insert(myData);
        }
    }

    public NodeT<T> delete(T myData){
        int cmp = myData.compareTo(this.data);
        if (cmp < 0) {
            if (left != null) {
                left = left.delete(myData);
                return this;
            }
        }
        else if(cmp > 0) {
            if (right != null) {
                right = right.delete(myData);
                return this;
            }
        }


        if (left == null){
            return right;
        }

        if (right == null) {
            return left;
        }

        T replacement = lexiAdj(left);
        this.data = replacement;
        left = left.delete(myData);
        return this;
    }

    private T lexiAdj(NodeT candidate){
        NodeT auxi = candidate;

        while (auxi.right != null){
            auxi = auxi.right;
        }

        return (T) auxi.data;
    }

    public int getHeight(){
        if (left == null && right == null)
            return 0;
        else {
            int leftHeight = (left == null ? 0 : left.getHeight());
            int rightHeight = (right == null ? 0 : right.getHeight());
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }


}
