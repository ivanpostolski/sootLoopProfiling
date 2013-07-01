package mytransformer.core;

import soot.Body;
import soot.jimple.toolkits.annotation.logic.LoopFinder;

import java.util.Map;


public class LoopFinderAdapter extends LoopFinder {

    public void internalTransformAdapter(Body b, String phaseName, Map options) {
        this.internalTransform(b,phaseName,options);
    }
}
