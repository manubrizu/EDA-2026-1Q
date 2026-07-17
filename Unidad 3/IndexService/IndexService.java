public interface IndexService {
	
	// elements ser�n los valores del �ndice, los anteriores se descartan.
      	// lanza excepction si elements is null y deja los valores anteriores.
	void initialize(int [] elements);

	// busca una key en el �ndice, O(log2 N)
	boolean search(int key);
	
	// inserta el key en pos correcta. Crece autom�ticamente de a chunks
	void insert(int key);
	
 	// borra el key si lo hay, sino lo ignora. 
    	// decrece autom�ticamente de a chunks
	void delete(int key);
	
      	// devuelve la cantidad de apariciones de la clave especificada
	int occurrences(int key);
	
	
	
	// devuelve un nuevo arreglo ordenado con los elementos que pertenecen al intervalo dado por 
	// leftkey y rightkey.  Si el mismo es abierto/cerrado depende de las variables leftIncluded 
	// y rightIncluded. True indica que es cerrado. 
	// Si no hay matching devuelve arreglo de length 0
	 int[] range(int leftKey, int rightKey, boolean leftIncluded, boolean rightIncluded);


	// imprime el contenido del �ndice ordenado por su key.	
	void sortedPrint();


	// devuelve el m�ximo elemento del �ndice. Lanza RuntimeException si no hay elementos
	int getMax();

	// devuelve el m�nimo elemento del �ndice. Lanza RuntimeException si no hay elementos
	int getMin();
	
	
}
