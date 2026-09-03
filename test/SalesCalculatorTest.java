import model.Product;
import model.SalesReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.SalesCalculator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SalesCalculatorTest {

    private SalesCalculator calculator;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        calculator = new SalesCalculator();

        // Sample data set used across most tests.
        products = new ArrayList<>();
        products.add(new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50));
        products.add(new Product("P002", "Notebook", "Stationery", 35, 3.75));
        products.add(new Product("P003", "Bluetooth Speaker", "Electronics", 8, 45.00));
        products.add(new Product("P004", "Ball Pen Pack", "Stationery", 60, 2.50));
    }

    @Test
    void testRevenuePerProduct() {
        SalesReport report = calculator.calculate(products);

        assertEquals(306.00, report.getRevenuePerProduct().get("Wireless Mouse"), 0.001);
        assertEquals(131.25, report.getRevenuePerProduct().get("Notebook"), 0.001);
        assertEquals(360.00, report.getRevenuePerProduct().get("Bluetooth Speaker"), 0.001);
        assertEquals(150.00, report.getRevenuePerProduct().get("Ball Pen Pack"), 0.001);
    }

    @Test
    void testRevenuePerCategory() {
        SalesReport report = calculator.calculate(products);

        // Electronics = 306.00 (Mouse) + 360.00 (Speaker) = 666.00
        assertEquals(666.00, report.getRevenuePerCategory().get("Electronics"), 0.001);

        // Stationery = 131.25 (Notebook) + 150.00 (Ball Pen Pack) = 281.25
        assertEquals(281.25, report.getRevenuePerCategory().get("Stationery"), 0.001);
    }

    @Test
    void testBestSellingProductByQuantity() {
        SalesReport report = calculator.calculate(products);

        // Ball Pen Pack has the highest quantity_sold (60).
        assertEquals("Ball Pen Pack", report.getBestSellingProduct().getProductName());
        assertEquals(60, report.getBestSellingProduct().getQuantitySold());
    }

    @Test
    void testHighestRevenueProduct() {
        SalesReport report = calculator.calculate(products);

        // Bluetooth Speaker has the highest total revenue (360.00).
        assertEquals("Bluetooth Speaker", report.getHighestRevenueProduct().getProductName());
        assertEquals(360.00, report.getHighestRevenueProduct().getTotalRevenue(), 0.001);
    }

    @Test
    void testGrandTotalRevenue() {
        SalesReport report = calculator.calculate(products);

        // 306.00 + 131.25 + 360.00 + 150.00 = 947.25
        assertEquals(947.25, report.getGrandTotalRevenue(), 0.001);
    }

    @Test
    void testTotalProductsProcessedCount() {
        SalesReport report = calculator.calculate(products);
        assertEquals(4, report.getTotalProductsProcessed());
    }

    @Test
    void testSingleProductReport() {
        List<Product> singleProduct = new ArrayList<>();
        singleProduct.add(new Product("P010", "Keyboard", "Electronics", 5, 40.00));

        SalesReport report = calculator.calculate(singleProduct);

        assertEquals("Keyboard", report.getBestSellingProduct().getProductName());
        assertEquals("Keyboard", report.getHighestRevenueProduct().getProductName());
        assertEquals(200.00, report.getGrandTotalRevenue(), 0.001);
    }

    @Test
    void testEmptyProductListThrowsException() {
        List<Product> emptyList = new ArrayList<>();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculate(emptyList)
        );

        assertTrue(exception.getMessage().toLowerCase().contains("empty"));
    }

    @Test
    void testNullProductListThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(null));
    }

    @Test
    void testProductTotalRevenueCalculation() {
        Product product = new Product("P099", "Test Item", "Misc", 10, 2.5);
        assertEquals(25.0, product.getTotalRevenue(), 0.001);
    }
}
