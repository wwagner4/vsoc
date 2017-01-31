package vsoc.util.resulttable.util;

/**
 * Devides a range of values into categories. Values inside the range can be
 * assigned to exactly one of that category. Each of the categories has one
 * central value.
 */
public class Categorizer {

    private Interval[] intervals;

    private double min;

    private double max;

    private int maxIndex;

    /**
     * 
     * @param min
     *            The lower border of the range.
     * @param max
     *            The upper border of the range
     * @param n
     *            The number of categories.
     */
    public Categorizer(double min, double max, int n) {
        super();
        this.maxIndex = n - 1;
        this.max = max;
        this.min = min;
        this.intervals = createIntervals(min, max, n);
    }

    public int categorize(double val) {
        int re = -1;
        for (int i = 0; i <= this.maxIndex; i++) {
            if (this.intervals[i].includes(val)) {
                re = i;
                break;
            }
        }
        if (re == -1) {
            throw new IllegalArgumentException(val + " is not within ["
                    + this.min + "|" + this.max + "]");
        }
        return re;
    }

    public double categoryValue(int index) {
        return this.intervals[index].getRepr();
    }

    public int maxIndex() {
        return this.maxIndex;
    }

    private Interval[] createIntervals(double min, double max, int n) {
        if (n < 2) {
            throw new IllegalArgumentException(
                    "n must not be smaller than 2. n=" + n);
        }
        Interval[] is = new Interval[n];
        double d = (max - min) / (2 * (n - 1));
        double base = min + d;
        double toPrev = -1;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                is[i] = new Interval(-Double.MAX_VALUE, base, min);
                toPrev = base;
            } else if (i == (n - 1)) {
                is[i] = new Interval(toPrev, Double.MAX_VALUE, max);
            } else {
                is[i] = new Interval(toPrev, toPrev + 2 * d, toPrev + d);
                toPrev = toPrev + 2 * d;
            }
        }
        return is;
    }

    private class Interval {

        private double from;

        private double to;

        private double repr;

        public Interval(double from, double to, double repr) {
            super();
            this.from = from;
            this.to = to;
            this.repr = repr;
        }

        public boolean includes(double val) {
            boolean re = false;
            if (val >= this.from && val < this.to) {
                re = true;
            }
            return re;
        }

        public double getRepr() {
            return this.repr;
        }

        public String toString() {
            return "Interval[" + this.from + "|" + this.to + "] " + this.repr;
        }

    }
}