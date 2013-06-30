package mytransformer.toddler;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 */
public class DynLoop {

    java.lang.String id;
    int iterations;
    HashMap<String, Stack<Seq>> map;

    public DynLoop(java.lang.String id) {
        this.id = id;
        this.iterations = 0;
        this.map = new HashMap<String, Stack<Seq>>();
    }

    public java.lang.String getId(){
        return id;
    }

    public void newIteration() {
        this.iterations++;
        for (String key: map.keySet()) {
            map.get(key).push(new Seq());
        }

    }

    public int getIterations() {
        return this.iterations;
    }

    public void put(String ipcs, Stack<Seq> seqs) {
        map.put(ipcs,seqs);
    }

    public void read(String ipcs, Val value) {
        if (map.containsKey(ipcs)) {
            map.get(ipcs).peek().add(value);
        } else {
            Seq seq = new Seq();
            seq.add(value);
            Stack<Seq> seqStack= new Stack<Seq>();
            seqStack.push(seq);
            map.put(ipcs,seqStack);
        }
    }

    public Map<String,Stack<Seq>> getMap() {
        return this.map;
    }


}
