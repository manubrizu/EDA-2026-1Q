package timer;

import java.util.Arrays;

public class AlgoB {

    public static int max (int[] array) {
        if (array == null || array.length == 0)
            throw new RuntimeException("Empty array");

        Arrays.sort(array);  // ordena ascendentemente

        return array[array.length - 1];
    }

}