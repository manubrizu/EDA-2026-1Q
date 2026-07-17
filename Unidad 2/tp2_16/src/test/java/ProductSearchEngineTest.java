import model.ProductMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ProductSearchEngine;
import service.SimilarityService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductSearchEngineTest {

    private ProductSearchEngine engine;
    private List<String> catalog;

    @BeforeEach
    public void setUp() {
        engine = new ProductSearchEngine(new SimilarityService());

        catalog = List.of(
                "porta rollo",
                "secaplatos",
                "tender",
                "tenderero",
                "escurridor",
                "cesto ropa"
        );
    }

    @Test
    public void testPortarollo() {
        List<ProductMatch> results = engine.searchTop5("portarollo", catalog);

        assertFalse(results.isEmpty());
        assertEquals("porta rollo", results.getFirst().getProductName());
    }

    @Test
    public void testSecaplatos() {
        List<ProductMatch> results = engine.searchTop5("secaplatos", catalog);

        assertFalse(results.isEmpty());
        assertEquals("secaplatos", results.getFirst().getProductName());
    }

    @Test
    public void testTenderero() {
        List<ProductMatch> results = engine.searchTop5("tenderero", catalog);

        assertFalse(results.isEmpty());
        assertEquals("tender", results.getFirst().getProductName());
    }

    @Test
    public void testTender() {
        List<ProductMatch> results = engine.searchTop5("tender", catalog);

        assertFalse(results.isEmpty());
        assertEquals("tender", results.getFirst().getProductName());
    }

    @Test
    public void testTenderConAcento() {
        List<ProductMatch> results = engine.searchTop5("ténder", catalog);

        assertFalse(results.isEmpty());
        assertEquals("tender", results.getFirst().getProductName());
    }

    @Test
    public void testPortaRoyo() {
        List<ProductMatch> results = engine.searchTop5("porta royo", catalog);

        assertFalse(results.isEmpty());
        assertEquals("porta rollo", results.getFirst().getProductName());
    }

    @Test
    public void testSecaPlato() {
        List<ProductMatch> results = engine.searchTop5("seca plato", catalog);

        assertFalse(results.isEmpty());
        assertEquals("secaplatos", results.getFirst().getProductName());
    }
}