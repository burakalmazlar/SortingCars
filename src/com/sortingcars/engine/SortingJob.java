package com.sortingcars.engine;

import com.sortingcars.sorting.Sorting;
import com.sortingcars.threadpool.Job;

import java.util.LinkedList;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class SortingJob<T extends Comparable> implements Job {

    private LinkedList<T> sortList;
    private boolean executed = false;
    private Object jobLock = new Object();
    private long jobId;
    private Sorting sorting;
    private long elapsedTimeInMs;

    public SortingJob(LinkedList<T> sortList, long jobId, Sorting sorting) {
        this.sortList = sortList;
        this.jobId = jobId;
        this.sorting = sorting;
    }

    @Override
    public void execute() {
        synchronized (jobLock) {
            long start = System.nanoTime();
            sorting.sort(sortList);
            executed = true;
            // job execution finished notify getResult method to return the result
            jobLock.notify();
            long end = System.nanoTime();
            elapsedTimeInMs = MILLISECONDS.convert(end-start, NANOSECONDS);
        }
    }

    public LinkedList<T> getResult() {

        synchronized (jobLock) {
            if (executed) {
                // job execution is already finished
                // return the result
                return sortList;
            }
            try {
                // job execution not yet finished
                // waiting to be notified from job execute method
                jobLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return sortList;
        }
    }

    public long getJobId() {
        return jobId;
    }

    public long getElapsedTimeInMs() {
        return elapsedTimeInMs;
    }
}
