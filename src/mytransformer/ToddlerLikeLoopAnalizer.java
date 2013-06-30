package mytransformer;

import mytransformer.toddler.DynLoop;
import mytransformer.toddler.Val;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 */
public class ToddlerLikeLoopAnalizer implements LoopAnalizer{

    Stack<DynLoop> currentLoopStack = new Stack<DynLoop>();

    @Override
    public void startLoop(String id) {
        DynLoop newDynLoop = new DynLoop(id);
        currentLoopStack.push(newDynLoop);
    }

    @Override
    public void startIteration() {
        currentLoopStack.peek().newIteration();
    }

    @Override
    public void endLoop(String id) {
        DynLoop endedLoop = currentLoopStack.pop();
        System.out.println("Loop:" + endedLoop.getId() + " has ended");
        System.out.println("Iterations: " + endedLoop.getIterations());
        System.out.println(endedLoop.getMap());
    }

    @Override
    public void read(String ipcs, int val) {
        Iterator<DynLoop> iterator = currentLoopStack.iterator();
        while (iterator.hasNext()) {
            DynLoop dynLoop = iterator.next();
            dynLoop.read(ipcs,new Val(val));
        }
    }
}
