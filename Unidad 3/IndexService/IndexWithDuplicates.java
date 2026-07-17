import java.util.Arrays;

public class IndexWithDuplicates implements IndexService {
    private static final int CHUNK = 10;

    private int[] elements;
    private int size;

    @Override
    public void initialize(int[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException();
        }

        int[] copy = Arrays.copyOf(elements, elements.length);
        Arrays.sort(copy);

        int capacity = Math.max(CHUNK, ((copy.length + CHUNK - 1) / CHUNK) * CHUNK);
        this.elements = new int[capacity];

        System.arraycopy(copy, 0, this.elements, 0, copy.length);
        this.size = copy.length;
    }

    @Override
    public boolean search(int key) {
        if (size == 0) {
            return false;
        }

        int pos = getClosestPosition(key);
        return pos < size && elements[pos] == key;
    }

    @Override
    public void insert(int key) {
        if (elements == null) {
            elements = new int[CHUNK];
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
    public void delete(int key) {
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
    public int occurrences(int key) {
        if (size == 0 || elements == null) {
            return 0;
        }

        int left = getClosestPosition(key);

        if (left == size || elements[left] != key) {
            return 0;
        }

        int right = getClosestPosition(key + 1);

        return right - left;
    }

    @Override
    public int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded) {
        int leftIndex = getClosestPosition(leftKey);
        int rightIndex = getClosestPosition(rightKey);

        if (elements[leftIndex] == leftKey && !leftIncluded) {
            leftIndex += occurrences(leftKey);
        }

        if (elements[rightIndex] == rightKey && rightIncluded) {
            rightIndex += occurrences(rightKey);
        }

        if(leftIndex > rightIndex) {
            return new int[0];
        }

        return Arrays.copyOfRange(elements, leftIndex, rightIndex);
    }

    @Override
    public void sortedPrint() {
        System.out.println(elements);
    }

    @Override
    public int getMax() {
        return elements[size - 1];
    }

    @Override
    public int getMin() {
        return elements[0];
    }

    private int getClosestPosition(int key) {
        int left = 0;
        int right = size - 1;
        int toReturn = size;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (elements[mid] >= key) {
                toReturn = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return toReturn;
    }
}