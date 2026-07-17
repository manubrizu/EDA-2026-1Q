package Graph;

abstract class Vertex implements Comparable<Vertex> {
    private String name;

    public Vertex(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Vertex o) {
        return this.name.compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}