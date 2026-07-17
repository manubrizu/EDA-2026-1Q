package model;

public class Similarity {
    private final String algorithm;
    private final double score;
    private final String detail;

    public Similarity(String algorithm, double score, String detail) {
        this.algorithm = algorithm;
        this.score = score;
        this.detail = detail;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public double getScore() {
        return score;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return algorithm + " -> score=" + score + " | " + detail;
    }
}