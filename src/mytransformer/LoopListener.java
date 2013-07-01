package mytransformer;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class LoopListener {

    private static LoopAnalizer loopAnalizer = new ToddlerLoopAnalizer();


    public static synchronized void startLoop(String id) {
        loopAnalizer.startLoop(id);
    }


    public static synchronized void startIteration() {
        loopAnalizer.startIteration();
    }

    public static synchronized void endLoop(String id) {
        loopAnalizer.endLoop(id);
    }

    public static synchronized void read(String id, int val) {
        loopAnalizer.read(id,val);
    }
}
