import algorithms.MergeSort;
import algorithms.Metrics;
import org.junit.jupiter.api.Test;
import utils.CsvMetricsWriter;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

class MergeSortTest {
    private static CsvMetricsWriter writer;
    
    static {
        try {
            writer = new CsvMetricsWriter("metrics/test-results.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void mergeSortAscendingOrder() throws IOException {
        int[] input = {5, 4, 3, 2, 1};
        int[] expectedOutput = {1, 2, 3, 4, 5};
        Metrics m = new Metrics();
        MergeSort.mergeSort(input, m);
        assertArrayEquals(expectedOutput, input);
        writer.append(m, "MergeSort_Test1", input.length);
    }

    @Test
    void handlesNullInput() throws IOException {
        int[] input = {};
        int[] expectedOutput = {};
        Metrics m = new Metrics();
        MergeSort.mergeSort(input, m);
        assertArrayEquals(expectedOutput, input);
        writer.append(m, "MergeSort_Empty", input.length);
    }

    @Test
    void handlesSingleElement() throws IOException {
        int[] input = {1};
        int[] expectedOutput = {1};
        Metrics m = new Metrics();
        MergeSort.mergeSort(input, m);
        assertArrayEquals(expectedOutput, input);
        writer.append(m, "MergeSort_Single", input.length);
    }
    
    @Test
    void performanceTest() throws IOException {
        int[] sizes = {10, 50, 100, 500};
        for (int size : sizes) {
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = (int)(Math.random() * 1000);
            }
            Metrics m = new Metrics();
            MergeSort.mergeSort(arr, m);
            writer.append(m, "MergeSort_Perf_" + size, size);
        }
    }
}