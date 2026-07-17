package core;

public class Person implements Comparable<Person> {

    private String name;
    private Integer legajo;

    public Person(Integer legajo, String name) {
        this.legajo = legajo;
        this.name = name;
    }


    @Override
    public int compareTo(Person o) {
        int cmp = o.legajo.compareTo(legajo);
        if(cmp == 0){
            cmp = o.name.compareTo(name);
        }
        return cmp;
    }

    @Override
    public String toString() {
        return "legajo = %d \n nombre = %s".formatted(legajo, name);
    }
}
