package com.ck.java_basic.sort_algorithm;
/*
* 插入排序
* */
public class sort_of_insert {

        final int MAX=20;
        int num[]=new int[MAX];
        {
            System.out.print("生成的随机数组是：");
            for(int i=0;i<20;i++){
                num[i]=(int)(Math.random()*100);
                System.out.print(num[i]+" ");
            }
            System.out.println();
        }


    /*-------------------------插入排序法--------------------------------
    像是玩朴克一样，我们将牌分作两堆，每次从后面一堆的牌抽出最前端的牌，然后插入前面一堆牌的适当位置
-----------------------------------------------------------------*/
        /***
         *
         * @param number 无序数组
         * MAX为为无序数据个数
         */
        public void insort(int number[]){
            int i, j, k, temp;
            long start,end;

            start=System.nanoTime();
            for(j = 1; j < MAX; j++) {
                temp = number[j];
                i = j - 1;
                while(temp < number[i]) {
                    number[i+1] = number[i];
                    i--;
                    if(i == -1){
                        break;
                    }
                }
                number[i+1] = temp;
            }
            end=System.nanoTime();

            System.out.println("-----------------插入排序法------------------");
            System.out.print("排序后是:");
            for(i=0;i<=MAX-1;i++){
                System.out.print(number[i]+" ");
            }
            System.out.println();
            System.out.println("排序使用时间："+(end-start)+" ns");
        }

        /**
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            sort_of_insert s = new sort_of_insert();
            s.insort(s.num.clone());                        //插入排序法
        }




}
