package main;

/**
 * @author Connor Taylor
 *
 * Driver.java
 *
 * This class is designed to run as a text parsing application
 * Users are able to parse various files to determine the most frequently occuring words in the file
 * Users have the ability to chose the following:
 *      which file to parse
 *      which file reader to use
 *      which stemmer to use
 *
 * This class contains a main(), and is intended to be the launch point of the application
 *
 */

import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

public class Driver {

    /*
        Determines the type of file reader to be used
        Should be modified when adding new types of file readers
     */
    /*
    public Filter getFileReader(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a number to choose the type of file reader to use:\n" +
                    "   1) Text File Reader\n");

            int file = scanner.nextInt();

            switch (file) {
                case 1:
                    return new FileReaderFilter();
                default:
                    System.out.println("Unable to read input. Input should be a number");
                    break;
            }
        }

    }

    /*
        Determines the type of stemmer to be used
        Should be modified when adding new types of stemmers
     */
    /*
    public Filter getStemmer(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a number to choose the type of stemmer to use:\n" +
                    "   1) Porter Stemmer (English)\n");

            int file = scanner.nextInt();

            switch (file) {
                case 1:
                    return new PorterStemmerFilter();
                default:
                    System.out.println("Unable to read input. Input should be a number");
                    break;
            }
        }

    }

    /*
        Determines the type of frequency counter to be used
        Should be modified when adding new types of frequency counters
     */
    /*
    public Filter getFrequencyCounter(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a number to choose the type of frequency counter to use:\n" +
                    "   1) Basic Frequency Counter\n");

            int file = scanner.nextInt();

            switch (file) {
                case 1:
                    return new FrequencyCounter();
                default:
                    System.out.println("Unable to read input. Input should be a number");
                    break;
            }
        }

    }

    /*
        Determines the name of the file to read
        Should be modified when changing which files can be read
    */
    public String getFileName(){
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter a number to choose a file to parse:\n" +
                    "   1) U.S. Declaration of Independence\n" +
                    "   2) Alice In Wonderland\n" +
                    "   3) KJV Bible\n");

            int file = scanner.nextInt();

            switch (file) {
                case 1:
                    return "usdeclar.txt";
                case 2:
                    return "alice30.txt";
                case 3:
                    return "kjbible.txt";
                default:
                    System.out.println("Unable to read input. Input should be a number");
                    break;
            }
        }

    }


    public void begin(){

        //Build tons of pipes
        Pipe p1 = new Pipe();
        Pipe p2 = new Pipe();
        Pipe p3 = new Pipe();
        Pipe p4 = new Pipe();
        Pipe p5 = new Pipe();
        Pipe p6 = new Pipe();

        String filename = getFileName();
        System.out.println("You've selected " + filename);
        p1.in(filename);
        p1.in(Filter.POISON_PILL);

        FileReaderFilter fileReaderFilter = new FileReaderFilter(p1,p2);
        EmptyStringFilter emptyStringFilter = new EmptyStringFilter(p2,p3);
        NonAlphaFilter nonAlphaFilter = new NonAlphaFilter(p3,p4);
        StopWordFilter stopWordFilter = new StopWordFilter(p4,p5);
        PorterStemmerFilter porterStemmerFilter = new PorterStemmerFilter(p5,p6);
        FrequencyCounter frequencyCounter = new FrequencyCounter(p6,null);


        stopWordFilter.setUp("stopwords.txt");

        Instant start = Instant.now();
        fileReaderFilter.run();
        emptyStringFilter.run();
        nonAlphaFilter.run();
        stopWordFilter.run();
        porterStemmerFilter.run();
        frequencyCounter.run();
        Instant end = Instant.now();

        System.out.println();
        System.out.println(String.format("FileFilter times - avg: %d total: %d",fileReaderFilter.getAvgTime(),fileReaderFilter.getTotalTime()));
        System.out.println(String.format("EmptyFilter times - avg: %d total: %d",emptyStringFilter.getAvgTime(),emptyStringFilter.getTotalTime()));
        System.out.println(String.format("NonAlphaFilter times - avg: %d total: %d",nonAlphaFilter.getAvgTime(),nonAlphaFilter.getTotalTime()));
        System.out.println(String.format("StopWordFilter times - avg: %d total: %d",stopWordFilter.getAvgTime(),stopWordFilter.getTotalTime()));
        System.out.println(String.format("PorterFilter times - avg: %d total: %d",porterStemmerFilter.getAvgTime(),porterStemmerFilter.getTotalTime()));
        System.out.println(String.format("FreqFilter times - avg: %d total: %d",frequencyCounter.getAvgTime(),frequencyCounter.getTotalTime()));

        System.out.println("Total time for program: " + Duration.between(start, end).toMillis());


    }

    public void beginImproved(){

        //Build tons of pipes
        Pipe p1 = new Pipe();
        Pipe p2 = new Pipe();
        Pipe p3 = new Pipe();
        Pipe p4 = new Pipe();
        Pipe p5 = new Pipe();
        Pipe p6 = new Pipe();

        String filename = getFileName();
        System.out.println("You've selected " + filename);
        p1.in(filename);
        p1.in(Filter.POISON_PILL);

        FileReaderFilter fileReaderFilter = new FileReaderFilter(p1,p2);
        EmptyStringFilter emptyStringFilter = new EmptyStringFilter(p2,p3);
        NonAlphaFilter nonAlphaFilter = new NonAlphaFilter(p3,p4);
        StopWordFilter stopWordFilter = new StopWordFilter(p4,p5);
        PorterStemmerFilter porterStemmerFilter = new PorterStemmerFilter(p5,p6);
        FrequencyCounter frequencyCounter = new FrequencyCounter(p6,null);


        //Add threads to improve efficiency
        NonAlphaFilter nonAlphaFilter1 = new NonAlphaFilter(p3,p4);
        NonAlphaFilter nonAlphaFilter2 = new NonAlphaFilter(p3,p4);
        NonAlphaFilter nonAlphaFilter3 = new NonAlphaFilter(p3,p4);

        //Add threads to improve efficiency
        StopWordFilter stopWordFilter1 = new StopWordFilter(p4,p5);
        StopWordFilter stopWordFilter2 = new StopWordFilter(p4,p5);
        StopWordFilter stopWordFilter3 = new StopWordFilter(p4,p5);
        stopWordFilter1.setUp("stopwords.txt");
        stopWordFilter2.setUp("stopwords.txt");
        stopWordFilter3.setUp("stopwords.txt");


        stopWordFilter.setUp("stopwords.txt");

        Instant start = Instant.now();
        fileReaderFilter.run();
        emptyStringFilter.run();
        //nonAlphaFilter.run();

        new Thread(nonAlphaFilter1).start();
        new Thread(nonAlphaFilter2).start();
        new Thread(nonAlphaFilter3).start();


        //stopWordFilter.run();

        new Thread(stopWordFilter1).start();
        new Thread(stopWordFilter2).start();
        new Thread(stopWordFilter3).start();


        porterStemmerFilter.run();
        frequencyCounter.run();
        Instant end = Instant.now();

        System.out.println();
        System.out.println(String.format("FileFilter times - avg: %d total: %d",fileReaderFilter.getAvgTime(),fileReaderFilter.getTotalTime()));
        System.out.println(String.format("EmptyFilter times - avg: %d total: %d",emptyStringFilter.getAvgTime(),emptyStringFilter.getTotalTime()));
        //System.out.println(String.format("NonAlphaFilter times - avg: %d total: %d",nonAlphaFilter.getAvgTime(),nonAlphaFilter.getTotalTime()));

        //Print out timing from threads
        System.out.println(String.format("NonAlphaFilter1 times - avg: %d total: %d",nonAlphaFilter1.getAvgTime(),nonAlphaFilter1.getTotalTime()));
        System.out.println(String.format("NonAlphaFilter2 times - avg: %d total: %d",nonAlphaFilter1.getAvgTime(),nonAlphaFilter1.getTotalTime()));
        System.out.println(String.format("NonAlphaFilter3 times - avg: %d total: %d",nonAlphaFilter1.getAvgTime(),nonAlphaFilter1.getTotalTime()));


        //System.out.println(String.format("StopWordFilter times - avg: %d total: %d",stopWordFilter.getAvgTime(),stopWordFilter.getTotalTime()));

        //Print out timing from threads
        System.out.println(String.format("StopWordFilter1 times - avg: %d total: %d",stopWordFilter1.getAvgTime(),stopWordFilter1.getTotalTime()));
        System.out.println(String.format("StopWordFilter2 times - avg: %d total: %d",stopWordFilter2.getAvgTime(),stopWordFilter2.getTotalTime()));
        System.out.println(String.format("StopWordFilter3 times - avg: %d total: %d",stopWordFilter3.getAvgTime(),stopWordFilter3.getTotalTime()));

        System.out.println(String.format("PorterFilter times - avg: %d total: %d",porterStemmerFilter.getAvgTime(),porterStemmerFilter.getTotalTime()));
        System.out.println(String.format("FreqFilter times - avg: %d total: %d",frequencyCounter.getAvgTime(),frequencyCounter.getTotalTime()));

        System.out.println("Total time for program: " + Duration.between(start, end).toMillis());

    }

    /*
        Run on execution of program
        Asks for the type of file reader, the type of stemmer, and which list of stop words to use
        Sets the appropriate components in the driver before beginning the parsing and analysis
     */
    public static void main(String[] args){

        Driver driver = new Driver();

        //Uncomment and comment these to pick the version of the program to run
        //driver.begin();
        driver.beginImproved();

    }

}
