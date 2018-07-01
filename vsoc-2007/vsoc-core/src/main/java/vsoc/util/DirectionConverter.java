package vsoc.util;

import java.io.PrintWriter;

public class DirectionConverter {

    private int maxVal;

    public DirectionConverter(int maxVal) {
        this.maxVal = maxVal;
    }
    public short getA(double in) {
        return (short) Math.round(Math.sin(in) * this.maxVal);
    }
    public short getB(double in) {
        return (short) Math.round(Math.cos(in) * this.maxVal);
    }
    void writeStat(PrintWriter ps) {
        DirectionConverter dc = new DirectionConverter(100);
        ps.println("dir;a;b");
        for (double i = -1; i < 5; i += 0.01) {
            short a = dc.getA(i);
            short b = dc.getB(i);
            ps.println(i + ";" + a + ";" + b);
        }
    }
}
