package mabrizuela;

public interface BoundedQueueInterface<T> {

    boolean isEmpty();

    boolean isFull();

    void enqueue(T element);

    T dequeue();

}