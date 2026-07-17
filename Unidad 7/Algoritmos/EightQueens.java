public class EightQueens {
	private boolean [][] tablero;
	
	public static void main(String[] args) {
		new EightQueens(4).solve();
	}
	
	public EightQueens(int dim) {
		tablero= new boolean[dim][dim];
	}
	
	public void solve() {
		solveHelper(0);
	}
	
	private void solveHelper(int col) {
		if (col == tablero.length) {
			print();
			return;
		}
		
		for(int row= 0; row < tablero.length; row++) {
			
			if ( check(row, col) )    { 

				tablero[row][col] = true; // resolver un caso pendiente

				solveHelper(col+1);		// explorar nuevos pendientes si satisface restricciones
			
				tablero[row][col]= false;  // quitar el pendiente generado
			}
		}
	}
			
	private boolean check(int rowCandidate, int colCandidate) {
		// row is empty hasta col?
		for(int c= 0; c< colCandidate; c++)
			if (tablero[rowCandidate][ c])
				return false;
		
		// diagonal empty hasta col?
		for(int c=colCandidate, r= rowCandidate; c>=0 && r<tablero.length ;  c--, r++)
			if (tablero[r][ c])
				return false;
		
		
		// contradiagonal empty hasta col?
		for(int c=colCandidate, r= rowCandidate; c>=0 && r>=0 ;  c--, r--)
			if (tablero[r][ c])
				return false;

		return true;
	}
	
	private void print() {
		for(int row= 0; row < tablero.length; row++) {
			for(int col= 0; col < tablero.length; col++)
			{
				if (tablero[row][col])
					System.out.print("Q");
				else
					System.out.print("-");
			}
			System.out.println();
		}
		
		System.out.println("\n");

	}
}
