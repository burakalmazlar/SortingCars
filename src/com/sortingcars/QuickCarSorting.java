package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.engine.SortingEngine;
import com.sortingcars.engine.SortingJob;
import com.sortingcars.sorting.QuickSorting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class QuickCarSorting {

    public void run(LinkedList<Car>[] lists, int maxThread) {
        long start = nanoTime();

        SortingEngine<Car> sortingEngine = new SortingEngine<>(maxThread, new QuickSorting());

        for (LinkedList<Car> carList : lists) {
            sortingEngine.sort(carList);
        }

        sortingEngine.getAllResults();

        sortingEngine.shutdownEngine();

        System.err.println("Total elapsed time with "+maxThread+" thread = " +
                MILLISECONDS.convert(nanoTime()-start, NANOSECONDS));

    }





}
