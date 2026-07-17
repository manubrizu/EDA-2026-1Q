package mabrizuela;

import java.util.ArrayList;
public class Levenshtein {
	private int[][] matrix;
	private char[] horizontalWord;
	private char[] verticalWord;

	public Levenshtein(char[] horizontalWord, char[] verticalWord) {
		this.horizontalWord= horizontalWord;
		this.verticalWord= verticalWord;
		matrix = new int[ verticalWord.length+1][ horizontalWord.length + 1];
		for(int col = 1; col <= horizontalWord.length; col++)
	    {
	        matrix[0][col] = col;
	    }
		for(int row = 1; row <= verticalWord.length; row++)
	    {
	        matrix[row][0] = row;        
	    }
	}

	public int distance() {
		// no se ha calculado antes
		if (verticalWord != null) {
			// calcular
			for(int row=1; row<= verticalWord.length; row++)
			{
				for(int col=1; col<= horizontalWord.length; col++) {
					matrix[row][col] = Math.min(matrix[row-1][col-1] + (( verticalWord[row-1]== horizontalWord[col-1])?0:1) ,
	                					           Math.min( matrix[row-1][col]+1, matrix[row][col-1]+1 ));
				}                
			}    
			// no las necesito mas. Las destruyo
			verticalWord= null;
			horizontalWord= null;
		}
		return matrix [ matrix.length-1][ matrix[0].length-1] ;  
	}

    public ArrayList<Character> getOperations() {
        ArrayList<Character> seqOps = new ArrayList<>();
        distance();

        int row = matrix.length - 1;
        int col = matrix[0].length - 1;

        while (row > 0 || col > 0) {

            // borde superior: solo quedan deletes
            if (row == 0) {
                seqOps.add(0, 'D');
                col--;
            }
            // borde izquierdo: solo quedan inserts
            else if (col == 0) {
                seqOps.add(0, 'I');
                row--;
            }
            // skip
            else if (matrix[row][col] == matrix[row - 1][col - 1]) {
                seqOps.add(0, '-');
                row--;
                col--;
            }
            // en empate, priorizo insert antes que substitute/delete
            else if (matrix[row][col] == matrix[row - 1][col] + 1) {
                seqOps.add(0, 'I');
                row--;
            }
            // substitute
            else if (matrix[row][col] == matrix[row - 1][col - 1] + 1) {
                seqOps.add(0, 'S');
                row--;
                col--;
            }
            // delete
            else {
                seqOps.add(0, 'D');
                col--;
            }
        }

        return seqOps;
    }

	public static void main(String[] args) {
		String p1;
		String p2;
		Levenshtein l;
		p1= "exkusa";
		p2= "ex-amigo";
		l = new Levenshtein(p1.toCharArray(), p2.toCharArray());

        ///  LO QUE INTERPRETO YO ES QUE CADA OPERACION NO TIENE UN ORDEN EN ESPECIFICO, PERO DEVUELVE BIEN PERO NO EN EL ORDEN ESPECIFICO
		System.out.println(String.format("las operaciones a realizar para transformar \"%s\" en \"%s\" son:", p1, p2 ) );
		System.out.println( l.getOperations() );
	}
}
