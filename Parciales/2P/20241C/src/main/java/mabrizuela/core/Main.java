package mabrizuela.core;

public class Main {
    public static void main(String[] args) {
        SimpleOrDefault<Character, EmptyEdgeProp> g =
                new SimpleOrDefault<>(false, false, false);

        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('B', 'C', new EmptyEdgeProp());
        g.addEdge('C', 'A', new EmptyEdgeProp());

        System.out.println(g.getNRegular()); // 2
    }
}
