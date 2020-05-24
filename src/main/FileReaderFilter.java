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
 * TestFileReader.java
 *
 * This class is designed to read text from a .txt file into a single string
 * The string is converted to all lower case before moving on
 *
 */
public class FileReaderFilter extends Filter implements Runnable {

    private List<Long> times = new ArrayList<>();
    private Long totalTime;

    public FileReaderFilter(Pipe in, Pipe out) {
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
        return (sum / times.size());
    }

    @Override
    public void run() {
        //System.out.println("FileReaderFilter started");
        Instant totalStart = Instant.now();

        while(true) {

            String filename = getData();

            if (filename != Filter.POISON_PILL) {

                if (filename != null) {
                    Instant actionStart = Instant.now();
                    String in = null;

                    //Read in from a file, throw an error if the file cannot be read
                    try {
                        //in = new String(Files.readAllBytes(Paths.get("src/resources/"+filename)));
                        //in = in.toLowerCase();

                        BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)));
                        String line;

                        while ((line = reader.readLine()) != null){
                            sendData(line.toLowerCase());
                        }
                        Instant actionEnd = Instant.now();
                        times.add(Duration.between(actionStart,actionEnd).toMillis());
                        /*
                        try {
                            lines = Files.readAllLines(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

                            for (String line : lines){
                                sendData(line.toLowerCase());
                            }
                        } catch (URISyntaxException ue) {
                                System.out.println("Cannot read file");
                                ue.printStackTrace();
                        }*/
                        //sendData(in);
                    } catch (IOException fe) {
                        System.out.println("Unable to read from file: " + filename);
                        fe.printStackTrace();
                    }
                } else continue;
            } else break;

        }
        sendData(Filter.POISON_PILL);
        Instant totalEnd = Instant.now();
        totalTime = Duration.between(totalStart, totalEnd).toMillis();
        System.out.println("FileReaderFilter finished");
    }
}
