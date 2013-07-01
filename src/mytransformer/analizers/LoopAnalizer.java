package mytransformer.analizers;

/**
 *
 */
public interface LoopAnalizer {
    void startLoop(String id);
    void startIteration();
    void endLoop(String id);
    void read(String id, int val);
}
