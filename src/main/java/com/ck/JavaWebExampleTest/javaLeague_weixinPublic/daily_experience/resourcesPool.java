package com.ck.JavaWebExampleTest.javaLeague_weixinPublic.daily_experience;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 资源池管理对象<br>
 * {@link #apply()},{@link #free()}用于申请/释放资源,申请的资源对象不可跨线程调用,<br>
 * 通过重写{@link #isNestable()}方法决定是否允许嵌套调用
 * @author guyadong
 *
 * @param <R> 资源类型
 */
public class resourcesPool<R> {
    //apply()
    // 从资源队列中申请资源，若队列为空，则线程阻塞，否则就从队列头部取出一个对象，保存在TLS变量中
    //free()
    //归还资源对象，将TLS中保存的资源重新加入quenue尾部

    /** 资源队列 */
    protected final LinkedBlockingQueue<R> queue = new LinkedBlockingQueue<R>();
    /** 当前线程申请的资源对象 */
    private final ThreadLocal<R> tlsResource = new ThreadLocal<R>();
    /** 线程嵌套计数 */
    private final ThreadLocal<AtomicInteger> threadNestCount= new ThreadLocal<AtomicInteger>();
    private final boolean nestable = isNestable();
    protected resourcesPool(){}
    /**
     * 构造方法
     * @param resources 资源对象集合
     * @throws IllegalArgumentException 包含{@code null}元素
     */
    public resourcesPool(Collection<R> resources){
        for(R r:resources){
            if(null == r){
                throw new IllegalArgumentException("resources contains null element");
            }
            queue.add(r);//add()方法：增加一个元素，如果队列已满则报异常
        }
    }
    @SafeVarargs
    public resourcesPool( R ...resources ){
        this(Arrays.asList(resources));
    }
    /**
     * 从资源队列{@link #queue}中取出一个对象,保存到{@link #tlsResource}
     * @return
     * @throws InterruptedException
     */
    private R getResource() throws InterruptedException{
        if(null != tlsResource.get()){
            // 资源状态异常
            throw new IllegalStateException("INVALID tlsResource state");
        }
        R r;
        if(queue.isEmpty() && null != (r = newResource())){
            queue.offer(r);//offer()方法：添加一个元素并返回true如果队列已满，则返回false
        }
        r = open(queue.take());//take()方法，移除并返回队列头部的元素，如果队列为空，则阻塞
        tlsResource.set(r);
        return r;
    }
    /**
     * 将{@link #tlsResource}中的资源对象重新加入资源队列{@link #queue},并清除TLS变量{@link #tlsResource}
     */
    private void recycleResource(){
        R r = tlsResource.get();
        if(null == r){
            // 资源状态异常
            throw new IllegalStateException("INVALID tlsResource while recycle");
        }
        // 放回队例
        queue.offer(close(r));
        tlsResource.remove();
    }
    /**
     * (阻塞式)申请当前线程使用的资源对象,不可跨线程使用
     * @return
     * @throws InterruptedException
     */
    public final R applyChecked() throws InterruptedException{
        if(nestable){
            AtomicInteger count = threadNestCount.get();
            if(null == count){
                // 当前线程第一次申请资源
                count = new AtomicInteger(1);
                threadNestCount.set(count);
                return getResource();
            }else{
                // 嵌套调用时直接返回TLS变量
                if(null == this.tlsResource.get()){
                    // 资源状态异常
                    throw new IllegalStateException("INVALID tlsResource");
                }
                count.incrementAndGet();
                return this.tlsResource.get();
            }
        }else{
            return getResource();
        }
    }
    /**
     * (阻塞式)申请当前线程使用的资源对象,不可跨线程使用<br>
     * {@link InterruptedException}封装到{@link RuntimeException}抛出
     * @return
     * @see #applyChecked()
     */
    public final R apply(){
        try {
            return applyChecked();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 释放当前线程占用的资源对象，放回资源队列
     */
    public final void free(){
        if(nestable){
            AtomicInteger count = threadNestCount.get();
            if(null == count){
                // 申请/释放没有成对调用
                throw new IllegalStateException("INVALID nestCount");
            }
            if( 0 == count.decrementAndGet()){
                threadNestCount.remove();
                recycleResource();
            }
        }else{
            recycleResource();
        }
    }
    /** 是否允许嵌套 */
    protected boolean isNestable() {
        return false;
    }
    /**
     * 创建一个新的资源对象
     * @return
     */
    protected R newResource(){
        return null;
    }
    /**
     * 资源从队形从取出时调用,子类可重写此方法
     * @param resource
     * @return 返回 {@code resource
     */
    protected R open(R resource){
        return resource;
    }
    /**
     * 资源对象放回队列时调用,子类可重写此方法
     * @param resource
     * @return 返回 {@code resource}
     */
    protected R close(R resource){
        return resource;
    }
}
