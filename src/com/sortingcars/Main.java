package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.car.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static final String FS = System.getProperty("file.separator");

    public static void main(String[] args) {
        int numberOfLists = 100;
        long numberOfCars = 100_000;
        int maxThread = 30;
        if (args.length == 3) {
            numberOfLists = Integer.parseInt(args[0]);
            numberOfCars = Long.parseLong(args[1]);
            maxThread = Integer.parseInt(args[2]);
        }

        LinkedList<Car>[] lists = new LinkedList[numberOfLists];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = generate(numberOfCars, i);
        }

        System.out.println("Car lists are ready.");

        new QuickCarSorting().run(lists, maxThread);

    }

    private static LinkedList<Car> generate(long size, int i) {

        LinkedList<Car> carList = LongStream.range(0, size).mapToObj(recId ->
                        new Car(recId, UUID.randomUUID().toString(),
                                Color.fromOrdinal(ThreadLocalRandom.current().nextInt(4)),
                                Car.DESTINATIONS[ThreadLocalRandom.current().nextInt(5)]))
                .collect(LinkedList::new, (a, b) -> a.add(b), (a, b) -> a.addAll(b));

        writeToFile(carList, "cars." + i + ".in.txt");

        return carList;

    }

    public static <T extends Object> void writeToFile(LinkedList<T> list, String fileName) {
        try {
            String folder = "." + FS + "files" + FS;
            Path path = Paths.get(folder);
            if (!Files.isDirectory(path)) {
                Files.createDirectories(path);
            }
            Files.write(Paths.get(folder + fileName), list.stream().map(T::toString).collect(toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
