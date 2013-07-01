package mytransformer.listeners;

import mytransformer.analizers.LoopAnalizer;
import mytransformer.analizers.PrintLoopAnalizer;


public class BasicLoopListener {

    private static LoopAnalizer loopAnalizer = new PrintLoopAnalizer();


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
