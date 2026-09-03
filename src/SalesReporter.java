import exception.InvalidArgumentsException;
import exception.InvalidCsvFormatException;
import model.Product;
import model.SalesReport;
import service.CsvReader;
import service.SalesCalculator;
import strategy.ConsoleOutputStrategy;
import strategy.FileOutputStrategy;
import strategy.OutputStrategy;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SalesReporter {

    
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_ARGUMENT_ERROR = 1;
    private static final int EXIT_CSV_ERROR = 2;
    private static final int EXIT_IO_ERROR = 3;
    private static final int EXIT_UNEXPECTED_ERROR = 4;

    public static void main(String[] args) {
        try {
            run(args);
            System.exit(EXIT_SUCCESS);

        } catch (InvalidArgumentsException e) {
          
            System.err.println("[Argument Error] " + e.getMessage());
            printUsage();
            System.exit(EXIT_ARGUMENT_ERROR);

        } catch (InvalidCsvFormatException e) {
           
            System.err.println("[CSV Format Error] " + e.getMessage());
            System.exit(EXIT_CSV_ERROR);

        } catch (IOException e) {
            
            System.err.println("[File I/O Error] " + e.getMessage());
            System.exit(EXIT_IO_ERROR);

        } catch (Exception e) {
           
            System.err.println("[Unexpected Error] " + e.getMessage());
            System.exit(EXIT_UNEXPECTED_ERROR);
        }
    }

    /*
      Orchestrates the full program flow:
        1 Validate CLI arguments
        2 Read & parse the CSV file into Product objects
        3 Calculate the SalesReport
        4 Format the report as text
        5 Send it to the chosen OutputStrategy
     */
    private static void run(String[] args) throws InvalidArgumentsException, InvalidCsvFormatException, IOException {

        //  STEP 1 ,Validate CLI arguments
        validateArgs(args);

        String csvFilePath = args[0];
        String outputMethod = args[1].toLowerCase();
        String outputFilePath = (args.length == 3) ? args[2] : null;

        validateCsvFileExists(csvFilePath);

        // STEP 2, Read CSV into Product list (Member 2's CsvReader) 
        CsvReader csvReader = new CsvReader();
        List<Product> products = csvReader.readProducts(csvFilePath);

        //STEP 3, Calculate the report (Member 1's SalesCalculator) 
        SalesCalculator calculator = new SalesCalculator();
        SalesReport report = calculator.calculate(products);

        //  STEP 4, Format report as text 
        String reportContent = report.toFormattedString();

        //  STEP 5, Pick the right Strategy and output the report
        
        OutputStrategy strategy = resolveOutputStrategy(outputMethod);
        strategy.writeReport(reportContent, outputFilePath);

        if ("file".equals(outputMethod)) {
            System.out.println("Report successfully written to: " + outputFilePath);
        }
    }

  
   
    private static void validateArgs(String[] args) throws InvalidArgumentsException {
        if (args.length < 2 || args.length > 3) {
            throw new InvalidArgumentsException(
                    "Expected 2 or 3 arguments, but received " + args.length + ".");
        }

        String csvFilePath = args[0];
        String outputMethod = args[1].toLowerCase();

        if (csvFilePath.trim().isEmpty()) {
            throw new InvalidArgumentsException("The CSV file path cannot be empty.");
        }

        if (!outputMethod.equals("console") && !outputMethod.equals("file")) {
            throw new InvalidArgumentsException(
                    "Invalid output-method '" + args[1] + "'. Must be either 'console' or 'file'.");
        }

        if (outputMethod.equals("file") && args.length != 3) {
            throw new InvalidArgumentsException(
                    "Output-method 'file' requires a third argument: [output-file-path].");
        }

        if (outputMethod.equals("console") && args.length == 3) {
            throw new InvalidArgumentsException(
                    "Output-method 'console' does not take an output-file-path argument.");
        }

        if (outputMethod.equals("file") && args[2].trim().isEmpty()) {
            throw new InvalidArgumentsException("The output-file-path cannot be empty.");
        }
    }

   
    private static void validateCsvFileExists(String csvFilePath) throws IOException {
        File file = new File(csvFilePath);

        if (!file.exists()) {
            throw new IOException("CSV file not found at path: " + csvFilePath);
        }
        if (!file.isFile()) {
            throw new IOException("Path exists but is not a regular file: " + csvFilePath);
        }
        if (!file.canRead()) {
            throw new IOException("CSV file cannot be read (check permissions): " + csvFilePath);
        }
    }

    
    private static OutputStrategy resolveOutputStrategy(String outputMethod) {
        switch (outputMethod) {
            case "console":
                return new ConsoleOutputStrategy();
            case "file":
                return new FileOutputStrategy();
            default:
                
                throw new IllegalStateException("Unknown output method: " + outputMethod);
        }
    }

    
    private static void printUsage() {
        System.err.println();
        System.err.println("Usage: java SalesReporter <csv-file-path> <output-method> [output-file-path]");
        System.err.println("  <csv-file-path>    : path to the input CSV file");
        System.err.println("  <output-method>    : 'console' or 'file'");
        System.err.println("  [output-file-path] : required only when output-method is 'file'");
        System.err.println();
        System.err.println("Examples:");
        System.err.println("  java SalesReporter sales.csv console");
        System.err.println("  java SalesReporter sales.csv file report.txt");
    }
}
