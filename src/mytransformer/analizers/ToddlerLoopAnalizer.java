package mytransformer.analizers;

import mytransformer.toddler.DynLoop;
import mytransformer.toddler.Seq;
import mytransformer.toddler.Val;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

/**
 *
 */
public class ToddlerLoopAnalizer implements LoopAnalizer{

    Stack<DynLoop> currentLoopStack = new Stack<DynLoop>();

    int minIter = 10;

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
        if (hasPerformanceBug(endedLoop)) {
            System.out.println("Loop:" + endedLoop.getId() + " has ended");
            System.out.println("Iterations: " + endedLoop.getIterations());
            System.out.println("Loop: " + endedLoop.getId() + " can have a performance bug!");

            PrintWriter writer = null;
            try {
                File analysisResults = new File("loop_analysis_results");
                analysisResults.mkdir();
                File analysisTags = new File(analysisResults,"tags");
                analysisTags.mkdir();
                File tag = new File(analysisTags,endedLoop.getId() + ".tag");
                writer = new PrintWriter(tag, "UTF-8");
                writer.println(endedLoop.getId());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }


    }

    private boolean hasPerformanceBug(DynLoop loop) {
        return !(computeSimilarIpcs(loop).isEmpty());
    }

    private Set<String> computeSimilarIpcs(DynLoop loop) {
        Set<String> similarIpcs = new HashSet<String>();
        if (loop.getIterations() < minIter) return similarIpcs;
        for (String curlIpcs: loop.getMap().keySet()) {
            if (areSimilarIterations(loop.getMap().get(curlIpcs),loop.getIterations())) {
                similarIpcs.add(curlIpcs);
            }
        }
        return similarIpcs;

    }

    double minSeqRatio = 0.45;
    double minSimRatio = 0.7;

    private boolean areSimilarIterations(Stack<Seq> seqs, int iterations) {
        if ((seqs.size()/iterations) < minSeqRatio) return false;
        int similar = 0;
        for (int i =0; i < seqs.size() -1 ; i++) {
            if (areSimilarSequences(seqs.get(i),seqs.get(i+1))) similar++;
        }
        return (similar / (seqs.size() -1)) >= minSimRatio;


    }

    int minLCS = 7;
    double minLCSRatio =  0.7;

    private boolean areSimilarSequences(Seq s1, Seq s2) {
        int lcs = longestCommonSubstring(s1.toString(),s2.toString()).length();
        float lcsRatio = lcs / Math.min(s1.list.size(), s2.list.size());
        return (lcs >= minLCS) && (lcsRatio >= minLCSRatio);
    }

    private static String longestCommonSubstring(String S1, String S2)
    {
        int Start = 0;
        int Max = 0;
        for (int i = 0; i < S1.length(); i++)
        {
            for (int j = 0; j < S2.length(); j++)
            {
                int x = 0;
                while (S1.charAt(i + x) == S2.charAt(j + x))
                {
                    x++;
                    if (((i + x) >= S1.length()) || ((j + x) >= S2.length())) break;
                }
                if (x > Max)
                {
                    Max = x;
                    Start = i;
                }
            }
        }
        return S1.substring(Start, (Start + Max));
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
