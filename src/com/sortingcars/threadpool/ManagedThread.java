package com.sortingcars.threadpool;

public class ManagedThread extends Thread {

    private Job job;
    private Object managedThreadLock = new Object();
    private boolean available = true;
    private boolean alive = false;
    private ThreadPool pool;

    public ManagedThread(ThreadPool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        synchronized (managedThreadLock) {
            while (alive) {
                job.execute();
                available = true;
                pool.managedThreadIsAvailable();
                // thread is executed its task and waiting for another or termination
                try {
                    managedThreadLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void executeJob(Job job) {
        synchronized (managedThreadLock) {
            available = false;
            this.job = job;
            if (alive) {
                // notify waiting thread to do incoming job
                managedThreadLock.notify();
            } else {
                // thread accepts first job
                alive = true;
                this.start();
            }
        }
    }

    public boolean isAvailable() {
        return available;
    }

    public void terminate() {
        synchronized (managedThreadLock) {
            alive = false;
            // all jobs are done marked as dead and notified for termination
            managedThreadLock.notify();
        }
        try {
            // if this thread did not finish its execution
            // join to main thread to prevent interruption before finish
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
