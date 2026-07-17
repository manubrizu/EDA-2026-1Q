import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedLinkedListWithHeader<T extends Comparable<? super T>> implements SortedListService<T> {
    private Node root;
    private final Header header;

    private final class Header {
        private Node first;
        private Node last;
    }

    public SortedLinkedListWithHeader() {
        this.header = new Header();
    }

    // insert resuelto todo en la clase SortedLinkedList, iterativo
    public boolean insert(T data) {

        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        Node prev= null;
        Node current = root;

        while (current!=null && current.data.compareTo(data) < 0) {
            // avanzo
            prev= current;
            current= current.next;
        }

        // repetido
        if (current!=null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed. %s repeated", data));
            return false;
        }

        Node aux= new Node(data, current);
        // es el lugar para colocarlo
        if (current == root) {
            // el primero es un caso especial: cambia root
            root= aux;
        }
        else {
            // nodo interno
            prev.next= aux;
        }

        return true;
    }


    // insert resuelto todo en la clase SortedLinkedList, recursivo
    // @Override
    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta = new boolean[1];
        root = insertRec(data, root, rta);
        return rta[0];
    }


    public Node insertRec(T data, Node current, boolean[] rta) {
        if (current == null || current.data.compareTo(data) > 0) {
            /// inserto
            rta[0] = true;
            return new Node(data, current);
        } else if (current.data.compareTo(data) < 0) {
            /// sigo
            current.next = insertRec(data, current.next, rta);
            return current;
        }

        ///  repetido
        rta[0] = false;
        return current;
    }

    // insert resuelto delegando al nodo
    public boolean insert3(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data cannot be null");
        }

        if(root == null){
            root = new Node(data);
            return true;
        }

        boolean[] rta = new boolean[1];
        root = root.insert(data, rta);
        return rta[0];
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }


    @Override
    public boolean remove(T data) {
        Node prev = null;
        Node current = header.first;

        while (current != null && current.data.compareTo(data) < 0) {
            prev = current;
            current = current.next;
        }
        if (current != null && current.data.compareTo(data) == 0) {
            if (current == header.first) {
                header.first = header.first.next;
            }
            else {
                prev.next = current.next;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        int rta= 0;

        Node current = root;

        while (current!=null ) {
            // avanzo
            rta++;
            current= current.next;
        }
        return rta;
    }


    @Override
    public void dump() {
        Node current = root;

        while (current != null) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }


    @Override
    public boolean equals(Object other) {
        if (other == null || !  (other instanceof SortedLinkedListWithHeader) )
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedListWithHeader<T> auxi = (SortedLinkedListWithHeader<T>) other;

        Node current = root;
        Node currentOther= auxi.root;
        while (current!=null && currentOther != null ) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current= current.next;
            currentOther= currentOther.next;
        }

        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = root;
        int pos= 0;

        while (current!=null ) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current= current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public T getMin() {
        if (root == null)
            return null;

        return root.data;
    }


    @Override
    public T getMax() {
        if (root == null)
            return null;

        Node current = root;

        while (current.next !=null ) {
            // avanzo
            current= current.next;
        }

        return current.data;
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedLinkedListIterator(){};
    }


    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data= data;
            this.next= next;
        }

        private Node(T data) {
            this.data = data;
        }

        private Node insert(T data, boolean[] rta){
            if(this.data.compareTo(data) < 0){
                if(this.next == null){
                    rta[0] = true;
                    this.next = new Node(data);
                    return this;
                }
                next = next.insert(data, rta);
                return this;
            }
            else if (this.data.compareTo(data) > 0){
                rta[0] = true;
                return new Node(data, this);
            }

            rta[0] = false;
            return this;
        }

    }

    private class SortedLinkedListIterator implements Iterator<T> {
        private Node current;

        public SortedLinkedListIterator() {
            current = root;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T rta= current.data;
            current= current.next;
            return rta;
        }
    }
}
