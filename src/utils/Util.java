package utils;

import algorithms.Metrics;

import java.util.Random;

public class Util {
    private static final Random rng = new Random();
    
    public static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    public static void swap(int[] a, int i, int j, Metrics m) {
        if (m != null) m.swaps++;
        swap(a, i, j);
    }
    
    public static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            swap(a, i, j);
        }
    }
    
    public static void shuffle(int[] a, Metrics m) {
        if (m != null) m.swaps += a.length - 1;
        shuffle(a);
    }
    
    public static int partition(int[] a, int l, int r, int p) {
        int pv = a[p];
        swap(a, p, r);
        int s = l;
        for (int i = l; i < r; i++) {
            if (a[i] < pv) { swap(a, s, i); s++; }
        }
        swap(a, s, r);
        return s;
    }
    
    public static int partition(int[] a, int l, int r, int p, Metrics m) {
        if (m != null) m.partitions++;
        int pv = a[p];
        swap(a, p, r, m);
        int s = l;
        for (int i = l; i < r; i++) {
            if (m != null) m.comparisons++;
            if (a[i] < pv) { swap(a, s, i, m); s++; }
        }
        swap(a, s, r, m);
        return s;
    }
    
    public static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { a[j + 1] = a[j]; j--; }
            a[j + 1] = key;
        }
    }
    
    public static void insertionSort(int[] a, int l, int r, Metrics m) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i], j = i - 1;
            while (j >= l && a[j] > key) { 
                if (m != null) m.comparisons++;
                a[j + 1] = a[j]; j--; 
            }
            a[j + 1] = key;
        }
    }
    
    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i-1]) return false;
        }
        return true;
    }
    
    public static void guard(int[] a) {
        if (a == null) throw new IllegalArgumentException("Array cannot be null");
    }
    
    public static void guard(int[] a, int l, int r) {
        guard(a);
        if (l < 0 || r >= a.length || l > r) 
            throw new IllegalArgumentException("Invalid bounds: " + l + ", " + r);
    }
}
