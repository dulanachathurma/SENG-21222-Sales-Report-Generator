package strategy;

import java.io.IOException;

public interface OutputStrategy {

    /**
     * Writes the given report content to some destination.
     *
     * @param reportContent the already-formatted report text to output
     * @param destination   for ConsoleOutputStrategy this is ignored;
     *                      for FileOutputStrategy this is the output file path
     * @throws IOException if writing fails (e.g. file cannot be created)
     */
    void writeReport(String reportContent, String destination) throws IOException;
}
