package mytransformer;

import soot.*;
import soot.jimple.*;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

import java.util.*;

public class LoopTransformer extends BodyTransformer {

     /* some internal fields */
    static SootClass listenerClass;
    static SootMethod startLoop, startIter, endLoop;

    static {
      listenerClass   = Scene.v().loadClassAndSupport("mytransformer.LoopListener");
      startLoop = listenerClass.getMethod("void startLoop(int)");
      startIter = listenerClass.getMethod("void startIteration()");
      endLoop = listenerClass.getMethod("void endLoop(int)");

    }


    @Override
    protected void internalTransform(Body b, String phaseName, Map options) {
        LoopFinderAdapter loopFinderAdapter = new LoopFinderAdapter();
        loopFinderAdapter.internalTransformAdapter(b, phaseName, options);

        for (Loop loop: loopFinderAdapter.loops()) {
            Stmt head = loop.getHead();
            //insert the start loop invocation but we aware of the redirect labels!
            InvokeExpr startLoopExpr = Jimple.v().newStaticInvokeExpr(startLoop.makeRef(),IntConstant.v(1));
            Stmt startLoopStmt = Jimple.v().newInvokeStmt(startLoopExpr);
            b.getUnits().insertBeforeNoRedirect(startLoopStmt,head);


            //get loop head and make the startIteration invocation
            InvokeExpr startExpr= Jimple.v().newStaticInvokeExpr(startIter.makeRef());
            Stmt startStmt = Jimple.v().newInvokeStmt(startExpr);
            b.getUnits().insertAfter(startStmt,head);


            //get every loop exit and make the endLoop invocation (with properly loop number)
            for (Stmt exit: loop.getLoopExits()) {
                for (Stmt target: loop.targetsOfLoopExit(exit)) {
                    InvokeExpr endExpr = Jimple.v().newStaticInvokeExpr(endLoop.makeRef(),IntConstant.v(1));
                    Stmt endStmt = Jimple.v().newInvokeStmt(endExpr);
                    b.getUnits().insertBefore(endStmt,target);
                }
            }

        }
    }
}

