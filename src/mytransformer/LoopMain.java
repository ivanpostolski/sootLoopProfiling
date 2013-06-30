package mytransformer;


import soot.PackManager;
import soot.Transform;

public class LoopMain {
    public static void main(String[] args) {



// Adding custom transformers, first loop listener and then heap access.
        PackManager.v().getPack("jtp").add(new
                Transform("jtp.loopanalysis",
                new LoopTransformer("mytransformer.LoopListener")));
        PackManager.v().getPack("jtp").add(new Transform("jtp.heapaccesstransformer", new HeapAccessTransformer("mytransformer.LoopListener")));


// Invoke soot.Main with arguments given
        soot.Main.main(args);
    }
}
