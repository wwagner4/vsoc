package vsoc.util.resulttable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vsoc.util.resulttable.ColumnDesc;
import vsoc.util.resulttable.ResultTable;
import vsoc.util.resulttable.ThinningResultTable;

public class ThinningResultTableRunner {

    private static Random ran = new Random();

    private static NumberFormat format = createNumFormat();

    public ThinningResultTableRunner() {
        super();
    }

    public static void main(String[] arg) {
        ThinningResultTableRunner runner = new ThinningResultTableRunner();
        runner.run();
    }

    private void run() {
        int var = 30;
        ResultTable rt1 = createResultTable(100, 60, 1000, var);
        ResultTable rt2 = createResultTable(100, 60, 5000, var);
        ResultTable rt3 = createResultTable(100, 60, 10000, var);
        ResultTable rt4 = createResultTable(100, 60, 15000, var);
        ResultTable rt5 = createResultTable(100, 60, 20000, var);
        List<Number> ser1 = rt1.getSerial();
        List<Number> col1 = rt1.getColumn("a");
        List<Number> ser2 = rt2.getSerial();
        List<Number> col2 = rt2.getColumn("a");
        List<Number> ser3 = rt3.getSerial();
        List<Number> col3 = rt3.getColumn("a");
        List<Number> ser4 = rt4.getSerial();
        List<Number> col4 = rt4.getColumn("a");
        List<Number> ser5 = rt5.getSerial();
        List<Number> col5 = rt5.getColumn("a");
        for (int i = 0; i < 100; i++) {
            printValue(ser1, i);
            printValue(col1, i);
            printValue(ser2, i);
            printValue(col2, i);
            printValue(ser3, i);
            printValue(col3, i);
            printValue(ser4, i);
            printValue(col4, i);
            printValue(ser5, i);
            printValue(col5, i);
            System.out.println();
        }
    }

    private void printValue(List<Number> col, int i) {
        if (i < col.size()) {
            Number val = col.get(i);
            if (val != null) {
                System.out.print(format(val.doubleValue()));
            }
        }
        System.out.print("\t");
    }

    private String format(double d) {
        return format.format(d);
    }

    private static NumberFormat createNumFormat() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        format.setMaximumFractionDigits(2);
        return format;
    }

    private ResultTable createResultTable(int maxSize, int minSize,
            int dataSize, int noise) {
        ThinningResultTable re = new ThinningResultTable();
        re.setMax(maxSize);
        re.setMin(minSize);
        ColumnDesc sdesc = new ColumnDesc();
        sdesc.setId("s");
        sdesc.setName("Serial Value");
        sdesc.setFormat(createFormat());
        re.setSerialDesc(sdesc);
        re.setColumnDescs(createColumnDescs());
        for (int i = 0; i < dataSize; i++) {
            re.addNextSerialValue(i);
            Double vala = i + noise(noise);
            re.setValue("a", vala);
        }
        return re;
    }

    private NumberFormat createFormat() {
        NumberFormat re = NumberFormat.getInstance();
        re.setMaximumFractionDigits(4);
        re.setMinimumFractionDigits(4);
        re.setGroupingUsed(false);
        return re;
    }

    private double noise(double ampli) {
        return ran.nextDouble() * ampli * 2 - ampli;
    }

    private List<ColumnDesc> createColumnDescs() {
        List<ColumnDesc> re = new ArrayList<>();
        {
            ColumnDesc desc = new ColumnDesc();
            desc.setId("a");
            desc.setName("Values");
            desc.setFormat(createFormat());
            re.add(desc);
        }
        return re;
    }

}
