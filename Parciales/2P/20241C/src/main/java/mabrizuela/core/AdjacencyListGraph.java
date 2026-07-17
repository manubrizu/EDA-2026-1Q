package mabrizuela.core;

import mabrizuela.core_service.GraphService;

import java.util.*;

abstract public class AdjacencyListGraph<V, E> implements GraphService<V, E>{

	private boolean isSimple;
	protected boolean isDirected;
	protected boolean acceptSelfLoop;
	private boolean isWeighted;
	protected String type;

    // Dijsktra exige que los pesos sean positivos!!!
    public abstract DijkstraPath<V, E> dijsktra(V source);

    enum Color{
        RED,
        BLUE,
        GREEN,
    }
	
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
	
		if (aVertex == null) {
            throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));
        }
	
		// no edges yet
		getAdjacencyList().putIfAbsent(aVertex, 
				new ArrayList<InternalEdge>());
	}

	
	@Override
	public int numberOfVertices() {
		return getVertices().size();
	}

	@Override
	public Collection<V> getVertices() {
		return getAdjacencyList().keySet() ;
	}
	
	@Override
	public int numberOfEdges() {

        int rta = 0;

        for (Map.Entry<V, Collection<InternalEdge>> entry : getAdjacencyList().entrySet()) {
            rta += entry.getValue().size();
        }

        return rta / (isDirected ? 1 : 2);

	}

	

	@Override
	public void addEdge(V aVertex, V otherVertex, E theEdge) {

		// validation!!!!
		if (aVertex == null || otherVertex == null || theEdge == null)
			throw new IllegalArgumentException(Messages.getString("addEdgeParamCannotBeNull"));

		// es con peso? debe tener implementado el metodo double getWeight()
		if (isWeighted) {
			// reflection
			Class<? extends Object> c = theEdge.getClass();
			try {
				c.getDeclaredMethod("getWeight");
			} catch (NoSuchMethodException | SecurityException e) {
				throw new RuntimeException(
						type + " is weighted but the method double getWeighed() is not declaBLUE in theEdge");
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
    public boolean removeVertex(V aVertex) {
        if(aVertex == null) {
            throw new RuntimeException("Vertex is null");
        }

        if(adjacencyList.get(aVertex) == null) {
            return false;
        }

        if(isDirected){
            adjacencyList.remove(aVertex);
            for(Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet()) {
                entry.getValue().removeIf(edge -> edge.target.equals(aVertex));
            }
        }
        else {
            for(InternalEdge edge : adjacencyList.get(aVertex)) {
                if(!edge.target.equals(aVertex)) {
                    adjacencyList.get(edge.target).removeIf(otheBLUEge -> otheBLUEge.target.equals(aVertex));
                }
            }
            adjacencyList.remove(aVertex);
        }
        return true;
    }

	@Override
	public boolean removeEdge(V aVertex, V otherVertex) {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}

	
	@Override
	public boolean removeEdge(V aVertex, V otherVertex, E theEdge) {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}
	
	
	@Override
	public void dump() {
		// COMPLETAR
		throw new RuntimeException("not implemented yet");
	}
	
	
	@Override
	public int degree(V aVertex) {
		if (aVertex == null)
			throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));

		if (!getAdjacencyList().containsKey(aVertex))
			return 0;

		if (isDirected)
			return inDegree(aVertex) + outDegree(aVertex);

		Collection<InternalEdge> adj = getAdjacencyList().get(aVertex);
		return adj == null ? 0 : adj.size();
	}

	

	@Override
	public int inDegree(V aVertex) {
		if (aVertex == null)
			throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));

		int degree = 0;

		for (Map.Entry<V, Collection<InternalEdge>> entry : getAdjacencyList().entrySet()) {
			for (InternalEdge edge : entry.getValue()) {
				if (edge.target.equals(aVertex)) {
					degree++;
				}
			}
		}

		return degree;
	}

    @Override
	public int outDegree(V aVertex) {
		if (aVertex == null)
			throw new IllegalArgumentException(Messages.getString("addVertexParamCannotBeNull"));

		Collection<InternalEdge> adj = getAdjacencyList().get(aVertex);
		return adj == null ? 0 : adj.size();
	}

    public void printBFS(V source){
        if (!getAdjacencyList().containsKey(source)){
            return;
        }
        Queue<V> q = new LinkedList<>();
        Map<V, Boolean> visited = new HashMap<>();

        q.add(source);
        visited.put(source, true);

        while (!q.isEmpty()) {
            V current = q.remove();
            System.out.print(current + " ");

            for (InternalEdge edge : getAdjacencyList().get(current)) {
                if (!visited.getOrDefault(edge.target, false)) {
                    q.add(edge.target);
                    visited.put(edge.target, true);
                }
            }
        }
        System.out.println();
    }

    public void printDFS(V source){
        if (!getAdjacencyList().containsKey(source)){
            return;
        }

        Stack<V> stack = new Stack<>();
        Map<V, Boolean> visited = new HashMap<>();

        stack.push(source);
        visited.put(source, true);

        while(!stack.isEmpty()){
            V current = stack.pop();
            System.out.print(current + " ");

            for (InternalEdge edge : getAdjacencyList().get(current)) {
                if (!visited.getOrDefault(edge.target, false)) {
                    stack.push(edge.target);
                    visited.put(edge.target, true);
                }
            }
        }
        System.out.println();
    }

    public Iterable<V> getBFS(V startNode) {
        if (startNode == null ||  !getAdjacencyList().containsKey(startNode)){
            throw new IllegalArgumentException(Messages.getString("vertexParamErrror"));
        }

        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new myIteratorBFS(startNode);
            }
        };
    }

    private class myIteratorBFS implements Iterator<V> {
        Set<V> visited = new HashSet<>();
        Queue<V> theQueue = new LinkedList<>();

        public myIteratorBFS(V startNode) {
            theQueue.add(startNode);
        }

        @Override
        public boolean hasNext() {
            while( !theQueue.isEmpty() ){
                V current = theQueue.peek();

                if(!visited.contains(current)){
                    return true;
                }

                theQueue.poll();
            }

            return false;
        }

        @Override
        public V next() {
            V current = theQueue.poll();
            visited.add(current);

            Collection<InternalEdge> adjListOther = getAdjacencyList().get(current);
            for (InternalEdge edge : adjListOther) {
                if (!visited.contains(edge.target)) {
                    theQueue.add(edge.target);
                }
            }
            return current;
        }
    }

    public Iterable<V> getDFS(V startNode) {
        if (startNode == null ||  !getAdjacencyList().containsKey(startNode)){
            throw new IllegalArgumentException(Messages.getString("vertexParamErrror"));
        }

        return new Iterable<V>() {
            @Override
            public Iterator<V> iterator() {
                return new myIteratorDFS(startNode);
            }
        };
    }

    private class myIteratorDFS implements Iterator<V> {
        Set<V> visited = new HashSet<>();
        Stack<V> theStack = new Stack<>();

        public myIteratorDFS(V startNode) {
            theStack.push(startNode);
        }

        @Override
        public boolean hasNext() {
            while( !theStack.isEmpty() ){
                V current = theStack.peek();

                if(!visited.contains(current)){
                    return true;
                }

                theStack.pop();
            }

            return false;
        }

        @Override
        public V next() {
            V current = theStack.pop();
            visited.add(current);

            Collection<InternalEdge> adjListOther = getAdjacencyList().get(current);
            for (InternalEdge edge : adjListOther) {
                if (!visited.contains(edge.target)) {
                    theStack.push(edge.target);
                }
            }
            return current;
        }
    }

    @Override
    public boolean isBipartite(){

        //check if graph is empty
        if(adjacencyList.isEmpty())
            return true;

        //initialize colors for all vertices
        Map<V, Color> colors = new HashMap<>();

        //color all the vertices with RED color

        for (V v : adjacencyList.keySet()) {
            colors.put(v, Color.RED);
        }

        //start coloring vertices , this code will handle the disconnected graph as well
        //color the first vertex with BLUE
        for(V vertex : adjacencyList.keySet()) {
            if(colors.get(vertex)==Color.RED) {
                colors.put(vertex, Color.BLUE);

                boolean result = isBipartiteUtil(vertex, colors);
                if(!result)
                    return false;
            }
        }
        return true;
    }

    private boolean isBipartiteUtil(V vertex, Map<V, Color> colors){

        //travel all adjacent vertices
        for(InternalEdge edge : adjacencyList.get(vertex)) {
            if(colors.get(edge.target)==Color.RED) {
                if(colors.get(vertex)==Color.BLUE) {
                    colors.put(edge.target,Color.GREEN);
                }
                else if(colors.get(vertex)==Color.GREEN) {
                    colors.put(edge.target, Color.BLUE);
                }
                isBipartiteUtil(edge.target, colors);
            }
            else if(colors.get(vertex)==colors.get(edge.target)) {
                return false;
            }
        }
        return true;
    }
	
	class InternalEdge {
		E edge;
		V target;

		InternalEdge(E propEdge, V target) {
			this.target = target;
			this.edge = propEdge;
		}

		@Override
		public boolean equals(Object obj) {
			@SuppressWarnings("unchecked")
			InternalEdge aux = (InternalEdge) obj;

			return ((edge == null && aux.edge == null) || (edge != null && edge.equals(aux.edge)))
					&& target.equals(aux.target);
		}

		@Override
		public int hashCode() {
			return target.hashCode();
		}

		@Override
		public String toString() {
			return String.format("-[%s]-(%s)", edge, target);
		}
	}
}
