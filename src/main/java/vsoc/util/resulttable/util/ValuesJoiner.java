package vsoc.util.resulttable.util;

import java.util.Collection;
import java.util.Iterator;

public class ValuesJoiner {

    private static ValuesJoiner current = null;

    private ValuesJoiner() {
        super();
    }

    public static ValuesJoiner current() {
        if (current == null) {
            current = new ValuesJoiner();
        }
        return current;
    }

    public Number join(Collection<Number> values) {
        Number re = null;
        if (!values.isEmpty()) {
            double sum = 0;
            int  count = 0;
            Iterator<Number> iter = values.iterator();
            while (iter.hasNext()) {
                Number val = iter.next();
                if (val != null) {
                    sum += val.doubleValue();
                    count++;
                }
            }
            if (count > 0) {
                double mean = sum / count;
                re = mean;
            }
        }
        return re;
    }

}