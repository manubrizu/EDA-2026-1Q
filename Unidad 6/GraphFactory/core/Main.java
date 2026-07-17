package core;

import core_service.GraphBuilder;

public class Main {
    public static void main(String[] args) {
        AdjacencyListGraph<Character, WeightedEdge> g = new Multi<>(true, true, true);

        g.addEdge('E', 'B', new WeightedEdge(3));
        g.addEdge('A', 'B', new WeightedEdge(1));
        g.addEdge('F', 'B', new WeightedEdge(2));
        g.addVertex('D');
        g.addVertex('G');
        g.addEdge('E', 'F', new WeightedEdge(-2));
        g.addEdge('F', 'A', new WeightedEdge(8));
        g.addEdge('F', 'G', new WeightedEdge(2));
        g.addEdge('U', 'G', new WeightedEdge(-10));
        g.addEdge('T', 'U', new WeightedEdge(8));
        g.addEdge('C', 'G', new WeightedEdge(1));
        g.addEdge('G', 'U', new WeightedEdge(0));
        g.addEdge('F', 'F', new WeightedEdge(3));
        g.addEdge('F', 'F', new WeightedEdge(2));

        System.out.println( g.degree('G') );  // 4
        System.out.println( g.degree('F') );  // 8

        g.printBFS('F');
        g.printDFS('F');
    }
}
