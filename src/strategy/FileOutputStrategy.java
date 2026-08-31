package strategy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileOutputStrategy implements OutputStrategy {

    @Override
    public void writeReport(String reportContent, String destination) throws IOException {
        if (destination == null || destination.trim().isEmpty()) {
            throw new IOException("Output file path was not provided for 'file' output method.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destination))) {
            writer.write(reportContent);
        }
    }
}
