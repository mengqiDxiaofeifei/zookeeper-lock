package com.sparksys.mall.core.utils;

import java.util.concurrent.*;

/**
 * 中文类名：线程池-静态内部类方式
 * 概要说明：
 *
 * @author: zhouxinlei
 * @date: 2019/6/21
 */
public class ThreadPoolExecutorUtils {

    private ThreadPoolExecutorUtils() {
        System.out.println("初始化线程池--");
    }

    public static void execute(Runnable runnable) {
        SingleThreadPoolExecutorInstance.threadPoolExecutor.execute(runnable);
    }


    private static class SingleThreadPoolExecutorInstance {
        private static int corePoolSize = Runtime.getRuntime().availableProcessors();
        private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                corePoolSize, corePoolSize + 1, 30,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
                Executors.defaultThreadFactory(),
                new CustomRejectedExecutionHandler()
        );
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            try {
                //由blockingqueue的offer改成put阻塞方法
                executor.getQueue().put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutorUtils.execute(() -> {
            System.out.println("哈哈");
        });
    }
}
