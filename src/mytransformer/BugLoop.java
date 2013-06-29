package mytransformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BugLoop {

//    public static void main(String[] args) {
//        BugLoop bugLoop = new BugLoop();
//        List<Integer> from = new ArrayList<Integer>();
//        for (int i = 0; i < 100000; i++ ){
//            from.add(i);
//        }
//
//        List<Integer> to = new ArrayList<Integer>();
//        for (int j = 0; j < 10000010; j++) {
//            to.add(j);
//        }
//
//        bugLoop.removeAll(from,to);
//    }

    public void removeAll(Collection<Integer> from, Collection<Integer> to) {
        if (from.size() > to.size()) {
            for (Iterator<Integer> i = to.iterator(); i.hasNext();) {
                from.remove(i.next());
            }
        } else {
            for (Iterator<Integer> i = from.iterator(); i.hasNext();) {
                if (to.contains(i.next())) {
                    i.remove();
                }
            }
        }
    }

}
