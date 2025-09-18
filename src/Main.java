import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random()*100);
        }
        System.out.println(Arrays.toString(array));
        MergeSort.mergeSort(array);
        System.out.println(Arrays.toString(array));
     }
}