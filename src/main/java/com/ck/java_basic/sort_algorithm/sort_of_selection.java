package com.ck.java_basic.sort_algorithm;


/*
*
* 选择排序
* */
public class sort_of_selection {
        final int MAX=20;
        int num[]=new int[MAX];
        {
            System.out.print("排序前生成的随机数组是：");
            for(int i=0;i<20;i++){
                num[i]=(int)(Math.random()*100);
                System.out.print(num[i]+" ");
            }
            System.out.println();
        }


        public void selsort(int number[]) {
            int i, j, k, m, temp;
            long start,end;

            start=System.nanoTime();
            for(i = 0; i < MAX-1; i++) {
                m = i;
                for(j = i+1; j < MAX; j++){
                    if(number[j] < number[m]){
                        m = j;
                    }
                }
                if( i != m){
                    temp=number[i];
                    number[i]=number[m];
                    number[m]=temp;
                }
            }
            end=System.nanoTime();

            System.out.println("-----------------选择排序法------------------");
            System.out.print("排序后是:");
            for(i=0;i<=MAX-1;i++){
                System.out.print(number[i]+" ");
            }
            System.out.println();
            System.out.println("排序使用时间："+(end-start)+" ns");
        }


        /**
         * @param args
         */
        public static void main(String[] args) {
            // TODO Auto-generated method stub
            sort_of_selection s = new sort_of_selection();
            s.selsort(s.num.clone());                        //选择排序法
        }
}
