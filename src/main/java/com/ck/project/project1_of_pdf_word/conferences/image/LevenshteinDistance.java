package com.ck.project.project1_of_pdf_word.conferences.image;

/**
 * Created by Administrator on 2018-6-11 0011.
 */
public class LevenshteinDistance {
        /*输入两个字符串，返回这两个字符串的编辑距离*/
        public static int getDistance(String strA, String strB){
            int distance=-1;
        /*输入参数合法性检查*/
            if(null==strA||null==strB||strA.isEmpty()||strB.isEmpty()){
                return distance;
            }
        /*两个字符串相等，编辑距离为0*/
            if (strA.equals(strB)) {
                return 0;
            }
            int lengthA=strA.length();
            int lengthB=strB.length();
            int length=Math.max(lengthA,lengthB);
        /*申请一个二维数组，存储转移矩阵*/
            int array[][]=new int[length+1][length+1];
        /*边界条件初始化*/
            for(int i=0;i<=length;i++){
                array[i][0]=i;

            }
        /*边界条件初始化*/
            for(int j=0;j<=length;j++){
                array[0][j]=j;
            }
        /*状态转移方程*/
            for(int i=1;i<=lengthA;i++){
                for(int j=1;j<=lengthB;j++){
                    array[i][j]=min(array[i-1][j]+1,
                            array[i][j-1]+1,
                            array[i-1][j-1]+(strA.charAt(i-1)==strB.charAt(j-1)?0:1));
                }
            }
            return array[lengthA][lengthB];

        }
        /*取三个数中的最小值*/
        public static int  min(int a,int b, int c){
            return Math.min(Math.min(a,b),c);
        }

}
