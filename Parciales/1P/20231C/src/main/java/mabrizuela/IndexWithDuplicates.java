package mabrizuela;

import java.util.Arrays;

public class IndexWithDuplicates<E extends Comparable<E> > {

    private final int chunk_size = 5;
    private E [] m_idx;
    private int m_size;
    @SuppressWarnings("unchecked")
    public IndexWithDuplicates(){
        m_idx = (E[]) new Comparable [chunk_size];
    }

    public void initialize(E[] elements) {
        if (elements == null) {
            throw new IllegalArgumentException("elements cannot be null");
        }
        for ( E e : elements )
            insert(e);
    }

    private void grow(){
        if (m_size < m_idx.length)
            return;
        m_idx = Arrays.copyOf(m_idx, m_idx.length + chunk_size );
    }

    public void insert(E key) {
        grow();

        int position = 0;
        for ( position = 0; position < m_size && m_idx[position].compareTo( key ) < 0; ++position);

        for (int i = m_size; i > position; --i)
            m_idx[i] = m_idx[i - 1];
        m_idx[position] = key;
        ++m_size;
    }

    void repeatedValues( E[] values, SimpleLinkedList<E> repeatedLst, SimpleLinkedList<E> singleLst, SimpleLinkedList<E> notIndexedLst ) {
        for (int i = 0; i < values.length; i++) {
            if(occurrences(values[i]) == 0){
                notIndexedLst.insert(values[i]);
            }
            else if (occurrences(values[i]) > 1){
                repeatedLst.insert(values[i]);
            }
            else {
                singleLst.insert(values[i]);
            }
        }
    }

    private int getClosestPosition(E key) {
        int left = 0;
        int right = m_size - 1;
        int toReturn = m_size;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (m_idx[mid].compareTo(key) >= 0) {
                toReturn = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return toReturn;
    }

    private int occurrences(E key) {
        if (m_size == 0 || m_idx == null) {
            return 0;
        }

        int left = getClosestPosition(key);

        if (left == m_size || m_idx[left].compareTo(key) != 0) {
            return 0;
        }

        int count = 0;
        int i = left;

        while (i < m_size && m_idx[i].compareTo(key) == 0) {
            count++;
            i++;
        }

        return count;
    }


    public static void main(String[] args) {
        IndexWithDuplicates<Integer> idx = new IndexWithDuplicates<>();
        idx.initialize(  new Integer[] {100, 50, 30, 50, 80, 10, 100, 30, 20, 138} );

        SimpleLinkedList<Integer> repeatedLst = new SimpleLinkedList();
        SimpleLinkedList<Integer> singleLst  = new SimpleLinkedList();
        SimpleLinkedList<Integer> notIndexedLst  = new SimpleLinkedList();
        idx.repeatedValues( new Integer[] { 100, 70, 40, 120, 33, 80, 10, 50 }, repeatedLst, singleLst, notIndexedLst );

        System.out.println("Repeated Values");
        repeatedLst.dump();

        System.out.println("Single Values");
        singleLst.dump();

        System.out.println("Non Indexed Values");
        notIndexedLst.dump();
    }


}
