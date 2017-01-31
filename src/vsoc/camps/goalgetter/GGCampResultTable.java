package vsoc.camps.goalgetter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vsoc.util.resulttable.ColumnDesc;
import vsoc.util.resulttable.SimpleResultTable;

/**
 * Result table for the changing results of a GGCamp.
 */
@SuppressWarnings("serial")
public class GGCampResultTable extends SimpleResultTable {

    public GGCampResultTable() {
        super();
        NumberFormat format = createFormat();
        ColumnDesc sdesc = createSerialDesc(format);
        setSerialDesc(sdesc);
        List<ColumnDesc> cdescs = new ArrayList<>();
        Iterator<GGCampResultColumns> iter = GGCampResultColumns.getEnumList().iterator();
        while (iter.hasNext()) {
            GGCampResultColumns enu = (GGCampResultColumns) iter.next();
            ColumnDesc desc = new ColumnDesc();
            desc.setFormat(format);
            desc.setId(enu.getName());
            desc.setName(enu.getDesc());
            cdescs.add(desc);
        }
        setColumnDescs(cdescs);
    }

    private ColumnDesc createSerialDesc(NumberFormat format) {
        ColumnDesc sdesc = new ColumnDesc();
        sdesc.setFormat(format);
        sdesc.setId("selections");
        sdesc.setName("Number of selections");
        return sdesc;
    }

    private NumberFormat createFormat() {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(3);
        format.setMaximumFractionDigits(3);
        format.setGroupingUsed(false);
        return format;
    }

}
