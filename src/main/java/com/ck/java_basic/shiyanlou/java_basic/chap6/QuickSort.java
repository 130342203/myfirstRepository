package com.ck.java_basic.shiyanlou.java_basic.chap6;

import java.util.Arrays;
/*
* 快速排序
*
* 快速排序（英语：Quicksort），又称划分交换排序（partition-exchange sort），简称快排，也是一种排序算法。最早由东尼·霍尔提出。在平均状况下，排序 n 个项目要 O(nlogn) 次比较。在最坏状况下则需要 O(n^2) 次比较，但这种状况并不常见。事实上，快速排序 O(nlogn) 通常明显比其他算法更快，因为它的内部循环（inner loop）可以在大部分的架构上很有效率地达成。
* */
/*
* 快速排序使用分治法（Divide and conquer）策略来把一个序列（list）分为两个子序列（sub-lists）。

步骤为：

从数列中挑出一个元素，称为“基准”（pivot），
重新排序数列，所有比基准值小的元素摆放在基准前面，所有比基准值大的元素摆在基准后面（相同的数可以到任何一边）。在这个分割结束之后，该基准就处于数列的中间位置。这个称为分割（partition）操作。
递归地（recursively）把小于基准值元素的子数列和大于基准值元素的子数列排序。
递归到最底部时，数列的大小是零或一，也就是已经排序好了。这个算法一定会结束，因为在每次的迭代（iteration）中，它至少会把一个元素摆到它最后的位置去。
* */
public class QuickSort {
    public static void sort(int[] arr, int head, int tail) {
        if (head >= tail || arr == null || arr.length <= 1) {
            return;
        }
        //设置数组的起始位置 i 结束位置j 基准 pivot 为数组的中间
        int i = head, j = tail, pivot = arr[(head + tail) / 2];
        while (i <= j) {
            //当数组小于基准 循环结束后 相当于i所处的位置的值为大于基准的元素
            while (arr[i] < pivot) {
                ++i;
            }
            //当数组大于基准 循环结束后 相当于j所处的位置的值为小于于基准的元素
            while (arr[j] > pivot) {
                --j;
            }
            //如果i<j 那么则将交互i j对应位置的值
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                //将指针继续移动
                ++i;
                --j;
            } else if (i == j) {
//如果i=j 那么说明本次排序已经结束 将i++ 如果这里不使用i++ 那么后面的sort(arr,i,tail)将改为arr(arr,i+1,tail)
                ++i;
            }
        }
        //继续将数组分割
        sort(arr, head, j);
        sort(arr, i, tail);
    }

    public static void main(String[] args) {
        int[] ints = {5, 3, 4, 1, 2};
        sort(ints, 0, ints.length - 1);
        System.out.println(Arrays.toString(ints));
    }
}