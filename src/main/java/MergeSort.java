public class MergeSort {
    private static final int CUTOFF = 10;
    private static int[] aux;
    
    public static void mergeSort(int[] array) { 
        Util.guard(array);
        if (array.length <= 1) return;
        aux = new int[array.length];
        mergeSort(array, 0, array.length - 1);
    }    
    
    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            if (right - left < CUTOFF) {
                Util.insertionSort(array, left, right);
                return;
            }
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    public static void mergeSort(int[] array, Metrics m) { 
        if (m != null) m.reset(); 
        Util.guard(array);
        if (array.length <= 1) return;
        aux = new int[array.length];
        mergeSortM(array, 0, array.length - 1, m); 
    }
    private static void mergeSortM(int[] array, int left, int right, Metrics m) {
        if (m != null) m.enter();
        if (left < right) {
            if (right - left < CUTOFF) {
                Util.insertionSort(array, left, right, m);
                if (m != null) m.leave();
                return;
            }
            int mid = (left + right) / 2;
            mergeSortM(array, left, mid, m);
            mergeSortM(array, mid + 1, right, m);
            mergeM(array,left,mid,right,m);
        }
        if (m != null) m.leave();
    }
    private static void merge(int[] arr, int left, int mid, int right) {
        for (int k = left; k <= right; k++) aux[k] = arr[k];
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > right) arr[k] = aux[i++];
            else if (aux[j] < aux[i]) arr[k] = aux[j++];
            else arr[k] = aux[i++];
        }
    }

    private static void mergeM(int[] arr, int left, int mid, int right, Metrics m) {
        if (m != null) m.merges++;
        for (int k = left; k <= right; k++) aux[k] = arr[k];
        int i = left, j = mid + 1;
        for (int k = left; k <= right; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > right) arr[k] = aux[i++];
            else {
                if (m != null) m.comparisons++;
                if (aux[j] < aux[i]) arr[k] = aux[j++];
                else arr[k] = aux[i++];
            }
        }
    }
}
    

