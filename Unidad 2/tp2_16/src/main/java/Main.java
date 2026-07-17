import model.ProductMatch;
import service.ProductSearchEngine;
import service.SimilarityService;
import util.CatalogLoader;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("Uso: java Main <ruta-del-catalogo>");
                return;
            }

            String filePath = args[0];
            List<String> catalog = CatalogLoader.loadProducts(filePath);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese producto a buscar: ");
            String input = scanner.nextLine();

            SimilarityService similarityService = new SimilarityService();
            ProductSearchEngine engine = new ProductSearchEngine(similarityService);

            List<ProductMatch> top5 = engine.searchTop5(input, catalog);

            System.out.println("\nTop 5 resultados:");
            for (ProductMatch result : top5) {
                System.out.println(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}