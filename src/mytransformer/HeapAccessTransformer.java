package mytransformer;


import soot.*;
import soot.JastAddJ.AssignExpr;
import soot.jimple.*;
import soot.jimple.internal.JAssignStmt;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;

public class HeapAccessTransformer extends BodyTransformer {

    /* some internal fields */
    static SootClass listenerClass, systemClass;
    static SootMethod readArray,identityHashCode;

    static {
        listenerClass   = Scene.v().loadClassAndSupport("mytransformer.LoopListener");
        readArray = listenerClass.getMethod("void read(java.lang.String,int)");
        systemClass = Scene.v().loadClassAndSupport("java.lang.System");
        identityHashCode = systemClass.getMethod("int identityHashCode(java.lang.Object)");
    }

    @Override
    protected void internalTransform(Body body, String phaseName, Map options) {
        // get body's unit as a chain
        Chain units = body.getUnits();

        // get a snapshot iterator of the unit since we are going to
        // mutate the chain when iterating over it.
        //
        Iterator stmtIt = units.snapshotIterator();

        // typical while loop for iterating over each statement
        while (stmtIt.hasNext()) {

            // cast back to a statement.
            Stmt stmt = (Stmt)stmtIt.next();

            if (stmt.containsFieldRef()) {
                FieldRef fieldRef = stmt.getFieldRef();
            }

            if (stmt.containsArrayRef()) {
                ArrayRef arrayRef = stmt.getArrayRef();
                //has an array-ref but still can be a write operation
                if (((JAssignStmt) stmt).getRightOp() instanceof ArrayRef) {
                    // now we reach the real instruction
                    // call Chain.insertBefore() to insert instructions
                    //
                    // 1. first, make a new invoke expression
                    String readId = stmt.toString();

                    InvokeExpr identityExpr = Jimple.v().newStaticInvokeExpr(identityHashCode.makeRef(),((JAssignStmt) stmt).getLeftOp());

                    Stmt identityStmt = Jimple.v().newInvokeStmt(identityExpr);

                    units.insertAfter(identityStmt, stmt);

//                    InvokeExpr readExpr= Jimple.v().newStaticInvokeExpr(readArray.makeRef(),
//                            StringConstant.v(readId),Value);
//                    // 2. then, make a invoke statement
//                    Stmt incStmt = Jimple.v().newInvokeStmt(incExpr);

                    // 3. insert new statement into the chain
                    //    (we are mutating the unit chain).
//                    units.insertBefore(incStmt, stmt);

                }


            }
        }

    }
}
