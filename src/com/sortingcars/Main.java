package com.sortingcars;

import com.sortingcars.car.Car;
import com.sortingcars.car.Color;
import com.sortingcars.sorting.QuickSortingLinkedList;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Main {

    public static final String FS = System.getProperty("file.separator");
    public static final String LS = System.getProperty("line.separator");

    public static void main(String[] args) {
        int numberOfLists = 10;
        long numberOfCars = 1_000;
        int maxThread = 5;
        if (args.length == 3) {
            numberOfLists = Integer.parseInt(args[0]);
            numberOfCars = Long.parseLong(args[1]);
            maxThread = Integer.parseInt(args[2]);
        }

        QuickSortingLinkedList<Car>[] lists = new QuickSortingLinkedList[numberOfLists];
        for (int i = 0; i < lists.length; i++) {
            lists[i] = generate(numberOfCars, i);
        }
        System.out.println("Car lists are ready.");

        new QuickCarSorting().run(lists, maxThread);

    }

    private static QuickSortingLinkedList<Car> generate(long size, int i) {

        QuickSortingLinkedList<Car> list = new QuickSortingLinkedList<>();
        for (long id = 0; id < size; id++) {
            list.add(new Car(id, UUID.randomUUID().toString(),
                    Color.fromOrdinal(ThreadLocalRandom.current().nextInt(4)),
                    Car.DESTINATIONS[ThreadLocalRandom.current().nextInt(5)]));
        }

        writeToFile(list, "cars." + i + ".in.txt");

        return list;

    }

    public static <T extends Object> void writeToFile(QuickSortingLinkedList list, String fileName) {
        try {
            String folder = "." + FS + "files" + FS;
            Path path = Paths.get(folder);
            if (!Files.isDirectory(path)) {
                Files.createDirectories(path);
            }
            StringBuilder lines = new StringBuilder();
            for (Object o : list) {
                lines.append(o.toString() + LS);
            }
            Files.write(Paths.get(folder + fileName), lines.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
