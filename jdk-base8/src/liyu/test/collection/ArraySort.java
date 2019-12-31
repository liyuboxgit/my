package liyu.test.collection;

import java.util.Arrays;
import java.util.List;

public class ArraySort {
    //选择排序
    public static void optionSort(Integer... arr){
        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i]<arr[j]){
                    swap(i,j,arr);
                }
            }
        }
        List<Integer> ret = Arrays.asList(arr);
        System.out.println(ret);
    }
    //冒泡排序
    public static void popupSort(Integer... arr){
        for(int i=arr.length-1;i>0;i--){
            for(int j=0;j<i;j++){
                if(arr[j]<arr[j+1]){
                    swap(j,j+1,arr);
                }
            }
        }
        List<Integer> ret = Arrays.asList(arr);
        System.out.println(ret);
    }
    //插入排序
    public static void insertSort(Integer... array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i - 1]) {
                int temp = array[i];
                int f = i;
                for (; f >= 1 && array[f - 1] < temp; f--) {
                    array[f] = array[f-1];
                }
                array[f] = temp;
            }
        }
        List<Integer> ret = Arrays.asList(array);
        System.out.println(ret);
    }

    private static void swap(int i, int j, Integer[] arr) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        optionSort(new Integer[]{2,3,5,1,0,9});
        popupSort(new Integer[]{2,3,5,1,0,9});
        insertSort(new Integer[]{2,3,5,1,0,9});
    }
}
