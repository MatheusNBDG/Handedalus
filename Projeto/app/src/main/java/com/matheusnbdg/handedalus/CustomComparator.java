package com.matheusnbdg.handedalus;

import java.util.Comparator;

/**
 * Created by Mathe on 05/03/2017.
 */

public class CustomComparator implements Comparator<book> {
    @Override
    public int compare(book o1, book o2) {
        return compareTo(o1.days(),o2.days());
    }
    public static int compareTo(int o1, int o2){
        return Double.compare(o1, o2);
    }
}
