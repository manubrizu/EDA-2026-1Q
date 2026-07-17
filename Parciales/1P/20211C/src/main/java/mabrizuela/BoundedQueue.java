package mabrizuela;

public class BoundedQueue<T> {
    private final T[] queue;
    private int size;
    private int first;
    private int last;
    private final int max_queue;
    @SuppressWarnings("unchecked")
    public BoundedQueue(int max_queue) {
        this.queue = (T[]) new Object[max_queue];
        this.max_queue = max_queue;
    }
    public int getSize() {
        return size;
    }
    public boolean isEmpty() {
        return getSize() == 0;
    }
    T peek() {
        return queue[first];
    }
    public void queue(T value) {
        if (size > max_queue)
            throw new RuntimeException();
        if (last == max_queue - 1) {
            queue[last] = value;
            last = 0;
        }
        else
            queue[last++] = value;
        size++;
    }
    public T dequeue() {
        T result;
        if (first == max_queue - 1) {
            result = queue[first];
            first = 0;
        } else result = queue[first++];
        size--;
        return result;
    }
    public void printQueue() {
        int i = first;
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while (count != max_queue) {
            if (i == max_queue) {
                i = 0;
                sb.append(queue[i]).append(' ');
            } else {
                sb.append(queue[i]).append(' ');
                i++;
            }
            count++;
        }
        System.out.println(sb);
    }
    void dump() {
        size = first = last = 0;
    }
}