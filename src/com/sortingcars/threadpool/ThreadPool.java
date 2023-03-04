package com.sortingcars.threadpool;

import java.util.Iterator;
import java.util.LinkedList;

public class ThreadPool {

    private LinkedList<ManagedThread> managedThreads;
    private final int maxTreadCount;
    private Object threadPoolLock = new Object();

    public ThreadPool(int maxThreadCount) {
        this.managedThreads = new LinkedList<ManagedThread>();
        this.maxTreadCount = maxThreadCount;
    }

    public ManagedThread getAvailableThread() {
        synchronized (threadPoolLock) {
            ManagedThread thread;
            if (managedThreads.size() < maxTreadCount) {
                // creating new threads for pool until size reaches to max tread count
                thread = new ManagedThread(this);
                managedThreads.add(thread);
            } else {
                while (true) {
                    // check for any available thread exists for job execution
                    for (ManagedThread managedThread : managedThreads) {
                        if (managedThread.isAvailable()) {
                            return managedThread;
                        }
                    }
                    try {
                        // available threads needed but pool size reached
                        // so waiting for any thread to finish its job and notify the pool
                        threadPoolLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return thread;
        }
    }

    // informing pool a thread is now available to get another job
    public void managedThreadIsAvailable() {
        synchronized (threadPoolLock) {
            threadPoolLock.notify();
        }
    }

    public void shutdown(){
        for (ManagedThread managedThread : managedThreads) {
            managedThread.terminate();
        }
    }

    public int getMaxTreadCount(){
        return maxTreadCount;
    }

}
