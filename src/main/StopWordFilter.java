package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Connor Taylor
 *
 * StopWordFilter.java
 *
 * This class is designed to filter out stop words from the words read from the file
 * Stop words are not passed down the pipe
 * Can call setup prior to run to preload the stopwords
 *
 */
public class StopWordFilter extends Filter implements Runnable {

    private List<String> stopWords = new ArrayList();

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public StopWordFilter(Pipe in, Pipe out) {
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

    //Build the list of stopwords
    //Can be called separate from run() to not interfere with run() operations
    public void setUp(String filename){

        //Read in from a file, throw an error if the file cannot be read
        try {
            //try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)));
                List<String> words = new ArrayList<>();
                String line;

                while ((line = reader.readLine()) != null){
                    words.add(line);
                }

                //List<String> words = Files.readAllLines(Paths.get(getClass().getClassLoader().getResourceAsStream(filename)));
                for (String word : words) {
                    String cleanedWord = word.replaceAll("[\\n\\t]", "");
                    stopWords.add(cleanedWord);
                }
                /*
            } catch (URISyntaxException ue){
                System.out.println("Cannot read file");
                ue.printStackTrace();
            }*/
        } catch (IOException fe) {
            System.out.println("Unable to read from stopwords file");
            fe.printStackTrace();
        }

        System.out.println("Stopwords filter setup complete");
    }

    //Remove stop words from the words pulled from the file
    @Override
    public void run() {
        //System.out.println("StopWordFilter started");

        //Prevents forgetting to set up, but delays the rest of the operations
        if (stopWords.isEmpty()){
            this.setUp("stopwords.txt");
        }

        Instant totalStart = Instant.now();

        while (true) {
            String word = getData();

            if (word != Filter.POISON_PILL) {
                if (word != null) {
                    Instant actionStart = Instant.now();
                    if (!stopWords.contains(word)) {
                        sendData(word);
                    }
                    Instant actionEnd = Instant.now();
                    times.add(Duration.between(actionStart,actionEnd).toNanos());
                } else continue;
            } else break;
        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("StopWordFilter finished");

    }
}
