import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class DeterministicSelectTest {
    private static CsvMetricsWriter writer;
    
    static {
        try {
            writer = new CsvMetricsWriter("metrics/test-results.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void findsKthSmallestElement() throws IOException {
        int[] array = {5, 3, 8, 1, 2, 7, 4, 6};
        Metrics m = new Metrics();
        assertEquals(1, DeterministicSelect.select(array, 1, m));
        writer.append(m, "DetSelect_Test1", array.length);
        
        m.reset();
        assertEquals(2, DeterministicSelect.select(array, 2, m));
        writer.append(m, "DetSelect_Test2", array.length);
        
        m.reset();
        assertEquals(3, DeterministicSelect.select(array, 3, m));
        writer.append(m, "DetSelect_Test3", array.length);
        
        m.reset();
        assertEquals(8, DeterministicSelect.select(array, 8, m));
        writer.append(m, "DetSelect_Test4", array.length);
    }
    
    @Test
    void handlesEmptyArray() {
        int[] empty = {};
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(empty, 1);
        });
    }
    
    @Test
    void handlesSingleElement() {
        int[] single = {42};
        assertEquals(42, DeterministicSelect.select(single, 1));
    }
    
    @Test
    void handlesTwoElements() {
        int[] two = {5, 3};
        assertEquals(3, DeterministicSelect.select(two, 1));
        assertEquals(5, DeterministicSelect.select(two, 2));
    }
    
    @Test
    void handlesAlreadySortedArray() {
        int[] sorted = {1, 2, 3, 4, 5, 6, 7, 8};
        assertEquals(1, DeterministicSelect.select(sorted, 1));
        assertEquals(4, DeterministicSelect.select(sorted, 4));
        assertEquals(8, DeterministicSelect.select(sorted, 8));
    }
    
    @Test
    void handlesReverseSortedArray() {
        int[] reverse = {8, 7, 6, 5, 4, 3, 2, 1};
        assertEquals(1, DeterministicSelect.select(reverse, 1));
        assertEquals(4, DeterministicSelect.select(reverse, 4));
        assertEquals(8, DeterministicSelect.select(reverse, 8));
    }
    
    @Test
    void handlesDuplicateElements() {
        int[] duplicates = {3, 1, 3, 2, 1, 3};
        assertEquals(1, DeterministicSelect.select(duplicates, 1));
        assertEquals(1, DeterministicSelect.select(duplicates, 2));
        assertEquals(2, DeterministicSelect.select(duplicates, 3));
        assertEquals(3, DeterministicSelect.select(duplicates, 4));
        assertEquals(3, DeterministicSelect.select(duplicates, 5));
        assertEquals(3, DeterministicSelect.select(duplicates, 6));
    }
    
    @Test
    void handlesLargeArray() {
        int[] large = new int[1000];
        for (int i = 0; i < large.length; i++) {
            large[i] = 1000 - i; // Reverse sorted
        }
        assertEquals(1, DeterministicSelect.select(large, 1));
        assertEquals(500, DeterministicSelect.select(large, 500));
        assertEquals(1000, DeterministicSelect.select(large, 1000));
    }
    
    @Test
    void invalidKThrowsException() {
        int[] array = {1, 2, 3, 4, 5};
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(array, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(array, 6);
        });
    }
    
    @Test
    void nullArrayThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            DeterministicSelect.select(null, 1);
        });
    }
    
    @Test
    void findMedianWorksCorrectly() throws IOException {
        int[] odd = {5, 3, 8, 1, 2, 7, 4}; // 7 elements
        Metrics m = new Metrics();
        assertEquals(4, DeterministicSelect.findMedian(odd));
        writer.append(m, "DetSelect_Median_Odd", odd.length);
        
        int[] even = {5, 3, 8, 1, 2, 7}; // 6 elements
        m.reset();
        assertEquals(3, DeterministicSelect.findMedian(even)); // Lower median
        writer.append(m, "DetSelect_Median_Even", even.length);
        
        int[] single = {42};
        m.reset();
        assertEquals(42, DeterministicSelect.findMedian(single));
        writer.append(m, "DetSelect_Median_Single", single.length);
        
        int[] two = {5, 3};
        m.reset();
        assertEquals(3, DeterministicSelect.findMedian(two));
        writer.append(m, "DetSelect_Median_Two", two.length);
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
            DeterministicSelect.select(arr, size/2, m); // Find median
            writer.append(m, "DetSelect_Perf_" + size, size);
        }
    }
    
    @Test
    void originalArrayNotModified() {
        int[] original = {5, 3, 8, 1, 2, 7, 4, 6};
        int[] copy = original.clone();
        
        DeterministicSelect.select(original, 4);
        
        assertArrayEquals(copy, original);
    }
}
