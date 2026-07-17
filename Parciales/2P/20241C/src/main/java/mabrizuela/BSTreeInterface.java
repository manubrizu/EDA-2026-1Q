package mabrizuela;


import java.util.HashMap;

public interface BSTreeInterface<T extends Comparable<? super T>> {

	void insert(T myData);

	// implementar
	 HashMap<T, Integer> inRange(T lower, T upper);
}