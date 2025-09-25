import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MergeSortTest {
    @Test
    void mergeSortAscendingOrder() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expectedOutput = {1, 2, 3, 4, 5};
        MergeSort.mergeSort(input);
        assertArrayEquals(expectedOutput, input);
    }

    @Test
    void handlesNullInput() {
        int[] input = {};
        int[] expectedOutput = {};
        MergeSort.mergeSort(input);
        assertArrayEquals(expectedOutput, input);
    }

    @Test
    void handlesSingleElement() {
        int[] input = {1};
        int[] expectedOutput = {1};
        MergeSort.mergeSort(input);
        assertArrayEquals(expectedOutput, input);
    }
}