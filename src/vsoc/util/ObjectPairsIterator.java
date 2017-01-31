package vsoc.util;

import java.util.List;

// TODO Replace ObectPair by Pair<A, B>
public class ObjectPairsIterator {

    private int index = 0;

    private int runningIndex = 0;

    private List<Object[]> list;

    public ObjectPairsIterator(List<Object[]> l) {
        this.index = l.size() - 1;
        this.runningIndex = this.index - 1;
        this.list = l;
    }

    public Object[] next() {
        Object[] pair = new Object[2];
        pair[0] = this.list.get(this.index);
        pair[1] = this.list.get(this.runningIndex);
        if (this.runningIndex <= 0) {
            if (this.index >= 2) {
                this.index--;
                this.runningIndex = this.index - 1;
            } else
                this.runningIndex--;
        } else
            this.runningIndex--;
        return pair;
    }

    public boolean hasNext() {
        return this.runningIndex >= 0;
    }
}