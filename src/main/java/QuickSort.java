public class QuickSort {
    public static void quickSort(int[] array) { quickSort(array, 0 , array.length-1); }

    public static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivot = partition(array,left,right);
            quickSort(array,left,pivot-1);
            quickSort(array,pivot+1,right);
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
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array,i,j);
            }
        }
        swap(array,i+1,right);
        return i+1;
    }

    public static int partition(int[] array, int left, int right, Metrics m) {
        if (m != null) m.partitions++;
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (m != null) m.comparisons++;
            if (array[j] < pivot) {
                i++;
                swap(array,i,j,m);
            }
        }
        swap(array,i+1,right,m);
        return i+1;
    }

    public static void swap(int[] array, int i,int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    public static void swap(int[] array, int i,int j, Metrics m){
        if (m != null) m.swaps++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}

