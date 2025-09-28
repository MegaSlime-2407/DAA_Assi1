import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class QuickSortTest {
    private static CsvMetricsWriter writer;
    
    static {
        try {
            writer = new CsvMetricsWriter("metrics/test-results.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void sortsArrayInAscendingOrder() throws IOException {
        int[] input = {5, 3, 8, 1, 2, 7};
        int[] expected = {1, 2, 3, 5, 7, 8};
        Metrics m = new Metrics();
        QuickSort.quickSort(input, m);
        assertArrayEquals(expected, input);
        writer.append(m, "QuickSort_Test1", input.length);
    }

    @Test
    void handlesEmptyArray() throws IOException {
        int[] input = {};
        int[] expected = {};
        Metrics m = new Metrics();
        QuickSort.quickSort(input, m);
        assertArrayEquals(expected, input);
        writer.append(m, "QuickSort_Empty", input.length);
    }

    @Test
    void handlesSingleElement() throws IOException {
        int[] input = {42};
        int[] expected = {42};
        Metrics m = new Metrics();
        QuickSort.quickSort(input, m);
        assertArrayEquals(expected, input);
        writer.append(m, "QuickSort_Single", input.length);
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
            QuickSort.quickSort(arr, m);
            writer.append(m, "QuickSort_Perf_" + size, size);
        }
    }
}

