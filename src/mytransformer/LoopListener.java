package mytransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LoopListener {


    public static synchronized void startLoop(String id) {
        System.out.println("StartLoop: " + id);
    }


    public static synchronized void startIteration() {
        System.out.println("StartIter");
    }

    public static synchronized void  endLoop(String id) {
        System.out.println("EndLoop: " + id);
    }

    public static synchronized void read(String id, int val) {
        System.out.println("Read:( From:" + id +", Value:" + val + ")");
    }
}
