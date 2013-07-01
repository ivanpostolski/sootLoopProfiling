package mytransformer.analizers;

import mytransformer.toddler.DynLoop;
import mytransformer.toddler.Seq;
import mytransformer.toddler.Val;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 */
public class ToddlerLoopPrinterAnalizer implements LoopAnalizer{

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

        PrintWriter writer = null;
        try {
            File analysisResults = new File("loop_analysis_results");
            analysisResults.mkdir();
            File analysis = new File(analysisResults,endedLoop.getId() + ".txt");
            writer = new PrintWriter( analysis, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(endedLoop.getIterations());
        for (String ipcs: endedLoop.getMap().keySet()) {
            writer.print(ipcs + " # ");
            Iterator<Seq> seqIterator = endedLoop.getMap().get(ipcs).iterator();
            while (seqIterator.hasNext()) {
                Seq current = seqIterator.next();
                if (seqIterator.hasNext()) {
                    writer.print(",");
                }
            }
            writer.println();
        }
        writer.println("END");
        writer.close();
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
