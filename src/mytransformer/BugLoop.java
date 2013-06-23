package mytransformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BugLoop {

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
