package version2;

import java.lang.reflect.Array;
import java.util.Arrays;

public class IndexWithDuplicates<T extends Comparable<? super T>> implements IndexParametricService<T> {
    private static final int CHUNK = 10;

    private T[] elements;
    private int size;

    public IndexWithDuplicates(Class<T> integerClass) {
        this.elements = (T[]) Array.newInstance(integerClass, CHUNK);
    }

    @Override
    public void initialize(T[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }

        T[] copy = Arrays.copyOf(elements, elements.length);
        Arrays.sort(copy);

        int capacity = Math.max(CHUNK, ((copy.length + CHUNK - 1) / CHUNK) * CHUNK);
        this.elements = (T[]) new Comparable[capacity];

        System.arraycopy(copy, 0, this.elements, 0, copy.length);
        this.size = copy.length;
    }

    @Override
    public boolean search(T key) {
        if (size == 0) {
            return false;
        }

        int pos = getClosestPosition(key);
        return pos < size && elements[pos].compareTo(key) == 0;
    }

    @Override
    public void insert(T key) {
        if (elements == null) {
            elements = (T[]) new Comparable[CHUNK];
            size = 0;
        }

        int pos = getClosestPosition(key);

        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length + CHUNK);
        }

        for (int i = size; i > pos; i--) {
            elements[i] = elements[i - 1];
        }

        elements[pos] = key;
        size++;
    }

    @Override
    public void delete(T key) {
        if (size == 0 || elements == null) {
            return;
        }

        int pos = getClosestPosition(key);

        if (pos == size || elements[pos] != key) {
            return;
        }

        for (int i = pos; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        size--;

        if (elements.length - size >= CHUNK && elements.length > CHUNK) {
            int newCapacity = elements.length - CHUNK;
            if (newCapacity < CHUNK) {
                newCapacity = CHUNK;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public int occurrences(T key) {
        if (size == 0 || elements == null) {
            return 0;
        }

        int left = getClosestPosition(key);

        if (left == size || elements[left].compareTo(key) != 0) {
            return 0;
        }

        int count = 0;
        int i = left;

        while (i < size && elements[i].compareTo(key) == 0) {
            count++;
            i++;
        }

        return count;
    }

    @Override
    public T[] range(T leftKey, T rightKey, boolean leftIncluded, boolean rightIncluded) {
        int leftIndex = getClosestPosition(leftKey);
        int rightIndex = getClosestPosition(rightKey);

        if (elements[leftIndex] == leftKey && !leftIncluded) {
            leftIndex += occurrences(leftKey);
        }

        if (elements[rightIndex] == rightKey && rightIncluded) {
            rightIndex += occurrences(rightKey);
        }

        if(leftIndex > rightIndex) {
            return (T[]) new Comparable[0];
        }

        return Arrays.copyOfRange(elements, leftIndex, rightIndex);
    }

    @Override
    public void sortedPrint() {
        System.out.println(Arrays.toString(Arrays.copyOf(elements, size)));
    }

    @Override
    public T getMax() {
        return elements[size - 1];
    }

    @Override
    public T getMin() {
        return elements[0];
    }


    private int getClosestPosition(T key) {
        int left = 0;
        int right = size - 1;
        int toReturn = size;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (elements[mid].compareTo(key) >= 0) {
                toReturn = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return toReturn;
    }
}