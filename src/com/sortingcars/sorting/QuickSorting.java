package com.sortingcars.sorting;

import java.util.Collections;
import java.util.List;

public class QuickSorting<T extends Comparable<T>> implements Sorting<T> {

    @Override
    public void sort(List<T> list) {

        Collections.sort(list);

    }
}
