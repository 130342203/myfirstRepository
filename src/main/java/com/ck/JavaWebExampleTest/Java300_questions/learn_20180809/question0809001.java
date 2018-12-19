package com.ck.JavaWebExampleTest.Java300_questions.learn_20180809;

import java.util.Arrays;

/*
* Java-52问起
* */
public class question0809001 {
    public static void main(String[] args) {
       /*
       * *//*使用Arrays类进行数组的赋值操作*//*
        int[] src = new int[]{1, 2, 3, 4, 5, 6, 123, 343, 23};
        int[] dest = new int[10];
        arrCopyTest(src,dest);*/

      /*
      * *//*使用Arrays类进行数组元素的查找[1、使用前需要排序]*//*
        int [] src = new int[]{1,33,21,45,12,23,34,46,13,34,567,123};
        arrSeach(src,23);*/
    }
    private static void arrCopyTest(int[] src,int[] dest){
        /*使用Arrays类进行数组的赋值操作*/
        dest = Arrays.copyOfRange(src,5,src.length);
        for (int a : dest){
            System.out.print(a+"\t");
        }
    }
    private static void arrSeach(int[] src,int key){
        /*使用Arrays类进行数组元素的查找[1、使用前需要排序]*/
        Arrays.sort(src);
        System.out.println("排序后的数组");
        for (int a : src){
            System.out.print(a +"\t");
        }
        key = Arrays.binarySearch(src,key);
        if (key > 0){
            System.out.println("index:"+key);
        }else {
            System.out.println("search nothing!");
        }
    }
}
