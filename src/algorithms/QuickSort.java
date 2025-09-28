package algorithms;

public class QuickSort {
    private static final int CUTOFF = 10;
    
    public static void quickSort(int[] array) { 
        Util.guard(array);
        if (array.length <= 1) return;
        Util.shuffle(array);
        quickSort(array, 0, array.length - 1); 
    }

    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            if (right - left < CUTOFF) {
                Util.insertionSort(array, left, right);
                return;
            }
            int pivot = partition(array, left, right);
            int leftSize = pivot - left;
            int rightSize = right - pivot;
            if (leftSize < rightSize) {
                quickSort(array, left, pivot - 1);
                quickSort(array, pivot + 1, right);
            } else {
                quickSort(array, pivot + 1, right);
                quickSort(array, left, pivot - 1);
            }
        }
    }

    public static void quickSort(int[] array, Metrics m) { if (m != null) m.reset(); quickSort(array, 0, array.length - 1, m); }
    public static void quickSort(int[] array, int left, int right, Metrics m) {
        if (m != null) m.enter();
        if (left < right) {
            int pivot = partition(array,left,right,m);
            quickSort(array,left,pivot-1,m);
            quickSort(array,pivot+1,right,m);
        }
        if (m != null) m.leave();
    }
    public static int partition(int[] array, int left, int right) {
        int mid = left + (right - left) / 2;
        int pivot = median3(array, left, mid, right);
        return Util.partition(array, left, right, pivot);
    }
    
    private static int median3(int[] a, int i, int j, int k) {
        if (a[i] > a[j]) { if (a[j] > a[k]) return j; else return (a[i] > a[k]) ? k : i; }
        else { if (a[i] > a[k]) return i; else return (a[j] > a[k]) ? k : j; }
    }

    public static int partition(int[] array, int left, int right, Metrics m) {
        int mid = left + (right - left) / 2;
        int pivot = median3(array, left, mid, right);
        return Util.partition(array, left, right, pivot, m);
    }

    public static void swap(int[] array, int i,int j){
        Util.swap(array, i, j);
    }
    public static void swap(int[] array, int i,int j, Metrics m){
        Util.swap(array, i, j, m);
    }
}

