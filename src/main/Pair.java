package main;

/**
 * @author Connor Taylor
 *
 * Pair.java
 *
 * A utility class for determining frequencies of words
 *
 */
public class Pair {

    private String word;
    private Integer frequency;

    public Pair(String word, Integer frequency){
        this.word = word;
        this.frequency = frequency;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public String getWord() {
        return word;
    }
}
