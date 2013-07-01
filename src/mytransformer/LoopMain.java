package mytransformer;


import mytransformer.core.HeapAccessTransformer;
import mytransformer.core.LoopTransformer;
import soot.PackManager;
import soot.Transform;

import java.util.ArrayList;
import java.util.List;

public class LoopMain {
    public static void main(String[] args) {

        String choicedListener = null;
        for(int i = 0; i < args.length - 1; i++) {
            if ("-listener".equals(args[i])) {
               choicedListener = args[i+1];
               args = removeElements(removeElements(args,"-listener"),choicedListener);
            }
        }
        String listener = (choicedListener == null) ? "mytransformer.listeners.BasicLoopListener" : "mytransformer.listeners." + choicedListener;

// Adding custom transformers, first loop listener and then heap access.
        PackManager.v().getPack("jtp").add(new
                Transform("jtp.loopanalysis",
                new LoopTransformer(listener)));
        PackManager.v().getPack("jtp").add(new Transform("jtp.heapaccesstransformer", new HeapAccessTransformer(listener)));


// Invoke soot.Main with arguments given
        soot.Main.main(args);
    }

    public static String[] removeElements(String[] input, String deleteMe) {
        List<String> result = new ArrayList<String>();

        for(String item : input)
            if(!deleteMe.equals(item))
                result.add(item);

        return result.toArray(new String[0]);
    }
}
