import model.Similarity;
import service.SimilarityService;

public class Main {
    public static void main(String[] args) {
        SimilarityService service = new SimilarityService();

        String input = "porta royo";
        String product = "porta rollo";

        System.out.println("Todos:");
        for (Similarity r : service.compareAll(input, product)) {
            System.out.println(r);
        }

        System.out.println("\nMejor:");
        System.out.println(service.bestOf(input, product));
    }
}