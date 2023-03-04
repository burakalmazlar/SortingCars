package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.engine.SortingEngine;
import com.sortingcars.engine.SortingJob;
import com.sortingcars.sorting.QuickSorting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class QuickCarSorting {

    public void run(LinkedList<Car>[] lists, int maxThread) {
        long start = nanoTime();

        SortingEngine sortingEngine = new SortingEngine(maxThread, new QuickSorting());

        for (int i = 0; i < lists.length; i++) {
            LinkedList<Car> carList = lists[i];
            writeToFile(carList, "cars."+i+".in.txt");
            // submitting lists for sorting
            sortingEngine.sort(carList);
        }

        for (int i = 0; i < lists.length; i++) {
            // getting jobs from engine and the results
            SortingJob job = sortingEngine.poll();
            LinkedList result = job.getResult();
            String fileName = "cars." + job.getJobId() + ".out.txt";
            writeToFile(result, fileName);
            long elapsedTimeInMs = job.getElapsedTimeInMs();
            System.out.println(fileName + " -> " + elapsedTimeInMs + " ms");
        }

        sortingEngine.shutdownEngine();

        System.err.println("Total elapsed time with "+maxThread+" thread = " +
                MILLISECONDS.convert(nanoTime()-start, NANOSECONDS));

    }



    private void writeToFile(LinkedList<Car> carList, String fileName) {
        try {
            Files.createDirectories(Paths.get(".\\files\\"));

            Files.write(Paths.get(".\\files\\"+fileName), carList.stream().map(Car::toString).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
