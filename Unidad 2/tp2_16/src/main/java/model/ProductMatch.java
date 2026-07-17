package model;

import model.Similarity;

public class ProductMatch {
    private final String productName;
    private final Similarity similarity;

    public ProductMatch(String productName, Similarity similarity) {
        this.productName = productName;
        this.similarity = similarity;
    }

    public String getProductName() {
        return productName;
    }

    public Similarity getSimilarity() {
        return similarity;
    }

    @Override
    public String toString() {
        return productName + " | score=" + similarity.getScore()
                + " | algoritmo=" + similarity.getAlgorithm()
                + " | detalle=" + similarity.getDetail();
    }
}