package com.sortingcars.threadpool;

import java.util.Iterator;
import java.util.LinkedList;

public class ThreadPool {

    private Object threadPoolLock = new Object();

    private LinkedList<ManagedThread> managedThreads;

    private final int maxTreadCount;

    public ThreadPool(int maxThreadCount) {
        this.managedThreads = new LinkedList<ManagedThread>();
        this.maxTreadCount = maxThreadCount;
    }

    public ManagedThread getAvailableThread() {
        synchronized (threadPoolLock) {
            ManagedThread thread;
            int existingThreadCount = managedThreads.size();
            if (existingThreadCount < maxTreadCount) {
                // creating new threads for pool until size reaches to max tread count
                thread = new ManagedThread(this, existingThreadCount + 1);
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
                        // there is no available threads
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

    public void managedThreadIsAvailable() {
        synchronized (threadPoolLock) {
            // a thread informed pool
            // notifying getAvailableThread method to return that available thread
            threadPoolLock.notify();
        }
    }

    public void shutdown(){
        for (ManagedThread managedThread : managedThreads) {
            managedThread.terminate();
        }
    }

}
