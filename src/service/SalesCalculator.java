package service;

import model.Product;
import model.SalesReport;

import java.util.List;
import java.util.Map;

/**
 * SalesCalculator.java
 * ---------------------------------------------------------
 * Team Member 1 - Summary Calculation Logic
 *
 * Takes a list of Product objects and produces a fully-populated
 * SalesReport object. All the "business logic" of the assignment
 * lives here:
 *   - Revenue per product
 *   - Revenue per category
 *   - Best-selling product (highest quantity_sold)
 *   - Highest revenue product (highest total revenue)
 *   - Grand total revenue
 *
 * This class is deliberately kept free of any console/file output
 * code - it only calculates and returns data. That separation is
 * what makes it easy to unit test (see SalesCalculatorTest) and is
 * also what allows Member 3's SalesReporter class to plug in any
 * OutputStrategy without SalesCalculator ever changing.
 * ---------------------------------------------------------
 */
public class SalesCalculator {

    /**
     * Calculates a full SalesReport from the given list of products.
     *
     * @param products list of products parsed from the CSV file (must not be empty)
     * @return a populated SalesReport
     * @throws IllegalArgumentException if the product list is null or empty
     */
    public SalesReport calculate(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Cannot calculate a sales report from an empty product list.");
        }

        SalesReport report = new SalesReport();
        double grandTotal = 0.0;

        Product bestSeller = products.get(0);
        Product highestRevenueProduct = products.get(0);

        for (Product product : products) {
            double revenue = product.getTotalRevenue();
            grandTotal += revenue;

            // Revenue per product (assumes each product name/id appears once;
            // if it repeats, revenues are summed together).
            report.getRevenuePerProduct().merge(product.getProductName(), revenue, Double::sum);

            // Revenue per category.
            report.getRevenuePerCategory().merge(product.getCategory(), revenue, Double::sum);

            // Track best-selling product by quantity sold.
            if (product.getQuantitySold() > bestSeller.getQuantitySold()) {
                bestSeller = product;
            }

            // Track highest revenue product.
            if (product.getTotalRevenue() > highestRevenueProduct.getTotalRevenue()) {
                highestRevenueProduct = product;
            }
        }

        report.setBestSellingProduct(bestSeller);
        report.setHighestRevenueProduct(highestRevenueProduct);
        report.setGrandTotalRevenue(grandTotal);
        report.setTotalProductsProcessed(products.size());

        return report;
    }
}
