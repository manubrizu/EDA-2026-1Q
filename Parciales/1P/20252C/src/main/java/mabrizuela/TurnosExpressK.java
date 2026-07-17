package mabrizuela;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TurnosExpressK {

    private static BoundedQueue<String> queue;


    public static void atender(int K, List<String> tokens) {
        int contA = 0, contR = 0, contC = 0;
        queue = new BoundedQueue<>(K);

        for (String token : tokens) {
            if (Objects.equals(token, "S")) {
                if (!queue.isEmpty()) {
                    queue.dequeue();
                    contA++;
                    contC--;
                }
            } else {
                if (!queue.isFull()) {
                    queue.enqueue(token);
                    contC++;
                } else {
                    contR++;
                }
            }
        }

        System.out.println("ATENDIDOS=" + contA + " RECHAZADOS=" + contR + " EN_COLA=" + contC);
    }



    public static void main(String[] args) {
        System.out.println("== Caso 1 ==");
        atender(3,
                Arrays.asList("A:A1","A:A2","A:A3","A:A4","S","A:A5","S","A:A6","S","S","A:A7","A:A8"
        ));

        System.out.println("== Caso 2 ==");
        atender(2,
                Arrays.asList("S","A:P1","S","S","A:P2","A:P3","S","A:P4","S"
        ));

        System.out.println("== Caso 3 ==");
        atender(2,
                Arrays.asList("A:X","A:Y","A:Z","A:W","S","A:K","A:L","A:M"
        ));
        System.out.println("== Caso 4 ==");
        atender(3,
                Arrays.asList("A:P1","A:P2","A:P3","A:P4","S","A:P5","S","A:P6","S","S"
        ));
        System.out.println("== Caso 5 ==");
        atender(2,
                Arrays.asList("S","S", "A:P1", "A:P2"
        ));
    }
}