package wrapper;

import model.Similarity;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.LevenshteinResults;

public class LevenshteinWrapper {

    private final LevenshteinDetailedDistance detailedDistance = new LevenshteinDetailedDistance();

    public Similarity compare(String s1, String s2) {
        LevenshteinResults results = detailedDistance.apply(s1, s2);

        int distance = results.getDistance();
        int insertions = results.getInsertCount();
        int deletions = results.getDeleteCount();
        int substitutions = results.getSubstituteCount();

        int maxLen = Math.max(s1.length(), s2.length());
        double score = (maxLen == 0) ? 1.0 : 1.0 - ((double) distance / maxLen);

        String detail = "distance=" + distance
                + ", insertions=" + insertions
                + ", deletions=" + deletions
                + ", substitutions=" + substitutions;

        return new Similarity("Levenshtein", score, detail);
    }
}
