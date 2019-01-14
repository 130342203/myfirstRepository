package com.ck.java_basic.sort_algorithm;

/*
 * 冒泡排序
 * */
public class sort_of_bubble {

    final int MAX = 20;
    int num[] = new int[MAX];

    {
        System.out.print("生成的随机数组是：");
        for (int i = 0; i < 20; i++) {
            num[i] = (int) (Math.random() * 100);
            System.out.print(num[i] + " ");
        }
        System.out.println();
    }

    /****-----------------------------------------冒泡排序法----------------------------------------
     顾名思义，就是排序时，最大的元素会如同气泡一样移至右端，其利用比较相邻元素的方法，将大的元素交换至右端，
     所以大的元素会不断的往右移动，直到适当的位置为止。 V型知识库 www.vxzsk.com
     基本的气泡排序法可以利用旗标的方式稍微减少一些比较的时间，当寻访完阵列后都没有发生任何的交换动作，
     表示排序已经完成，而无需再进行之后的回圈比较与交换动作。
     ----------------------------------------------------------------------------------------*****/
    public void bubsort(int number[]) {
        int i, j, k, temp, flag = 1;
        long start, end;

        start = System.nanoTime();
        for (i = 0; i < MAX - 1 && flag == 1; i++) {
            flag = 0;
            for (j = 0; j < MAX - i - 1; j++) {
                if (number[j + 1] < number[j]) {
                    temp = number[j + 1];
                    number[j + 1] = number[j];
                    number[j] = temp;
                    flag = 1;
                }
            }
        }
        end = System.nanoTime();

        System.out.println("-----------------冒泡排序法------------------");
        System.out.print("排序后是:");
        for (i = 0; i <= MAX - 1; i++) {
            System.out.print(number[i] + " ");
        }
        System.out.println();
        System.out.println("排序使用时间：" + (end - start) + " ns");
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        sort_of_bubble s = new sort_of_bubble();
        s.bubsort(s.num.clone());          //冒泡排序
    }

}
