import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            array[i] = (int)(Math.random()*100);
        }
        System.out.println(Arrays.toString(array));
        QuickSort.quickSort(array, 0, array.length-1);
        System.out.println(Arrays.toString(array));
     }
}