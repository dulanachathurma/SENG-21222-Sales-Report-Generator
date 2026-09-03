package model;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SalesReport.java
 * ---------------------------------------------------------
 * Team Member 1 - Core Domain Model
 *
 * This class does NOT calculate anything itself. It is a simple
 * "results holder" object that SalesCalculator fills in after it
 * finishes processing the list of products. Keeping calculation
 * logic (SalesCalculator) separate from the data holder (this
 * class) follows the Single Responsibility Principle.
 *
 * It also knows how to turn itself into a nicely formatted String,
 * so the same formatted text can be sent to either the console or
 * a file through the Strategy Pattern.
 * ---------------------------------------------------------
 */
public class SalesReport {

    // Revenue earned by each individual product, keyed by product name.
    private final Map<String, Double> revenuePerProduct = new LinkedHashMap<>();

    // Revenue earned by each category, keyed by category name.
    private final Map<String, Double> revenuePerCategory = new LinkedHashMap<>();

    private Product bestSellingProduct;      // highest quantitySold
    private Product highestRevenueProduct;   // highest totalRevenue
    private double grandTotalRevenue;
    private int totalProductsProcessed;

    public Map<String, Double> getRevenuePerProduct() {
        return revenuePerProduct;
    }

    public Map<String, Double> getRevenuePerCategory() {
        return revenuePerCategory;
    }

    public Product getBestSellingProduct() {
        return bestSellingProduct;
    }

    public void setBestSellingProduct(Product bestSellingProduct) {
        this.bestSellingProduct = bestSellingProduct;
    }

    public Product getHighestRevenueProduct() {
        return highestRevenueProduct;
    }

    public void setHighestRevenueProduct(Product highestRevenueProduct) {
        this.highestRevenueProduct = highestRevenueProduct;
    }

    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }

    public void setGrandTotalRevenue(double grandTotalRevenue) {
        this.grandTotalRevenue = grandTotalRevenue;
    }

    public int getTotalProductsProcessed() {
        return totalProductsProcessed;
    }

    public void setTotalProductsProcessed(int totalProductsProcessed) {
        this.totalProductsProcessed = totalProductsProcessed;
    }

    /**
     * Builds the final human-readable report text.
     * This exact String is what gets passed to whichever OutputStrategy
     * is chosen (console or file) - the report content itself does not
     * change based on where it is going.
     */
    public String toFormattedString() {
        StringBuilder sb = new StringBuilder();

        sb.append("=========================================\n");
        sb.append("        PRODUCT SALES REPORT\n");
        sb.append("=========================================\n\n");

        sb.append("Total products processed: ").append(totalProductsProcessed).append("\n\n");

        sb.append("---- Revenue per Product ----\n");
        for (Map.Entry<String, Double> entry : revenuePerProduct.entrySet()) {
            sb.append(String.format("  %-25s Rs. %10.2f%n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n---- Revenue per Category ----\n");
        for (Map.Entry<String, Double> entry : revenuePerCategory.entrySet()) {
            sb.append(String.format("  %-25s Rs. %10.2f%n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n---- Highlights ----\n");
        if (bestSellingProduct != null) {
            sb.append(String.format("  Best-Selling Product   : %s (Qty Sold: %d)%n",
                    bestSellingProduct.getProductName(), bestSellingProduct.getQuantitySold()));
        }
        if (highestRevenueProduct != null) {
            sb.append(String.format("  Highest Revenue Product: %s (Revenue: Rs. %.2f)%n",
                    highestRevenueProduct.getProductName(), highestRevenueProduct.getTotalRevenue()));
        }

        sb.append("\n---- Grand Total ----\n");
        sb.append(String.format("  GRAND TOTAL REVENUE    : Rs. %.2f%n", grandTotalRevenue));
        sb.append("=========================================\n");

        return sb.toString();
    }
}
