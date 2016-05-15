package com.ahao.wnacgnet.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Avalon on 2016/5/13.
 */
public class DefaultThreadPool {
    //阻塞队列最大任务数量
    final static int BLOCKING_QUEUE_SIZE = 20;
    //线程池最大任务数量
    final static int THREAD_POOL_MAX_SIZE = 10;
    //线程池当前任务数量
    final static int THREAD_POOL_SIZE = 6;

    //阻塞队列
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(THREAD_POOL_SIZE);
    //线程池
    static AbstractExecutorService pool = new ThreadPoolExecutor(THREAD_POOL_SIZE, THREAD_POOL_MAX_SIZE,
            15L, TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.DiscardOldestPolicy());


    //单例模式
    private static DefaultThreadPool instance = null;
    private DefaultThreadPool(){}


    public static synchronized DefaultThreadPool getInstance(){
        if(DefaultThreadPool.instance == null) {
            DefaultThreadPool.instance = new DefaultThreadPool();
        }
        return DefaultThreadPool.instance;
    }

    public static void removeAllTask(){
        DefaultThreadPool.blockingQueue.clear();
    }

    public static void removeTaskFromQueue(final Object obj){
        DefaultThreadPool.blockingQueue.remove(obj);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightnow() {
        if (DefaultThreadPool.pool != null) {
            DefaultThreadPool.pool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                DefaultThreadPool.pool.awaitTermination(1,
                        TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行任务
     *
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                DefaultThreadPool.pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

}

