package mabrizuela;

public class Corredores {

    private int[] tiempos;
    private Pedido[] p;

    public int[] tiemposEntre(int[] tiempos, Pedido[] p) {
        this.tiempos = tiempos;
        this.p = p;

        int[] toReturn = new int[p.length];

        for (int i = 0; i < p.length; i++) {
            toReturn[i] = occurrences(p[i]);
        }

        return toReturn;
    }
    
    public static void main(String[] args) {
        Corredores c = new Corredores();

        Pedido[] pedidos = new Pedido[] {
                new Pedido(200, 240),
                new Pedido(180, 210),
                new Pedido(220, 280),
                new Pedido(0, 200),
                new Pedido(290, 10000)
        };

        int[] tiempos = new int[] {
                192,
                200,
                210,
                221,
                229,
                232,
                240,
                240,
                243,
                247,
                280,
                285
        };

        int [] respuestas = c.tiemposEntre(tiempos, pedidos);
        for(int i=0; i< respuestas.length; i++) {
            System.out.println(respuestas[i]);
        }

    }


    private int getClosestPosition(int key) {
        int left = 0;
        int size = tiempos.length;
        int right = size - 1;
        int toReturn = size;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (tiempos[mid] >= key) {
                toReturn = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return toReturn;
    }

    private int occurrences(Pedido key) {
        if (tiempos == null || tiempos.length == 0) {
            return 0;
        }

        int left = getClosestPosition(key.desde);               /// TE ENCUENTRA EL PRIMERO A LA DERECHA
        int right = getClosestPosition(key.hasta + 1);     /// TENGO QUE RESTAR UNO DSP DEL "HASTA"

        return right - left;
    }


}

class Pedido {
    public int desde;
    public int hasta;
    public Pedido(int desde, int hasta) {
        this.desde = desde;
        this.hasta = hasta;
    }
}
