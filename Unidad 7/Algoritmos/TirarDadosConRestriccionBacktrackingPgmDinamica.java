import java.util.ArrayList;


public class TirarDadosConRestriccionBacktrackingPgmDinamica {
	public static void main(String[] args) {
		new TirarDadosConRestriccionBacktrackingPgmDinamica().solve(3);
	}
	
	public void solve(int cantDadosPendientes) {
		ArrayList<Integer> auxi= new ArrayList<>();
		solveHelper( cantDadosPendientes, auxi, 6, 0);
	}

	private void solveHelper(int cantDadosPendientes, ArrayList<Integer> auxi, int umbral, int sumaAcumulada) {
		
		if (sumaAcumulada + cantDadosPendientes * 1 > umbral)
			return;
		
		if (cantDadosPendientes == 0) {
			System.out.println(auxi);
			return;
		}


		for(int rec= 1; rec <= 6; rec++) {
			auxi.add(rec);					// resolver un caso pendiente

			solveHelper(cantDadosPendientes-1, auxi, umbral, sumaAcumulada + rec);  // explorar nuevos pendientes
			
			auxi.remove( auxi.size() - 1);  // quitar el pendiente generado
		}		
	}
}
