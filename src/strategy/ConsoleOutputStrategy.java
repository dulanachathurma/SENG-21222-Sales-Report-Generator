package strategy;

/**
 * ConsoleOutputStrategy.java
 *
 * Simply prints the report content to standard output (the terminal / console). 
 * 
 */
public class ConsoleOutputStrategy implements OutputStrategy {

    @Override
    public void writeReport(String reportContent, String destination) {
        System.out.println(reportContent);
    }
}
