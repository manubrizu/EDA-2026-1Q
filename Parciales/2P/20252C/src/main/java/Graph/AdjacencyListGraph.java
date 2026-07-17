package Graph;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E> {

    private boolean isSimple;
    protected boolean isDirected;
    private boolean acceptSelfLoop;
    private boolean isWeighted;
    protected String type;

    // HashMap no respeta el orden de insercion. En el testing considerar eso
    private Map<V,Collection<InternalEdge>> adjacencyList= new HashMap<>();

    // respeta el orden de llegada y facilita el testing
    //	private Map<V,Collection<InternalEdge>> adjacencyList= new LinkedHashMap<>();

    protected   Map<V,  Collection<InternalEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    protected AdjacencyListGraph(boolean isSimple, boolean isDirected, boolean acceptSelfLoop, boolean isWeighted) {
        this.isSimple = isSimple;
        this.isDirected = isDirected;
        this.acceptSelfLoop= acceptSelfLoop;
        this.isWeighted = isWeighted;

        this.type = String.format("%s %sWeighted %sGraph with %sSelfLoop",
                isSimple ? "Simple" : "Multi", isWeighted ? "" : "Non-",
                isDirected ? "Di" : "", acceptSelfLoop? "":"No ");
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertex parameters cannot be null");

        // no edges yet
        getAdjacencyList().putIfAbsent(aVertex, new ArrayList<InternalEdge>());
    }

    @Override
    public Collection<V> getVertices() {
        return getAdjacencyList().keySet() ;
    }

    @Override
    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        // validation!!!!
        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdge parameters cannot be null");

        // es con peso? debe tener implementado el metodo double getWeight()
        if (isWeighted) {
            // reflection
            Class<? extends Object> c = theEdge.getClass();
            try {
                c.getDeclaredMethod("getWeight");
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(
                        type + " is weighted but the method double getWeighed() is not declared in theEdge");
            }
        }

        if (! acceptSelfLoop && aVertex.equals(otherVertex)) {
            throw new RuntimeException(String.format("%s does not accept self loops between %s and %s" ,
                    type, aVertex, otherVertex) );
        }

        // if any of the vertex is not presented, the node is created automatically
        addVertex(aVertex);
        addVertex(otherVertex);
    }


    @Override
    public boolean hasEdge(V fromVertex, V toVertex, E edge) {
        if (fromVertex == null || toVertex == null || edge == null) {
            throw new RuntimeException("hasEdges called with at least one null parameter");
        }

        Collection<InternalEdge> edges = adjacencyList.get(fromVertex);
        if (edges == null) {
            return false;
        }

        for (InternalEdge e : edges) {
            if (e.target.equals(toVertex) && e.edge.equals(edge)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public GraphService<V, E> fansSubgraph(Integer popularThreshold) {
        // TODO: Implementar
        return null;
    }

    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            @SuppressWarnings("unchecked")
            InternalEdge aux = (InternalEdge) obj;

            return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
                    && target.equals(aux.target);
        }

        @Override
        public String toString() {
            return String.format("-[%s]-(%s)", edge, target);
        }
    }



    public static void main(String[] args) {
        GraphService<Vertex, WeightedEdge> g = new GraphBuilder<Vertex, WeightedEdge>()
                .withAcceptSelfLoop(SelfLoop.NO)
                .withAcceptWeight(Weight.YES)
                .withDirected(EdgeMode.DIRECTED)
                .withMultiplicity(Multiplicity.SIMPLE)
                .build();
        Artist a1 = new Artist("Ping Floyd");
        Artist a2 = new Artist("Queen");
        Song s1 = new Song("Run like hell");
        Song s2 = new Song("Money");
        Song s3 = new Song("Radio Ga Ga");
        Song s4 = new Song("Under Pressure");
        Fan f1 = new Fan("Mer");
        Fan f2 = new Fan("Ale");
        Fan f3 = new Fan("Juan");

        g.addEdge(f3,s1, new WeightedEdge(4));
        g.addEdge(f3,s2, new WeightedEdge(7));
        g.addEdge(f2,s2, new WeightedEdge(6));
        g.addEdge(f2,s3, new WeightedEdge(1));
        g.addEdge(f2,s4, new WeightedEdge(1));
        g.addEdge(f1,s1, new WeightedEdge(2));
        g.addEdge(f1,s2, new WeightedEdge(1));
        g.addEdge(f1,s3, new WeightedEdge(9));
        g.addEdge(f1,s4, new WeightedEdge(8));
        g.addEdge(s1,a1, new WeightedEdge(6));
        g.addEdge(s2,a1, new WeightedEdge(14));
        g.addEdge(s3,a2, new WeightedEdge(10));
        g.addEdge(s4,a2, new WeightedEdge(9));

        GraphService<Vertex, WeightedEdge> g1 = g.fansSubgraph(2);

        System.out.println();

        GraphService<Vertex, WeightedEdge> g2 = g.fansSubgraph(5);

        System.out.println();
    }

}