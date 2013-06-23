package mytransformer;


import soot.PackManager;
import soot.Transform;

public class LoopMain {
    public static void main(String[] args) {
// Inject the analysis tagger into Soot
//        PackManager.v().getPack("jtp").add(new Transform("jtp.instrumenter", new InvokeStaticInstrumenter()));
        PackManager.v().getPack("jtp").add(new
                Transform("jtp.loopanalysis",
                new LoopTransformer()));


// Invoke soot.Main with arguments given
        soot.Main.main(args);
    }
}
