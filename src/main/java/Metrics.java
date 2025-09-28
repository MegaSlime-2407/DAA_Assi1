import java.util.Locale;

public class Metrics {

    public long comparisons;
    public long swaps;
    public long partitions;
    public long merges;
    public long stripChecks;
    public long recursiveCalls;
    public int depth;
    public int maxDepth;

    public void reset() {
        comparisons = swaps = partitions = merges = stripChecks = recursiveCalls = 0;
        depth = maxDepth = 0;
    }

    public void enter() { depth++; if (depth > maxDepth) maxDepth = depth; recursiveCalls++; }
    public void leave() { if (depth > 0) depth--; }

    public String csvHeader() {
        return "algo,n,comparisons,swaps,partitions,merges,stripChecks,recursiveCalls,maxDepth";
    }

    public String toCsvRow(String algo, int n) {
        return String.format(Locale.ROOT, "%s,%d,%d,%d,%d,%d,%d,%d,%d",
                algo, n, comparisons, swaps, partitions, merges, stripChecks, recursiveCalls, maxDepth);
    }
}


