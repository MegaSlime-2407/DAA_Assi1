import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            CsvMetricsWriter writer = new CsvMetricsWriter("metrics/out.csv");
            Metrics m = new Metrics();
            
            // QuickSort demo
            int[] a1 = {64, 34, 25, 12, 22, 11, 90, 5};
            System.out.println("QuickSort: " + Arrays.toString(a1));
            QuickSort.quickSort(a1, m);
            System.out.println("Sorted: " + Arrays.toString(a1));
            writer.append(m, "QuickSort", a1.length);
            System.out.println("Metrics: comparisons=" + m.comparisons + ", swaps=" + m.swaps + ", maxDepth=" + m.maxDepth);
            
            // MergeSort demo
            m.reset();
            int[] a2 = {38, 27, 43, 3, 9, 82, 10};
            System.out.println("\nMergeSort: " + Arrays.toString(a2));
            MergeSort.mergeSort(a2, m);
            System.out.println("Sorted: " + Arrays.toString(a2));
            writer.append(m, "MergeSort", a2.length);
            System.out.println("Metrics: comparisons=" + m.comparisons + ", merges=" + m.merges + ", maxDepth=" + m.maxDepth);
            
            // DeterministicSelect demo
            m.reset();
            int[] a3 = {7, 10, 4, 3, 20, 15};
            System.out.println("\nDeterministicSelect: " + Arrays.toString(a3));
            int kth = DeterministicSelect.select(a3, 3, m);
            System.out.println("3rd smallest: " + kth);
            writer.append(m, "DetSelect", a3.length);
            System.out.println("Metrics: comparisons=" + m.comparisons + ", partitions=" + m.partitions + ", maxDepth=" + m.maxDepth);
            
            // ClosestPair demo
            m.reset();
            ClosestPair.Point[] pts = {
                new ClosestPair.Point(2, 3),
                new ClosestPair.Point(12, 30),
                new ClosestPair.Point(40, 50),
                new ClosestPair.Point(5, 1),
                new ClosestPair.Point(12, 10),
                new ClosestPair.Point(3, 4)
            };
            System.out.println("\nClosestPair: 6 points");
            ClosestPair.Result result = ClosestPair.findClosestPair(pts, m);
            System.out.println("Closest: (" + result.p1.x + "," + result.p1.y + ") to (" + result.p2.x + "," + result.p2.y + ")");
            System.out.println("Distance: " + String.format("%.3f", result.distance));
            writer.append(m, "ClosestPair", pts.length);
            System.out.println("Metrics: comparisons=" + m.comparisons + ", stripChecks=" + m.stripChecks + ", maxDepth=" + m.maxDepth);
            
            System.out.println("\nMetrics saved to metrics/out.csv");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

