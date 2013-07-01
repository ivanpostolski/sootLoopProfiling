package mytransformer.listeners;


import mytransformer.analizers.LoopAnalizer;
import mytransformer.analizers.ToddlerLoopAnalizer;

public class ToddlerPerformanceDetectionListener extends BasicLoopListener {

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
    };

}
