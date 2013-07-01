package mytransformer.analizers;

/**
 *
 */
public class PrintLoopAnalizer implements LoopAnalizer {
    @Override
    public void startLoop(String id) {
        System.out.println("StartLoop: " + id);
    }

    @Override
    public void startIteration() {
        System.out.println("StartIter");
    }

    @Override
    public void endLoop(String id) {
        System.out.println("EndLoop");
    }

    @Override
    public void read(String id, int val) {
        System.out.println("Read:( From:" + id +", Value:" + val + ")");
    }
}
