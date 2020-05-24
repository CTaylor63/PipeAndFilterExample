package main;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Connor Taylor
 *
 * Pipe.java
 *
 * A simple pipe in the form of a queue
 *
 */
public class Pipe {

    Queue<String> data = new LinkedList<>();

    public synchronized void in (String in){
        data.add(in);
    }

    public synchronized String out (){
        return data.poll();
    }

}
