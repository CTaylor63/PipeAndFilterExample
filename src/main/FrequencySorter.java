package main;

import java.util.Comparator;

/**
 * @author Connor Taylor
 *
 * FrequencySorter.java
 *
 * Comparator class for sorting words by their frequency, and then by name if there is a tie
 *
 */
public class FrequencySorter implements Comparator<Pair> {

    @Override
    public int compare(Pair o1, Pair o2) {
        int compareVal =  o2.getFrequency().compareTo(o1.getFrequency());
        if (compareVal != 0){
            return compareVal;
        }
        return o1.getWord().compareTo(o2.getWord());
    }
}
