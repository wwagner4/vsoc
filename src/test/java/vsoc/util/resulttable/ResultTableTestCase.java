package vsoc.util.resulttable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;
import vsoc.util.resulttable.ColumnDesc;
import vsoc.util.resulttable.LMHOutputter;
import vsoc.util.resulttable.ResultTable;
import vsoc.util.resulttable.ResultTableRow;
import vsoc.util.resulttable.SimpleResultTable;
import vsoc.util.resulttable.util.Categorizer;
import vsoc.util.resulttable.util.ListMap;
import vsoc.util.resulttable.util.Thinner;
import vsoc.util.resulttable.util.ValuesJoiner;

public class ResultTableTestCase extends TestCase {

    public void testUncompressed() {
        ResultTable rt = createSimpleTestTable();

        List<ResultTableRow> rows = rt.getRows();
        assertEquals("Number of rows", 2, rows.size());

        {
            ResultTableRow row = (ResultTableRow) rows.get(0);
            assertEquals("Number of values", 3, row.getResultValues().size());
            assertEquals(0, row.getSerialValue());

            Number valA = row.getResultValue("a");
            assertEquals(10.0, valA);

            Number valB = row.getResultValue("b");
            assertEquals(20.0, valB);

            Number valC = row.getResultValue("c");
            assertEquals(33.33f, valC);

        }
        {
            ResultTableRow row = (ResultTableRow) rows.get(1);
            assertEquals("Number of values", 3, row.getResultValues().size());
            assertEquals(10, row.getSerialValue());

            Number valA = row.getResultValue("a");
            assertEquals(12.0, valA);

            Number valB = row.getResultValue("b");
            assertEquals(44.44, valB);

            Number valC = row.getResultValue("c");
            assertEquals(null, valC);

        }

    }

    public void testOutputter() {
        ResultTable rt = createSimpleTestTable();
        LMHOutputter outputter = new LMHOutputter();
        outputter.setTable(rt);
    }

    private ResultTable createSimpleTestTable() {
        SimpleResultTable re = new SimpleResultTable();
        ColumnDesc sdesc = new ColumnDesc();
        sdesc.setId("s");
        sdesc.setName("Serial Value");
        sdesc.setFormat(createFormat());
        re.setSerialDesc(sdesc);
        re.setColumnDescs(createColumnDescs());
        ResultTable rt = re;
        rt.addNextSerialValue(0);
        rt.setValue("a", 10.0);
        rt.setValue("b", 20.0);
        rt.setValue("c", 33.33f);
        rt.addNextSerialValue(10);
        rt.setValue("a", 12.0);
        rt.setValue("b", 44.44);
        return rt;
    }

    private NumberFormat createFormat() {
        NumberFormat re = NumberFormat.getInstance();
        re.setMaximumFractionDigits(4);
        re.setMinimumFractionDigits(4);
        re.setGroupingUsed(false);
        return re;
    }

    private List<ColumnDesc> createColumnDescs() {
        List<ColumnDesc> re = new ArrayList<>();
        {
        	ColumnDesc desc = new ColumnDesc();
            desc.setId("a");
            desc.setName("Hallo a");
            desc.setFormat(createFormat());
            re.add(desc);
        }
        {
            ColumnDesc desc = new ColumnDesc();
            desc.setId("b");
            desc.setFormat(createFormat());
            re.add(desc);
        }
        {
            ColumnDesc desc = new ColumnDesc();
            desc.setId("c");
            desc.setFormat(createFormat());
            re.add(desc);
        }
        return re;
    }

    public void testCategorizer00() {
        Categorizer cat = new Categorizer(1, 9, 3);
        assertEquals(2, cat.maxIndex());

        assertEquals(0, cat.categorize(-2));
        assertEquals(0, cat.categorize(0.9999));
        assertEquals(0, cat.categorize(1));
        assertEquals(0, cat.categorize(2));
        assertEquals(0, cat.categorize(2.345345));
        assertEquals(0, cat.categorize(2.999999999));

        assertEquals(1, cat.categorize(3));
        assertEquals(1, cat.categorize(3.3434));
        assertEquals(1, cat.categorize(5));
        assertEquals(1, cat.categorize(6));
        assertEquals(1, cat.categorize(6.999999999));

        assertEquals(2, cat.categorize(7));
        assertEquals(2, cat.categorize(8));
        assertEquals(2, cat.categorize(7.00000000001));
        assertEquals(2, cat.categorize(8.876876));
        assertEquals(2, cat.categorize(9));
        assertEquals(2, cat.categorize(9997));
        assertEquals(2, cat.categorize(9997.08098876));

        assertEquals(1.0, cat.categoryValue(0), 0.000001);
        assertEquals(5.0, cat.categoryValue(1), 0.000001);
        assertEquals(9.0, cat.categoryValue(2), 0.000001);
    }

    public void testCategorizer01() {
        Categorizer cat = new Categorizer(0, 13, 5);
        assertEquals(4, cat.maxIndex());

        assertEquals(0, cat.categorize(-2));
        assertEquals(0, cat.categorize(0.9999));
        assertEquals(0, cat.categorize(1));

        assertEquals(1, cat.categorize(2));
        assertEquals(1, cat.categorize(2.345345));
        assertEquals(1, cat.categorize(2.999999999));
        assertEquals(1, cat.categorize(3));
        assertEquals(1, cat.categorize(3.3434));

        assertEquals(2, cat.categorize(5));
        assertEquals(2, cat.categorize(6));
        assertEquals(2, cat.categorize(6.999999999));
        assertEquals(2, cat.categorize(7));
        assertEquals(2, cat.categorize(8));
        assertEquals(2, cat.categorize(7.00000000001));

        assertEquals(3, cat.categorize(8.876876));
        assertEquals(3, cat.categorize(9));

        assertEquals(4, cat.categorize(13));
        assertEquals(4, cat.categorize(12));
        assertEquals(4, cat.categorize(9997));
        assertEquals(4, cat.categorize(9997.08098876));

        assertEquals(0.0, cat.categoryValue(0), 0.000001);
        assertEquals(3.25, cat.categoryValue(1), 0.000001);
        assertEquals(6.5, cat.categoryValue(2), 0.000001);
        assertEquals(9.75, cat.categoryValue(3), 0.000001);
        assertEquals(13, cat.categoryValue(4), 0.000001);
    }

    public void testValuesJoiner00() {
        Collection<Number> values = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            values.add(i);
        }
        Number re = ValuesJoiner.current().join(values);
        assertEquals(14.5, re.doubleValue(), 0.0001);
    }

    public void testValuesJoiner01() {
        Collection<Number> values = new ArrayList<>();
        for (int i = 0; i < 33; i++) {
            values.add(i);
        }
        Number re = ValuesJoiner.current().join(values);
        assertEquals(16, re.doubleValue(), 0.0001);
    }

    public void testListMap() {
        ListMap lm = new ListMap();
        lm.put(0, 1);
        lm.put(0, 2);
        lm.put(0, 3);
        lm.put(0, 4);

        lm.put(1, 10);
        lm.put(1, 20);
        lm.put(1, 30);
        lm.put(1, 40);

        assertNotNull(lm.get(-1));
        assertTrue(lm.get(-1).isEmpty());

        assertNotNull(lm.get(2));
        assertTrue(lm.get(2).isEmpty());

        assertNotNull(lm.get(2000));
        assertTrue(lm.get(2000).isEmpty());
        {
            List<Object> list = lm.get(0);
            assertEquals(4, list.size());
            Integer val0 = (Integer) list.get(0);
            assertEquals(1, val0.intValue());
            Integer val1 = (Integer) list.get(1);
            assertEquals(2, val1.intValue());
            Integer val2 = (Integer) list.get(2);
            assertEquals(3, val2.intValue());
            Integer val3 = (Integer) list.get(3);
            assertEquals(4, val3.intValue());
        }
        {
            List<Object> list = lm.get(1);
            assertEquals(4, list.size());
            Integer val0 = (Integer) list.get(0);
            assertEquals(10, val0.intValue());
            Integer val1 = (Integer) list.get(1);
            assertEquals(20, val1.intValue());
            Integer val2 = (Integer) list.get(2);
            assertEquals(30, val2.intValue());
            Integer val3 = (Integer) list.get(3);
            assertEquals(40, val3.intValue());
        }
    }

    public void testThinner00() {
        ArrayList<Object> in = new ArrayList<>();
        in.add(0);
        in.add(1);
        in.add(2);
        in.add(3);
        in.add(4);
        in.add(5);
        Thinner t = Thinner.current();
        List<Object> out = t.thin(in, 3);
        assertEquals(3, out.size());
        assertEquals(0, ((Integer) out.get(0)).intValue());
        assertEquals(2, ((Integer) out.get(1)).intValue());
        assertEquals(5, ((Integer) out.get(2)).intValue());
    }

    public void testThinner01() {
        ArrayList<Object> in = new ArrayList<>();
        in.add(0);
        in.add(1);
        in.add(2);
        in.add(3);
        in.add(4);
        in.add(5);
        Thinner t = Thinner.current();
        List<Object> out = t.thin(in, 4);
        assertEquals(4, out.size());
        assertEquals(0, ((Integer) out.get(0)).intValue());
        assertEquals(2, ((Integer) out.get(1)).intValue());
        assertEquals(3, ((Integer) out.get(2)).intValue());
        assertEquals(5, ((Integer) out.get(3)).intValue());
    }
}
