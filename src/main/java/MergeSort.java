public class MergeSort {
    public static void mergeSort(int[] array) { mergeSort(array,0,array.length-1);}    
    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array,left,mid,right);
        }
    }

    public static void mergeSort(int[] array, Metrics m) { if (m != null) m.reset(); mergeSortM(array, 0, array.length - 1, m); }
    private static void mergeSortM(int[] array, int left, int right, Metrics m) {
        if (m != null) m.enter();
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortM(array, left, mid, m);
            mergeSortM(array, mid + 1, right, m);
            mergeM(array,left,mid,right,m);
        }
        if (m != null) m.leave();
    }
    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];}
            else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        for (int l = 0; l < temp.length; l++) {
            arr[left + l] = temp[l];
        }
    }

    private static void mergeM(int[] arr, int left, int mid, int right, Metrics m) {
        if (m != null) m.merges++;
        int[] temp = new int[right - left + 1];
        int i = left;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= right) {
            if (m != null) m.comparisons++;
            if (arr[i] <= arr[j]) temp[k++] = arr[i++]; else temp[k++] = arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        for (int l = 0; l < temp.length; l++) arr[left + l] = temp[l];
    }
}
    

