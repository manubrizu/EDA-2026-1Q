package mabrizuela;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MultiDirectedGraph<V, E> {

    private Map<V, Collection<InternalEdge>> adjacencyList= new HashMap<>();

    public Collection<V> getVertices() {
        return adjacencyList.keySet() ;
    }

    protected MultiDirectedGraph(){
    }

    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        adjacencyList.putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        addVertex(aVertex);
        addVertex(otherVertex);

        adjacencyList.get(aVertex).add( new InternalEdge(theEdge, otherVertex) );
    }

    void printAllVertexes(){
        for( V v : getVertices() )
            System.out.println( v );
    }

    void printAllEdges(){
        for( Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet() )
            for( InternalEdge i : entry.getValue() )
                System.out.println( entry.getKey().toString() + " -> "  + i.target.toString() + "[" + i.edge.toString() + "]" );
    }


    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }

    MultiDirectedGraph<V,E> transposeSummarize(BiFunction<E, E, E> summarizer) {
        if (summarizer == null)
            throw new IllegalArgumentException("summarizer cannot be null");

        MultiDirectedGraph<V, E> ans = new MultiDirectedGraph<>();

        // Primero agrego todos los vértices, incluso los que no tengan ejes salientes
        for (V vertex : adjacencyList.keySet()) {
            ans.addVertex(vertex);
        }

        // Recorro todos los ejes del grafo original
        for (Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
            V source = entry.getKey();

            for (InternalEdge internalEdge : entry.getValue()) {
                V target = internalEdge.target;
                E edge = internalEdge.edge;

                // En el transpuesto se invierte:
                // source -> target pasa a ser target -> source
                ans.addOrSummarizeEdge(target, source, edge, summarizer);
            }
        }

        return ans;
    }

    private void addOrSummarizeEdge(V source, V target, E edge, BiFunction<E, E, E> summarizer) {
        Collection<InternalEdge> edges = adjacencyList.get(source);

        // Busco si ya existe un eje source -> target
        for (InternalEdge internalEdge : edges) {
            if (internalEdge.target.equals(target)) {
                internalEdge.edge = summarizer.apply(internalEdge.edge, edge);
                return;
            }
        }

        // Si no existía, lo agrego normalmente
        addEdge(source, target, edge);
    }

    // ejemplo 1
    public static void main1(String[] args) {
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("A", "B", 1);
        myGraph.addEdge("B", "C", 2);
        myGraph.addEdge("B", "D", 3);
        myGraph.addEdge("D", "C", 4);
        myGraph.addEdge("D", "E", 5);
        myGraph.addEdge("A", "E", 6);
        myGraph.addEdge("E", "G", 7);
        myGraph.addEdge("G", "F", 8);
        myGraph.addEdge("F", "E", 9);
        myGraph.addEdge("A", "B", 10);
        myGraph.addEdge("B", "C", 11);
        myGraph.addEdge("B", "C", 12);
        myGraph.addEdge("E", "G", 13);

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }


    // ejemplo 2
    public static void main2(String[] args) {
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("B", "C", 11);
        myGraph.addEdge("B", "C", 12);
        myGraph.addEdge("C", "B", 2);
        myGraph.addEdge("B", "D", 3);
        myGraph.addEdge("D", "C", 4);
        myGraph.addEdge("E", "G", 7);
        myGraph.addEdge("G", "F", 8);
        myGraph.addEdge("F", "G", -3);
        myGraph.addEdge("F", "E", 9);
        myGraph.addEdge("E", "G", 13);

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }

    public static void main(String[] args) {
        System.out.println("===== EJEMPLO 1 =====");
        main1(args);

        System.out.println();

        System.out.println("===== EJEMPLO 2 =====");
        main2(args);
    }
}

