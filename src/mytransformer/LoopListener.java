package mytransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LoopListener {

    public static synchronized void startLoop(int id) {
        System.out.println("StartLoop");
    }


    public static synchronized void startIteration() {
        System.out.println("StartIter");
    }

    public static synchronized void  endLoop(int id) {
        System.out.println("EndLoop");
    }

    public static synchronized void read(int ip, long val) {
        System.out.println("Read(" + ip +"," + val + ")");
    }
}
