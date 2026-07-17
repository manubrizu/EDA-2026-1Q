import java.util.*;

public class Greedy {
	
	static class Arista implements Comparable<Arista> {
		int nodo1, nodo2, peso;
		
		Arista(int nodo1, int nodo2, int peso) {
			this.nodo1 = nodo1;
			this.nodo2 = nodo2;
			this.peso = peso;
		}
		
		@Override
		public int compareTo(Arista otra) {
			return Integer.compare(this.peso, otra.peso);
		}
		
		@Override
		public String toString() {
			return "[" + nodo1 + "-" + nodo2 + ":" + peso + "]";
		}
	}
	
	static class UnionFind {
		int[] parent;
		int[] rank;
		
		UnionFind(int n) {
			parent = new int[n];
			rank = new int[n];
			for (int i = 0; i < n; i++) {
				parent[i] = i;
				rank[i] = 0;
			}
		}
		
		int find(int x) {
			if (parent[x] != x) {
				parent[x] = find(parent[x]); // compresión de camino
			}
			return parent[x];
		}
		
		boolean union(int x, int y) {
			int rootX = find(x);
			int rootY = find(y);
			
			if (rootX == rootY) {
				return false; // ya están en el mismo conjunto
			}
			
			// unir por rango
			if (rank[rootX] < rank[rootY]) {
				parent[rootX] = rootY;
			} else if (rank[rootX] > rank[rootY]) {
				parent[rootY] = rootX;
			} else {
				parent[rootY] = rootX;
				rank[rootX]++;
			}
			return true;
		}
	}
	
	public static void main(String[] args) {
		// Ejemplo del algoritmo de Kruskal
		List<Arista> aristas = new ArrayList<>();
		aristas.add(new Arista(0, 1, 9));   // A-E
		aristas.add(new Arista(0, 4, 2));   // A-B
		aristas.add(new Arista(1, 2, 2));   // F-C
		aristas.add(new Arista(1, 5, 2));   // F-G
		aristas.add(new Arista(1, 3, 1));   // F-D
		aristas.add(new Arista(2, 3, 6));   // C-D
		aristas.add(new Arista(2, 5, 4));   // C-G
		aristas.add(new Arista(0, 2, 9));   // A-C
		aristas.add(new Arista(4, 2, 2));   // B-C
		
		List<Arista> mst = kruskal(aristas, 6);
		System.out.println("Árbol generador mínimo: " + mst);
		
		int pesoTotal = 0;
		for (Arista a : mst) {
			pesoTotal += a.peso;
		}
		System.out.println("Peso total: " + pesoTotal);
	}
	
	// Algoritmo de Kruskal: encuentra el árbol generador mínimo
	public static List<Arista> kruskal(List<Arista> aristas, int cantNodos) {
		List<Arista> mst = new ArrayList<>();
		
		// Paso 1: Ordenar aristas por peso (estrategia voraz)
		Collections.sort(aristas);
		
		// Paso 2: Usar Union-Find para detectar ciclos
		UnionFind uf = new UnionFind(cantNodos);
		
		// Paso 3: Agregar aristas al MST si no crean ciclo
		for (Arista arista : aristas) {
			if (uf.union(arista.nodo1, arista.nodo2)) {
				mst.add(arista);
				if (mst.size() == cantNodos - 1) {
					break; // MST completo
				}
			}
		}
		
		return mst;
	}
}
