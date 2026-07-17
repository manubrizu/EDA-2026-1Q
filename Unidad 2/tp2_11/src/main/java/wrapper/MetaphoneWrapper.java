package wrapper;

import model.Similarity;
import org.apache.commons.codec.language.Metaphone;

public class MetaphoneWrapper {

    private final Metaphone metaphone = new Metaphone();

    public String encoding(String s) {
        return metaphone.encode(s);
    }

    public Similarity compare(String s1, String s2) {
        String e1 = metaphone.encode(s1);
        String e2 = metaphone.encode(s2);

        double score = e1.equals(e2) ? 1.0 : 0.0;

        String detail = "encoding1=" + e1 + ", encoding2=" + e2;

        return new Similarity("Metaphone", score, detail);
    }
}