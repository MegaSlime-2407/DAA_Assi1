import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                runDemo();
            } else {
                runWithArgs(args);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void runDemo() throws Exception {
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
    }
    
    private static void runWithArgs(String[] args) throws Exception {
        String algo = args[0];
        int size = args.length > 1 ? Integer.parseInt(args[1]) : 10;
        String output = args.length > 2 ? args[2] : "metrics/out.csv";
        
        CsvMetricsWriter writer = new CsvMetricsWriter(output);
        Metrics m = new Metrics();
        
        switch (algo.toLowerCase()) {
            case "quicksort":
            case "qs":
                runQuickSort(size, m, writer);
                break;
            case "mergesort":
            case "ms":
                runMergeSort(size, m, writer);
                break;
            case "select":
            case "ds":
                runSelect(size, m, writer);
                break;
            case "closest":
            case "cp":
                runClosestPair(size, m, writer);
                break;
            case "all":
                runAll(size, m, writer);
                break;
            default:
                System.out.println("Usage: java Main [algorithm] [size] [output]");
                System.out.println("Algorithms: quicksort, mergesort, select, closest, all");
                System.out.println("Example: java Main quicksort 100 metrics/results.csv");
        }
    }
    
    private static void runQuickSort(int size, Metrics m, CsvMetricsWriter writer) throws Exception {
        int[] arr = generateArray(size);
        System.out.println("QuickSort on " + size + " elements");
        QuickSort.quickSort(arr, m);
        System.out.println("Comparisons: " + m.comparisons + ", Swaps: " + m.swaps + ", MaxDepth: " + m.maxDepth);
        writer.append(m, "QuickSort_" + size, size);
    }
    
    private static void runMergeSort(int size, Metrics m, CsvMetricsWriter writer) throws Exception {
        int[] arr = generateArray(size);
        System.out.println("MergeSort on " + size + " elements");
        MergeSort.mergeSort(arr, m);
        System.out.println("Comparisons: " + m.comparisons + ", Merges: " + m.merges + ", MaxDepth: " + m.maxDepth);
        writer.append(m, "MergeSort_" + size, size);
    }
    
    private static void runSelect(int size, Metrics m, CsvMetricsWriter writer) throws Exception {
        int[] arr = generateArray(size);
        System.out.println("DeterministicSelect on " + size + " elements");
        int median = DeterministicSelect.select(arr, size/2, m);
        System.out.println("Median: " + median + ", Comparisons: " + m.comparisons + ", Partitions: " + m.partitions);
        writer.append(m, "DetSelect_" + size, size);
    }
    
    private static void runClosestPair(int size, Metrics m, CsvMetricsWriter writer) throws Exception {
        ClosestPair.Point[] pts = generatePoints(size);
        System.out.println("ClosestPair on " + size + " points");
        ClosestPair.Result result = ClosestPair.findClosestPair(pts, m);
        System.out.println("Distance: " + String.format("%.3f", result.distance) + ", Comparisons: " + m.comparisons);
        writer.append(m, "ClosestPair_" + size, size);
    }
    
    private static void runAll(int size, Metrics m, CsvMetricsWriter writer) throws Exception {
        runQuickSort(size, m, writer);
        m.reset();
        runMergeSort(size, m, writer);
        m.reset();
        runSelect(size, m, writer);
        m.reset();
        runClosestPair(size, m, writer);
    }
    
    private static int[] generateArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int)(Math.random() * 1000);
        }
        return arr;
    }
    
    private static ClosestPair.Point[] generatePoints(int size) {
        ClosestPair.Point[] pts = new ClosestPair.Point[size];
        for (int i = 0; i < size; i++) {
            pts[i] = new ClosestPair.Point(Math.random() * 100, Math.random() * 100);
        }
        return pts;
    }
}

