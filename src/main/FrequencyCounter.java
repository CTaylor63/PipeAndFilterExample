package main;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrequencyCounter extends Filter implements Runnable{

    HashMap<String,Integer> frequencies = new HashMap();
    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public FrequencyCounter(Pipe in, Pipe out) {
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


    private List<Pair> getTopFrequencies(){
        List<Pair> words = new ArrayList<>();
        for (String key : frequencies.keySet()){
            words.add(new Pair(key,frequencies.get(key)));
        }
        words.sort(new FrequencySorter());
        return words;
    }

    private void printOutResults(List<Pair> top){
        System.out.println("\nTop words for this file:");
        for (int i = 0; i < 10; i++){
            Pair candidate = top.get(i);
            System.out.println(String.format("\t %d) %s : %d",i,candidate.getWord(),candidate.getFrequency()));
        }
    }

    @Override
    public void run() {
        //System.out.println("FrequencyCounter started");

        Instant totalStart = Instant.now();

        //Add words to the frequency map
        while (true) {

            String word = getData();

            if (word != Filter.POISON_PILL) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    if (frequencies.containsKey(word)) {
                        frequencies.put(word, frequencies.get(word) + 1);
                    } else {
                        frequencies.put(word,1);
                    }
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        //System.out.println("FrequencyCounter finished");

        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();

        //Determine frequencies
        List<Pair> top = getTopFrequencies();
        printOutResults(top);
    }
}
