package main;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Taylor
 *
 * PorterStemmerFilter.java
 *
 * This class is designed to change words into their base forms
 *      ie. remove all tense changes
 * Utilizes the Stemmer.java class
 *
 */
public class PorterStemmerFilter extends Filter implements Runnable{

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public PorterStemmerFilter(Pipe in, Pipe out) {
        super(in, out);
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public long getAvgTime(){
        long sum = 0;
        for (Long time: times){
            sum += time;
        }
        return (sum / times.size()) / 100;
    }

    @Override
    public void run() {
        //System.out.println("PorterStemmerFilter started");
        Instant totalStart = Instant.now();

        while (true) {

            String word = getData();

            if (word != Filter.POISON_PILL) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    Stemmer stemmer = new Stemmer();
                    //System.out.println(word);
                    for (int i = 0; i < word.length(); i++){
                        stemmer.add(word.charAt(i));
                    }
                    stemmer.stem();
                    sendData(stemmer.toString());
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("PorterStemmerFilter finished");
    }
}
