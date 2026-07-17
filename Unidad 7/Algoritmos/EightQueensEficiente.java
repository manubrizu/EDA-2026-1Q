public class EightQueensEficiente {
	private Integer [] tablero;
	private boolean diag[];
	private boolean contradiag[];
	
	public static void main(String[] args) {
		 new EightQueensEficiente(4).solve();
	}
	
	public EightQueensEficiente(int dim) {
		tablero= new Integer[dim];
		diag= new boolean[ 2 * dim - 1];
		contradiag= new boolean[ 2 * dim - 1];
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
			if (check(row, col))
			{
				tablero[row] = col; // resolver un caso pendiente
				diag[row + col]= true;
				contradiag[row - col + tablero.length - 1]= true;
				
				solveHelper(col+1);	// explorar nuevos pendientes si satisface restricciones
				
				tablero[row]= null;  // quitar el pendiente generado
				diag[row + col]= false;
				contradiag[row - col + tablero.length - 1]= false;
			}
		}
	}
			
	private boolean check(int rowCandidate, int colCandidate) {
		// row is empty 
		if (tablero[rowCandidate] != null)
				return false;
		
		// diagonal empty 
		if (diag[rowCandidate + colCandidate])
				return false;
		
		
		// contradiagonal empty 
		if (contradiag[rowCandidate - colCandidate + tablero.length - 1])
			return false;
		
		return true;
	}
	
	private void print() {
		for(int row= 0; row < tablero.length; row++) {
			for(int col= 0; col < tablero.length; col++)
			{
				if (tablero[row] == col)
					System.out.print("Q");
				else
					System.out.print("-");
			}
			System.out.println();
		}
		
		System.out.println("\n");

	}
}
