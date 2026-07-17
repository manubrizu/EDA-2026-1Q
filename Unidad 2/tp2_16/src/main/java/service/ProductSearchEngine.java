package service;

import model.ProductMatch;
import model.Similarity;
import util.InputNormalizer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductSearchEngine {

    private final SimilarityService similarityService;

    public ProductSearchEngine(SimilarityService similarityService) {
        this.similarityService = similarityService;
    }

    public List<ProductMatch> searchTop5(String userInput, List<String> catalog) {
        String normalizedInput = InputNormalizer.normalize(userInput);

        List<ProductMatch> results = new ArrayList<>();

        for (String product : catalog) {
            Similarity best = similarityService.bestSimilarity(normalizedInput, product);
            results.add(new ProductMatch(product, best));
        }

        results.sort(Comparator.comparingDouble(
                (ProductMatch r) -> r.getSimilarity().getScore()
        ).reversed());

        if (results.size() > 5) {
            return results.subList(0, 5);
        }
        return results;
    }
}