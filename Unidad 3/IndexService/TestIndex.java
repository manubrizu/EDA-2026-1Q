import java.util.Arrays;

public class TestIndex {
    public static void main(String[] args) {
        IndexWithDuplicates index = new IndexWithDuplicates();

        index.initialize(new int[]{7, 3, 5, 3, 9, 1, 7, 3});

        System.out.println(Arrays.toString(index.range(3, 7, true, true)));
        // esperado: [3, 3, 3, 5, 7, 7]

        System.out.println(Arrays.toString(index.range(3, 7, false, true)));
        // esperado: [5, 7, 7]

        System.out.println(Arrays.toString(index.range(3, 7, true, false)));
        // esperado: [3, 3, 3, 5]

        System.out.println(Arrays.toString(index.range(3, 7, false, false)));
        // esperado: [5]

        System.out.println(Arrays.toString(index.range(4, 8, true, true)));
        // esperado: [5, 7, 7]

        System.out.println(Arrays.toString(index.range(10, 20, true, true)));
        // esperado: []

        System.out.println(Arrays.toString(index.range(1, 1, true, true)));
        // esperado: [1]

        System.out.println(Arrays.toString(index.range(1, 1, false, false)));
        // esperado: []
    }
}