package org.example.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * 自定义线程池
 */
public class CustomThreadPool {
    // 线程池中线程的数量为5
    private final int numThreads = 5;
    private final List<WorkerThread> threads;
    private final LinkedList<Runnable> taskQueue;

    public CustomThreadPool(int numThreads) {
        this.threads = new LinkedList<>();
        this.taskQueue = new LinkedList<>();
        initializeThreads(numThreads);
    }

    private void initializeThreads(int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            WorkerThread thread = new WorkerThread();
            thread.start();
            threads.add(thread);
        }
    }

    public void submit(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.addLast(task);
            taskQueue.notify(); // 唤醒等待的线程
        }
    }

    private class WorkerThread extends Thread {
        public void run() {
            Runnable task;
            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait(); // 等待任务
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                    task = taskQueue.removeFirst(); // 取出任务
                }
                try {
                    task.run(); // 执行任务
                } catch (RuntimeException e) {
                    // 处理任务执行异常
                    e.printStackTrace();
                }
            }
        }
    }
}
