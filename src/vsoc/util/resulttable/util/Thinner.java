package vsoc.util.resulttable.util;

import java.util.ArrayList;
import java.util.List;

public class Thinner {

    private static Thinner current = null;

    private Thinner() {
        super();
    }

    public static Thinner current() {
        if (current == null) {
            current = new Thinner();
        }
        return current;
    }

    public List<Object> thin(List<Object> inList, int amount) {
        int size = inList.size();
        if (size <= amount) {
            return inList;
        }
        List<Object> outList = new ArrayList<>();
        for (int index = 0; index < amount; index++) {
            int thinIndex = thin(size, amount, index);
            outList.add(inList.get(thinIndex));
        }
        return outList;
    }

    private int thin(int len, int n, int i) {
        if (i >= n)
            throw new IllegalArgumentException(
                    "i may never be greater or equal than n. i:" + i + " n:"
                            + n);
        if (n < 2)
            throw new IllegalArgumentException(
                    "n may never be smaller than 2. n:" + n);
        if (n > len)
            throw new IllegalArgumentException(
                    "n may never be greater than len. n:" + n + " len:" + len);
        int result;
        if (i == (n - 1)) {
            result = len - 1;
        } else {
            double d = 1.0 / (n - 1) * i;
            result = (int) Math.ceil(d * (len - 2));
        }
        return result;
    }

}
