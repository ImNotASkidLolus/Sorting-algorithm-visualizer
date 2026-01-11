import java.util.HashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class sortVisualizer {
    static int speed = 250;
    public static void main(String[] args){
        int[] array = {1, 4, 35, 34, 23, 3, 8, 15, 27, 18, 36, 39, 16, 17, 7, 12, 22, 11, 19, 25, 28, 38, 5, 2, 6, 40, 31, 26, 37, 21, 33, 10, 29, 13, 24, 30, 20, 14, 32, 9};
        for(String arg: args){
            switch (arg){
                case "-h":
                    System.out.println("""
                            THIS IS A HELP MENU!
                            -h → help menu
                            --speed (int) → speed of the sorting animation;
                                -b → bubble sort
                                -q → quick sort
                                -m → merge sort
                                -s → selection sort
                            """);
                    return;
                case "-b":
                    try {
                        bubbleSort(array);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    break;
                case "-q":
                    quickSort(array, 0, array.length - 1);
                    break;      
                case "-m":
                    mergeSort(array, 0, array.length-1);
                    break;
                case "--speed":
                    if(args.length > 1){
                        speed = Integer.parseInt(args[1]);
                        System.out.println(speed);
                    }              
            }
        }
    }
    static void merge(int arr[], int l, int m, int r) throws InterruptedException {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                System.out.print("\033[H");
                printArray(arr);
                System.out.flush();
                Thread.sleep(speed);
                i++;
            }
            else {
                arr[k] = R[j];
                System.out.print("\033[H");
                printArray(arr);
                System.out.flush();
                Thread.sleep(speed);
                j++;
            }
            k++;
        }
        while (i < n1) {
            arr[k] = L[i];
            System.out.print("\033[H");
            printArray(arr);
            System.out.flush();
            Thread.sleep(speed);
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            System.out.print("\033[H");
            printArray(arr);
            System.out.flush();
            Thread.sleep(speed);
            j++;
            k++;
        }
    }
    private static void mergeSort(int arr[], int l, int r){
        
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            try{
                merge(arr, l, m, r);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    private static void quickSort(int[] array, int left, int right){
        if(left < right){            
            int pivot = partition(array, left, right);
            quickSort(array, left, pivot - 1);
            quickSort(array, pivot + 1, right);
        }
    }
    static int partition(int[] array, int left, int right){
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j <= right - 1; j++) {
            if (array[j] < pivot) {
                i++;
                try{
                    swap(array, i, j);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }      
            }
        }
        try{
            swap(array, i + 1, right); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } 
        return i + 1;
    }
    static void swap(int[] array, int i, int j) throws InterruptedException {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        System.out.print("\033[H");
        printArray(array);
        System.out.flush();
        Thread.sleep(speed);
    }


    private static void bubbleSort(int[] array) throws InterruptedException {
        int size = array.length;
        for (int i = 0; i < size - 1; i++){
            for (int j = 0; j < size - i - 1; j++)
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    System.out.print("\033[H");
                    printArray(array);
                    System.out.flush();
                    Thread.sleep(speed);

                }
        }
    }


    public static void printArray(int[] array){
        for(int level = array.length -1; level >= 0; level--){
            HashSet<Integer> numbersOnTheRightLevel = new HashSet<Integer>();
            if(level >= 9){
                System.out.print(level + 1 + "\u001B[31m│\u001B[0m ");
            }else{
                System.out.print(level + 1 + " " + "\u001B[31m│\u001B[0m ");                
            }
            for(int column = 0; column < array.length ; column++){
                numbersOnTheRightLevel.addAll(isOnLevel(array, level));
                int num = array[column];
                //System.out.print(numbersOnTheRightLevel);
                if(numbersOnTheRightLevel.contains(num) && findIndex(array, array[column]) == column){
                    System.out.print("█");
                    //System.out.flush();
                    //Thread.sleep(5);
                }else{
                    System.out.print(" ");
                    //System.out.flush();
                    //Thread.sleep(5);
                }
                System.out.print(" ");

            }
            System.out.println();
        }
        for(int i = 0; i < array.length * 2 + 1; i++){
            if(i ==0 ){
                System.out.print("  \u001B[31m└\u001B[0m");
            }else{
                System.out.print("\u001B[31m─\u001B[0m");
            }
        }
    }
    private static HashSet<Integer> isOnLevel(int[] array, int level){
        HashSet<Integer> onLevel = new HashSet<Integer>();
        for(int i = 0; i < array.length; i++){
            int number = array[i];
            if(number >= level +1){
                onLevel.add(number);
            }
        }
        return onLevel;

    }
    private static int findIndex(int[] array, int number){
        int index;
        for(int i = 0; i < array.length; i++){
            if(array[i] == number){
                index = i;
                //System.out.print(i);
                return index;
            }
        }
        return -1;
    }
}
