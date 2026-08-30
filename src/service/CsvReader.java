package service;

import exception.InvalidCsvFormatException;
import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CsvReader.java
 * ---------------------------------------------------------
 * Team Member 2 - File I/O
 *
 * Responsible for reading the input CSV file from disk and turning
 * each valid data row into a Product object. The header row
 * (product_id, product_name, category, quantity_sold, unit_price)
 * is automatically skipped.
 *
 * Expected CSV format:
 *   product_id, product_name, category, quantity_sold, unit_price
 *   P001, Wireless Mouse, Electronics, 12, 25.50
 * ---------------------------------------------------------
 */
public class CsvReader {

    private static final int EXPECTED_COLUMN_COUNT = 5;

    /**
     * Reads the CSV file at the given path and returns a list of Products.
     *
     * @param filePath path to the CSV file
     * @return list of parsed Product objects (never empty - throws if empty)
     * @throws InvalidCsvFormatException if the file has bad rows or no data
     * @throws IOException               if the file cannot be read (I/O error)
     */
    public List<Product> readProducts(String filePath) throws InvalidCsvFormatException, IOException {
        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            boolean headerSkipped = false;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                // Skip blank lines anywhere in the file.
                if (line.trim().isEmpty()) {
                    continue;
                }

                // The very first non-blank line is treated as the header and skipped.
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                Product product = parseLine(line, lineNumber);
                products.add(product);
            }
        }

        if (products.isEmpty()) {
            throw new InvalidCsvFormatException(
                    "The CSV file '" + filePath + "' contains no product data rows.");
        }

        return products;
    }

    /**
     * Parses one CSV line into a Product object.
     * Package-visible/private helper - keeps parsing logic in one place
     * so it is easy to unit test and easy to explain in a viva.
     */
    private Product parseLine(String line, int lineNumber) throws InvalidCsvFormatException {
        String[] columns = line.split(",");

        if (columns.length != EXPECTED_COLUMN_COUNT) {
            throw new InvalidCsvFormatException(
                    "Line " + lineNumber + ": expected " + EXPECTED_COLUMN_COUNT +
                            " columns but found " + columns.length + " -> \"" + line + "\"");
        }

        String productId = columns[0].trim();
        String productName = columns[1].trim();
        String category = columns[2].trim();
        int quantitySold;
        double unitPrice;

        try {
            quantitySold = Integer.parseInt(columns[3].trim());
        } catch (NumberFormatException e) {
            throw new InvalidCsvFormatException(
                    "Line " + lineNumber + ": quantity_sold is not a valid whole number -> \"" +
                            columns[3].trim() + "\"", e);
        }

        try {
            unitPrice = Double.parseDouble(columns[4].trim());
        } catch (NumberFormatException e) {
            throw new InvalidCsvFormatException(
                    "Line " + lineNumber + ": unit_price is not a valid number -> \"" +
                            columns[4].trim() + "\"", e);
        }

        if (quantitySold < 0) {
            throw new InvalidCsvFormatException(
                    "Line " + lineNumber + ": quantity_sold cannot be negative.");
        }
        if (unitPrice < 0) {
            throw new InvalidCsvFormatException(
                    "Line " + lineNumber + ": unit_price cannot be negative.");
        }

        return new Product(productId, productName, category, quantitySold, unitPrice);
    }
}
