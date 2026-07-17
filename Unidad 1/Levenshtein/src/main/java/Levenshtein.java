import static java.lang.Long.max;
import static java.lang.Long.min;

public class Levenshtein {
    private String s1, s2;
    private int n, m;
    private int[][] matrix;

    public int distance(String s1, String s2){
        this.s1 = s1;
        this.s2 = s2;
        this.n = s1.length() + 1;
        this.m = s2.length() + 1;

        matrix = new int[n][m];

        for(int i = 0; i < n; i++){
            matrix[0][i] = i;
            matrix[i][0] = i;
        }

        for(int i = 1; i < n; i++){
            for(int j = 1; j < m; j++){
                matrix[i][j] = getMin(i, j);
            }
        }

        return matrix[n - 1][m - 1];
    }

    public int getMin(int x, int y) {
        int diagonal = 0;
        if (s1.charAt(x - 1) == s2.charAt(y - 1)) {
            diagonal = matrix[x - 1][y - 1];
        }
        else {
            diagonal = matrix[x - 1][y - 1] + 1;
        }
        return Math.min(diagonal, Math.min(matrix[x - 1][y] + 1, matrix[x][y - 1] + 1));
    }

    public double normalized(String s1, String s2){
        return 1 - ((double) distance(s1, s2) / Math.max(s1.length(), s2.length()));
    }

    public void printMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        Levenshtein lev = new Levenshtein();

        String a = "kitten";
        String b = "sitting";

        int d = lev.distance(a, b);

        System.out.println("Distancia: " + d);
        System.out.println("Matriz:");

        lev.printMatrix();
    }
}


