package org.example;

import java.util.ArrayList;
import java.util.List;

public class Convert {
    public static <T> List<T> arrayToList(T[] arr) {
        List<T> list = new ArrayList<>();

        for (T i: arr) {
            list.add(i);
        }

        return list;
    }
}
