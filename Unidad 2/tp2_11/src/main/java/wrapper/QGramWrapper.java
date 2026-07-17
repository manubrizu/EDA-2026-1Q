package wrapper;

import model.Similarity;

import java.util.HashMap;
import java.util.Map;

public class QGramWrapper {

    private final int q;

    public QGramWrapper(int q) {
        this.q = q;
    }

    public Map<String, Integer> getTokens(String s) {
        Map<String, Integer> map = new HashMap<>();

        String padded = "#".repeat(q - 1) + s + "#".repeat(q - 1);

        for (int i = 0; i <= padded.length() - q; i++) {
            String token = padded.substring(i, i + q);
            map.put(token, map.getOrDefault(token, 0) + 1);
        }

        return map;
    }

    public Similarity compare(String s1, String s2) {
        Map<String, Integer> t1 = getTokens(s1);
        Map<String, Integer> t2 = getTokens(s2);

        int intersection = 0;
        int union = 0;

        for (String key : t1.keySet()) {
            int c1 = t1.getOrDefault(key, 0);
            int c2 = t2.getOrDefault(key, 0);
            intersection += Math.min(c1, c2);
        }

        for (String key : t1.keySet()) {
            union += t1.get(key);
        }
        for (String key : t2.keySet()) {
            union += t2.get(key);
        }
        union -= intersection;

        double score = (union == 0) ? 1.0 : (double) intersection / union;

        String detail = "tokens1=" + t1 + ", tokens2=" + t2;
        return new Similarity("QGrams(3)", score, detail);
    }
}
