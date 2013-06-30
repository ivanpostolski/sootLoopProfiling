package mytransformer.toddler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Seq {
    public List<Val> list;

    public Seq() {
        list = new ArrayList<Val>();
    }

    public void add(Val readedValue) {
        list.add(readedValue);
    }

    public String toString() {
        String res = "[";
        Iterator<Val> iterator = list.iterator();

        while (iterator.hasNext()) {
            Val current = iterator.next();
            res+= current.val;
            if (iterator.hasNext()) {
                res+= ",";
            }
        }
        res += "]";
        return res;
    }
}
