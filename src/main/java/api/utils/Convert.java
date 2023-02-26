package api.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Convert {
    public static <T> List<T> arrayToList(T[] arr) {
        List<T> list = new ArrayList<>();

        Collections.addAll(list, arr);

        return list;
    }
}
