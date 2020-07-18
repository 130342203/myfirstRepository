package com.ck.java_basic.dataStructure.chap4_stackAndQuenue;

public class QuenueApp
{
    public static void main(String[] args) {
        Quenue quenue = new Quenue(5);
        quenue.insert(1);
        quenue.insert(2);
        quenue.insert(3);
        quenue.insert(4);

        quenue.remove();
        quenue.remove();
        quenue.remove();

        quenue.insert(5);
        quenue.insert(6);
        quenue.insert(7);
        quenue.insert(8);
        quenue.insert(9);

        while (!quenue.isEmpty()){
            long n = quenue.remove();
            System.out.print(n);
            System.out.print(" ");
        }
        System.out.println(" ");

    }
}
