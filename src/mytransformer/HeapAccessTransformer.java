package mytransformer;


import soot.*;
import soot.JastAddJ.AssignExpr;
import soot.jimple.*;
import soot.jimple.internal.JAssignStmt;
import soot.jimple.internal.JimpleLocal;
import soot.jimple.internal.VariableBox;
import soot.util.Chain;

import java.util.Iterator;
import java.util.Map;

public class HeapAccessTransformer extends BodyTransformer {

    /* some internal fields */
    private SootClass listenerClass, systemClass;
    private SootMethod readArray,identityHashCode;

    public HeapAccessTransformer(String clazz) {
        listenerClass   = Scene.v().loadClassAndSupport(clazz);
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
                //has an array-ref but still can be a write operation
                if (((JAssignStmt) stmt).getRightOp() instanceof ArrayRef) {
                    String readId = stmt.toString();
                    StaticInvokeExpr identityExpr = Jimple.v().newStaticInvokeExpr(identityHashCode.makeRef(),((JAssignStmt) stmt).getLeftOp());
                    Local l = Jimple.v().newLocal("myvar" + body.getLocalCount(),IntType.v());
                    body.getLocals().add(l);
                    AssignStmt assign = Jimple.v().newAssignStmt(l,identityExpr);
                    body.getUnits().insertAfter(assign,stmt);

                    StaticInvokeExpr readExpr = Jimple.v().newStaticInvokeExpr(readArray.makeRef(),StringConstant.v(readId),l);
                    Stmt readStmt = Jimple.v().newInvokeStmt(readExpr);
                    body.getUnits().insertAfter(readStmt,assign);
                }
            }
        }

    }
}
