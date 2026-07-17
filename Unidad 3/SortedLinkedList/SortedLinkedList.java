import java.security.PrivilegedActionException;
import java.util.Iterator;
import java.util.NoSuchElementException;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SortedLinkedList<T extends Comparable<? super T>> implements SortedListService<T>{

	private Node root;
	
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

		// repetido?
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
	
	
	// delete resuelto todo en la clase SortedLinkedList, iterativo
    // iterativo
    @Override
    public boolean remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        Node current = root;
        Node prev = null;
        while (current != null && current.data.compareTo(data) <= 0) {
            if (current.data.equals(data)) {
                // remove
                if (prev == null) {
                    root = current.next;
                } else {
                    prev.next = current.next;
                }
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }


    // recursivo
    public boolean remove2(T data) {
        boolean[] rta = new boolean[1];
        root = removeRec(data, root, rta);
        return rta[0];
    }


    private Node removeRec(T data, Node current, boolean[] rta) {
        if (current == null || current.data.compareTo(data) > 0) {
            return current;
        }
        if (current.data.compareTo(data) == 0) {
            // remove
            rta[0] = true;
            return current.next;
        }
        current.next = removeRec(data, current.next, rta);
        return current;
    }


    // delete resuelto delegando al nodo
    public boolean remove3(T data) {
        if (data == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return false;
        }
        if (root.data.equals(data)) {
            root = root.next;
            return true;
        }
        Node current = root;
        while (current.next != null && current.next.data.compareTo(data) <= 0) {
            if (current.next.data.equals(data)) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
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

		while (current!=null ) {
			// avanzo
			System.out.println(current.data);
			current= current.next;
		}
	}
	
	
	@Override
	public boolean equals(Object other) {
		if (other == null || !  (other instanceof SortedLinkedList) )
			return false;
		
		@SuppressWarnings("unchecked")
		SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;
		
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






    public static void main(String[] args) {
		SortedLinkedList<String> l = new SortedLinkedList<>();
	
		System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
		System.out.println(l.size() );
		System.out.println(l.getMin() );
		System.out.println(l.getMax() );
		System.out.println();
		
		System.out.println(l.insert("hola"));
		l.dump();
		System.out.println();
		
		System.out.println("lista " +  (l.isEmpty()? "":"NO") + " vacia");
		System.out.println();
		
		System.out.println(l.insert("tal"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("ah"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("veo"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("bio"));
		l.dump();
		System.out.println();
		
		System.out.println(l.insert("tito"));
		l.dump();
		System.out.println();


		System.out.println(l.insert("hola"));
		l.dump();
		System.out.println();
		
		
		System.out.println(l.insert("aca"));
		l.dump();
		System.out.println();
		
		System.out.println(l.size() );
		System.out.println(l.getMin() );
		System.out.println(l.getMax() );
	}


}
