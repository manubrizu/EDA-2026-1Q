public class BoundedQueue<T> {
    private T[]  elements;
    private int first;
    private int last;
    private int size= 0;

    public BoundedQueue(int limit) {
        elements = (T[]) new Object[limit];
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean isFull() {
        return size==elements.length;
    }

    public void enqueue(T element) {
        if(isFull()) {
            System.out.println("Queue is full");
        }
        elements[last++] = element;
        last %= elements.length;
        size++;
    }

    public T dequeue()  {
        if(isEmpty()) {
            System.out.println("Queue is empty");
        }
        T toReturn =  elements[first];
        elements[first++] = null;
        first %= elements.length;
        size--;
        return toReturn;
    }

    private void dump() {
        for(int i=first; i<last; i++) {
            System.out.print(elements[i] + " ");
            elements[i] = null;
        }
    }

    public static void main(String[] args) {
        BoundedQueue<Integer> myQueue = new BoundedQueue<>(10);

        myQueue.enqueue(10);
        myQueue.enqueue(20);
        myQueue.enqueue(30);
        myQueue.enqueue(40);

        System.out.println(myQueue.dequeue() );
        System.out.println(myQueue.dequeue() );

        myQueue.enqueue(50);
        myQueue.enqueue(60);
        myQueue.enqueue(70);

        System.out.println("\nquedaron 5 elementos");
        myQueue.dump();

    }
}



