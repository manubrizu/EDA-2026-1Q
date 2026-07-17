package dependencies;

import java.util.*;

public class TestDependencyGraph<V> {

    private final Map<V, Set<V>> adjacency = new HashMap<>();

    public void addDependency(V before, V after) {
        if (before == null || after == null) {
            throw new RuntimeException("Vertices cannot be null");
        }

        if (before.equals(after)) {
            throw new RuntimeException("Self dependencies are not allowed");
        }

        adjacency.putIfAbsent(before, new HashSet<>());
        adjacency.putIfAbsent(after, new HashSet<>());

        adjacency.get(before).add(after);
    }

    /// 1.1

    public boolean hasCycle() {
        Set<V> visited = new HashSet<>();
        Set<V> stack = new HashSet<>();

        for (V v : adjacency.keySet()) {
            if (!visited.contains(v)) {
                if (hasCycleRec(v, visited, stack)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasCycleRec(V current, Set<V> visited, Set<V> stack) {
        visited.add(current);
        stack.add(current);

        for (V adj : adjacency.get(current)) {
            if (!visited.contains(adj)) {
                if (hasCycleRec(adj, visited, stack)) {
                    return true;
                }
            } else if (stack.contains(adj)) {
                return true;
            }
        }

        stack.remove(current);
        return false;
    }

    /// 1.2

    public Set<V> impactedTests(V v) {
        if (hasCycle()) {
            throw new RuntimeException("ERROR. Hay un ciclo.");
        }

        Set<V> impacted = new HashSet<>();

        if (!adjacency.containsKey(v)) {
            return impacted;
        }

        impactedTestsRec(v, impacted);

        return impacted;
    }

    private void impactedTestsRec(V current, Set<V> impacted) {
        for (V adj : adjacency.get(current)) {
            if (!impacted.contains(adj)) {
                impacted.add(adj);
                impactedTestsRec(adj, impacted);
            }
        }
    }

    public static void main(String[] args) {
        TestDependencyGraph<String> g1 = new TestDependencyGraph<>();

        System.out.println("CASO A");
        g1.addDependency("loginMock", "testCreateUser");
        g1.addDependency("createData", "testCreateUser");
        g1.addDependency("testCreateUser", "testDeleteUser");

        System.out.println(g1.hasCycle());
        System.out.println(g1.impactedTests("loginMock"));

        System.out.println("CASO B");
        TestDependencyGraph<String> g2 = new TestDependencyGraph<>();
        g2.addDependency("createData", "testCreateUser");

        System.out.println(g2.hasCycle());
        System.out.println(g2.impactedTests("testCreateUser"));

        System.out.println("CASO C");
        TestDependencyGraph<String> g3 = new TestDependencyGraph<>();
        g3.addDependency("A", "B");
        g3.addDependency("B", "C");
        g3.addDependency("C", "A");

        System.out.println(g3.hasCycle());
        System.out.println(g3.impactedTests("B"));
    }
}