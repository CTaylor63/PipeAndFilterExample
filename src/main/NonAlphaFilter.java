package main;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Taylor
 *
 * NonAlphaFilter.java
 *
 * This class is designed to filter out all non-alphabetic characters from a string
 * Also removes new line and tab characters from a string
 *
 */
public class NonAlphaFilter extends Filter implements Runnable {

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public NonAlphaFilter(Pipe in, Pipe out) {
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
        //System.out.println("NonAlphaFilter started");
        Instant totalStart = Instant.now();

        while (true) {
            String unparsed = getData();

            if (unparsed != Filter.POISON_PILL) {
                if (unparsed != null) {
                    Instant actionStart = Instant.now();
                    String parsed = unparsed.replaceAll("[^a-zA-Z]", "");
                    parsed = parsed.replaceAll("[\\r\\n\\t]", "");
                    sendData(parsed);
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("NonAlphaFilter finished");
    }
}
