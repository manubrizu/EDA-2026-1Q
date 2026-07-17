import java.util.HashMap;

public class Qgram {
    private int q;

    private HashMap<String, Integer> qmap;

    Qgram(int q){
        this.q = q;
    }

    public void printTokens(String s){
        String hashes = "";
        for(int i = 0; i < q - 1; i++) {
            hashes += "#";
        }
        s = hashes + s + hashes;

        qmap = new HashMap<>();
        for(int i = 0; i < s.length() - 1; i++) {
            String token = "" + s.charAt(i) + s.charAt(i+1);
            if(!qmap.containsKey(token)) {
                qmap.put(token, 1);
            }
            else{
                qmap.put(token, qmap.get(token) + 1);
            }
        }

        for(String key : qmap.keySet()) {
            System.out.println(key + " -> " + qmap.get(key));
        }
    }

    public static void main(String[] args) {
        Qgram qg = new Qgram(2);

        qg.printTokens("alal");

    }
}
