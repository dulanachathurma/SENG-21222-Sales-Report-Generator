package strategy;

import java.io.IOException;

public interface OutputStrategy {


    void writeReport(String reportContent, String destination) throws IOException;
}
