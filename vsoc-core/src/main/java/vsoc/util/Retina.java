package vsoc.util;

import java.io.Serializable;

public class Retina implements Serializable {

    private static final long serialVersionUID = 0L;

    private short a;

    private short b;

    private short c;

    private short d;

    private static final short MAX_SEE = 120;

    private static final short MAX = 99;

    private static final double MEAN_A = 33.75;

    private static final double MEAN_B = 11.25;

    private static final double MEAN_C = -11.25;

    private static final double MEAN_D = -33.75;

    private static final double UNIT = 11.25;

    public void addVision(double dist, double deg) {
        if ((this.a < MAX) && (dist < MAX_SEE)) {
            double rel = (deg - MEAN_A) / UNIT;
            if ((rel > -3.0) && (rel < 3.0)) {
                short inc = increase(dist, rel);
                if (this.a + inc > MAX) {
                    inc = (short) (MAX - this.a);
                }
                this.a += inc;
            }
        }
        if ((this.b < MAX) && (dist < MAX_SEE)) {
            double rel = (deg - MEAN_B) / UNIT;
            if ((rel > -3.0) && (rel < 3.0)) {
                short inc = increase(dist, rel);
                if (this.b + inc > MAX) {
                    inc = (short) (MAX - this.b);
                }
                this.b += inc;
            }
        }
        if ((this.c < MAX) && (dist < MAX_SEE)) {
            double rel = (deg - MEAN_C) / UNIT;
            if ((rel > -3.0) && (rel < 3.0)) {
                short inc = increase(dist, rel);
                if (this.c + inc > MAX) {
                    inc = (short) (MAX - this.c);
                }
                this.c += inc;
            }
        }
        if ((this.d < MAX) && (dist < MAX_SEE)) {
            double rel = (deg - MEAN_D) / UNIT;
            if ((rel > -3.0) && (rel < 3.0)) {
                short inc = increase(dist, rel);
                if (this.d + inc > MAX) {
                    inc = (short) (MAX - this.d);
                }
                this.d += inc;
            }
        }
    }

    private short increase(double dist, double dir) {
        double relDist = MAX - dist * MAX / MAX_SEE;
        double relDir;
        if (dir < 0)
            relDir = 1 + dir / 3;
        else
            relDir = 1 - dir / 3;
        return (short) (relDist * relDir);
    }

    public void reset() {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }

    public short getA() {
        return this.a;
    }

    public short getB() {
        return this.b;
    }

    public short getC() {
        return this.c;
    }

    public short getD() {
        return this.d;
    }
}