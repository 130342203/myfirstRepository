package com.ck.java_basic.dataStructure.chap4_stackAndQuenue;

/*
* 队列
* */
public class Quenue {
    private int maxSize;
    private long[] quenueArr;
    private int front;
    private int rear;
    private int realSize;

    public Quenue(int s){
        this.maxSize = s;
        this.quenueArr = new  long[maxSize];
        this.rear = -1;
        this.front = 0;
        this.realSize = 0;
    }

    public void insert(long s ){
        if (isFull()){
            throw new ArrayIndexOutOfBoundsException("quenue is full,do not allow insert");
        }
        if (rear == maxSize -1){
            rear = -1;
        }
        quenueArr[++rear] = s;
        realSize ++;
    }
    public long remove(){
        if (isEmpty()){
            throw new ArrayIndexOutOfBoundsException("quenue is empty,there is no any item for remove");
        }
        long tmp = quenueArr[front++];
        if (front == maxSize){
            front = 0;
        }
        realSize--;
        return tmp;
    }
    public long peekFront(){
        return quenueArr[front];
    }
    public boolean isEmpty(){
        return realSize == 0;
    }
    public boolean isFull(){
        return realSize == maxSize;
    }
    public int size(){
        return realSize;
    }
}
