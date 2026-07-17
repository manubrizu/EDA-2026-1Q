package service;

import model.Similarity;
import wrapper.LevenshteinWrapper;
import wrapper.MetaphoneWrapper;
import wrapper.QGramWrapper;
import wrapper.SoundexWrapper;

import java.util.ArrayList;
import java.util.List;

public class SimilarityService {

    private final SoundexWrapper soundex = new SoundexWrapper();
    private final MetaphoneWrapper metaphone = new MetaphoneWrapper();
    private final LevenshteinWrapper levenshtein = new LevenshteinWrapper();
    private final QGramWrapper qgrams = new QGramWrapper(3);

    public List<Similarity> compareAll(String input, String product) {
        List<Similarity> results = new ArrayList<>();
        results.add(soundex.compare(input, product));
        results.add(metaphone.compare(input, product));
        results.add(levenshtein.compare(input, product));
        results.add(qgrams.compare(input, product));
        return results;
    }

    public Similarity bestOf(String input, String product) {
        return compareAll(input, product).stream()
                .max((a, b) -> Double.compare(a.getScore(), b.getScore()))
                .orElseThrow();
    }

    public Similarity bestSimilarity(String s1, String s2) {
        List<Similarity> results = compareAll(s1, s2);
        Similarity best = results.getFirst();

        for (Similarity sim : results) {
            if (sim.getScore() > best.getScore()) {
                best = sim;
            }
        }
        return best;
    }
}
