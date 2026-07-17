package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CatalogLoader {
    public static List<String> loadProducts(String filePath) throws IOException {
        return Files.readAllLines(Path.of(filePath));
    }
}