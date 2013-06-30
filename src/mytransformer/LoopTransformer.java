package mytransformer;

import soot.*;
import soot.jimple.*;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

import java.util.*;

public class LoopTransformer extends BodyTransformer {

     /* some internal fields */
    private SootClass listenerClass;
    private SootMethod startLoop, startIter, endLoop;

    public LoopTransformer(String clazz) {
        listenerClass   = Scene.v().loadClassAndSupport(clazz);
        startLoop = listenerClass.getMethod("void startLoop(java.lang.String)");
        startIter = listenerClass.getMethod("void startIteration()");
        endLoop = listenerClass.getMethod("void endLoop(java.lang.String)");
    }


    @Override
    protected void internalTransform(Body b, String phaseName, Map options) {
        LoopFinderAdapter loopFinderAdapter = new LoopFinderAdapter();
        loopFinderAdapter.internalTransformAdapter(b, phaseName, options);

        for (Loop loop: loopFinderAdapter.loops()) {
            Stmt head = loop.getHead();
            String loopId = b.getMethod().getSignature() + ":" + head.toString();
            //insert the start loop invocation but we aware of the redirect labels!
            InvokeExpr startLoopExpr = Jimple.v().newStaticInvokeExpr(startLoop.makeRef(),StringConstant.v(loopId));
            Stmt startLoopStmt = Jimple.v().newInvokeStmt(startLoopExpr);
            b.getUnits().insertBeforeNoRedirect(startLoopStmt,head);


            //get loop head and make the startIteration invocation
            InvokeExpr startExpr= Jimple.v().newStaticInvokeExpr(startIter.makeRef());
            Stmt startStmt = Jimple.v().newInvokeStmt(startExpr);
            b.getUnits().insertAfter(startStmt,head);


            //get every loop exit and make the endLoop invocation (with properly loop number)
            for (Stmt exit: loop.getLoopExits()) {
                for (Stmt target: loop.targetsOfLoopExit(exit)) {
                    InvokeExpr endExpr = Jimple.v().newStaticInvokeExpr(endLoop.makeRef(),StringConstant.v(loopId));
                    Stmt endStmt = Jimple.v().newInvokeStmt(endExpr);
                    b.getUnits().insertBefore(endStmt,target);
                }
            }

        }
    }
}

