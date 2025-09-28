package algorithms;

import utils.Util;

public class DeterministicSelect {

    public static int select(int[] array, int k) {
        if (array == null || array.length == 0) throw new IllegalArgumentException("Array cannot be null or empty");
        if (k < 1 || k > array.length) throw new IllegalArgumentException("k must be between 1 and array length");
        int[] copy = array.clone();
        return select(copy, 0, copy.length - 1, k - 1);
    }

    public static int select(int[] array, int k, Metrics m) {
        if (m != null) m.reset();
        if (array == null || array.length == 0) throw new IllegalArgumentException("Array cannot be null or empty");
        if (k < 1 || k > array.length) throw new IllegalArgumentException("k must be between 1 and array length");
        int[] copy = array.clone();
        return select(copy, 0, copy.length - 1, k - 1, m);
    }

    private static int select(int[] a, int l, int r, int k) {
        if (l == r) return a[l];
        int p = medianOfMedians(a, l, r);
        int m = partition(a, l, r, p);
        if (k == m) return a[k];
        return k < m ? select(a, l, m - 1, k) : select(a, m + 1, r, k);
    }

    private static int select(int[] a, int l, int r, int k, Metrics m) {
        if (m != null) m.enter();
        int res;
        if (l == r) res = a[l];
        else {
            int p = medianOfMedians(a, l, r, m);
            int mpos = partition(a, l, r, p, m);
            if (k == mpos) res = a[k];
            else if (k < mpos) res = select(a, l, mpos - 1, k, m);
            else res = select(a, mpos + 1, r, k, m);
        }
        if (m != null) m.leave();
        return res;
    }

    private static int medianOfMedians(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) { insertionSort(a, l, r); return l + n / 2; }
        int groups = (n + 4) / 5;
        int[] medVals = new int[groups];
        int[] medIdxs = new int[groups];
        for (int i = 0; i < groups; i++) {
            int gl = l + i * 5;
            int gr = Math.min(gl + 4, r);
            insertionSort(a, gl, gr);
            int mi = gl + (gr - gl) / 2;
            medVals[i] = a[mi];
            medIdxs[i] = mi;
        }
        int pick = medianIndexOfSmall(medVals, 0, groups - 1);
        return medIdxs[pick];
    }

    private static int medianOfMedians(int[] a, int l, int r, Metrics m) {
        int n = r - l + 1;
        if (n <= 5) { insertionSort(a, l, r, m); return l + n / 2; }
        int groups = (n + 4) / 5;
        int[] medVals = new int[groups];
        int[] medIdxs = new int[groups];
        for (int i = 0; i < groups; i++) {
            int gl = l + i * 5;
            int gr = Math.min(gl + 4, r);
            insertionSort(a, gl, gr, m);
            int mi = gl + (gr - gl) / 2;
            medVals[i] = a[mi];
            medIdxs[i] = mi;
        }
        int pick = medianIndexOfSmall(medVals, 0, groups - 1, m);
        return medIdxs[pick];
    }

    private static int medianIndexOfSmall(int[] a, int l, int r) {
        int n = r - l + 1;
        if (n <= 5) { insertionSort(a, l, r); return l + n / 2; }
        int[] copy = new int[n];
        System.arraycopy(a, l, copy, 0, n);
        insertionSort(copy, 0, n - 1);
        int target = copy[n / 2];
        for (int i = l; i <= r; i++) if (a[i] == target) return i;
        return l + n / 2;
    }

    private static int medianIndexOfSmall(int[] a, int l, int r, Metrics m) {
        int n = r - l + 1;
        if (n <= 5) { insertionSort(a, l, r, m); return l + n / 2; }
        int[] copy = new int[n];
        System.arraycopy(a, l, copy, 0, n);
        insertionSort(copy, 0, n - 1, m);
        int target = copy[n / 2];
        for (int i = l; i <= r; i++) { if (m != null) m.comparisons++; if (a[i] == target) return i; }
        return l + n / 2;
    }

    private static int partition(int[] a, int l, int r, int pIdx) {
        int pv = a[pIdx];
        swap(a, pIdx, r);
        int s = l;
        for (int i = l; i < r; i++) if (a[i] < pv) { swap(a, s, i); s++; }
        swap(a, s, r);
        return s;
    }

    private static int partition(int[] a, int l, int r, int pIdx, Metrics m) {
        if (m != null) m.partitions++;
        int pv = a[pIdx];
        swap(a, pIdx, r, m);
        int s = l;
        for (int i = l; i < r; i++) { if (m != null) m.comparisons++; if (a[i] < pv) { swap(a, s, i, m); s++; } }
        swap(a, s, r, m);
        return s;
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    private static void insertionSort(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { if (m != null) m.comparisons++; a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }
    private static void swap(int[] a, int i, int j, Metrics m) { if (m != null) m.swaps++; int t = a[i]; a[i] = a[j]; a[j] = t; }

    public static int findMedian(int[] array) {
        if (array == null || array.length == 0) throw new IllegalArgumentException("Array cannot be null or empty");
        int n = array.length;
        return (n % 2 == 1) ? select(array, n / 2 + 1) : select(array, n / 2);
    }
}
