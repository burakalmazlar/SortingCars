package com.sortingcars.engine;

import com.sortingcars.Main;
import com.sortingcars.QuickCarSorting;
import com.sortingcars.sorting.Sorting;
import com.sortingcars.threadpool.Job;

import java.util.LinkedList;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class SortingJob<T extends Comparable<T>> implements Job {

    private Object jobLock = new Object();

    private LinkedList<T> list;

    private boolean executed = false;
    private final long id;

    private final Sorting sorting;

    public SortingJob(LinkedList<T> list, long id, Sorting sorting) {
        this.list = list;
        this.id = id;
        this.sorting = sorting;
    }

    @Override
    public void execute() {
        synchronized (jobLock) {
            long start = nanoTime();
            sorting.sort(list);
            long elapsedTime = MILLISECONDS.convert(nanoTime() - start, NANOSECONDS);
            System.out.println("Job " + id + " executed in " + elapsedTime + " ms by " + Thread.currentThread().getName());
            String fileName = "cars-" + id + "-sorted.txt";
            Main.writeToFile(list, fileName);
            executed = true;
            // job execution finished
            // inform getResult method to return the result
            jobLock.notify();
        }
    }

    public LinkedList<T> getResult() {

        synchronized (jobLock) {
            if (executed) {
                // job execution is already finished
                // return the result
                return list;
            }
            try {
                // job execution not yet finished
                // waiting to be notified from job execute method
                jobLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return list;
        }
    }

    public long getId() {
        return id;
    }

}
