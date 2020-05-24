package main;

/**
 * @author Connor Taylor
 *
 * Filter.java
 *
 * A super class for all filters
 * Ensures that filters have an in-pipe and an out-pipe
 * Includes a poison pill for stopping threads when all data has been processed
 *
 */
public class Filter {

    public static final String POISON_PILL = new String("Poison");

    Pipe inPipe;
    Pipe outPipe;

    public Filter(Pipe in, Pipe out){
        inPipe = in;
        outPipe = out;
    }

    public String getData(){
        return inPipe.out();
    }

    public void sendData( String toSend ){
        outPipe.in(toSend);
    }

}
