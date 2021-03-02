package com.company;

import java.util.Comparator;

/**
 * Comparator class that allows Scores to be compared to each other in Collections.Sort()
 */
public class ScoreComparator implements Comparator<Score> {

    /**
     * Method that compares two Score objects to determine which one is better. The lower the number, the better Score.
     * @param s1
     * @param s2
     * @return
     */
    public int compare(Score s1, Score s2) {
        int compare = 0;
        if(s1.getScore() > s2.getScore()) {
            compare = 1;
        } else if(s1.getScore() < s2.getScore()) {
            compare = -1;
        } else {
            compare = 0;
        }
        return compare;
    }
}
