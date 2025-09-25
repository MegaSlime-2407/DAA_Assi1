import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    void sortsArrayInAscendingOrder() {
        int[] input = {5, 3, 8, 1, 2, 7};
        int[] expected = {1, 2, 3, 5, 7, 8};
        QuickSort.quickSort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void handlesEmptyArray() {
        int[] input = {};
        int[] expected = {};
        QuickSort.quickSort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    void handlesSingleElement() {
        int[] input = {42};
        int[] expected = {42};
        QuickSort.quickSort(input);
        assertArrayEquals(expected, input);
    }
}

