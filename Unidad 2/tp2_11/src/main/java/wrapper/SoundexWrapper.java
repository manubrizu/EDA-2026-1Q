package wrapper;

import model.Similarity;
import org.apache.commons.codec.language.Soundex;

public class SoundexWrapper {

    private final Soundex soundex = new Soundex();

    public String encoding(String s) {
        return soundex.encode(s);
    }

    public Similarity compare(String s1, String s2) {
        String e1 = soundex.encode(s1);
        String e2 = soundex.encode(s2);

        double score = e1.equals(e2) ? 1.0 : 0.0;

        String detail = "encoding1=" + e1 + ", encoding2=" + e2;
        return new Similarity("Soundex", score, detail);
    }
}