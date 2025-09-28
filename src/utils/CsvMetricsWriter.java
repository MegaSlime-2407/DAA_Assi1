package utils;

import algorithms.Metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CsvMetricsWriter {
    private final Path file;
    private boolean wroteHeader = false;

    public CsvMetricsWriter(String filePath) { this.file = Path.of(filePath); }

    public void writeHeaderIfNeeded(Metrics m) throws IOException {
        if (!wroteHeader) {
            if (Files.notExists(file)) {
                Files.createDirectories(file.getParent());
            }
            try (FileWriter fw = new FileWriter(file.toFile(), true)) {
                fw.write(m.csvHeader());
                fw.write(System.lineSeparator());
            }
            wroteHeader = true;
        }
    }

    public void append(Metrics m, String algo, int n) throws IOException {
        writeHeaderIfNeeded(m);
        try (FileWriter fw = new FileWriter(file.toFile(), true)) {
            fw.write(m.toCsvRow(algo, n));
            fw.write(System.lineSeparator());
        }
    }
}


