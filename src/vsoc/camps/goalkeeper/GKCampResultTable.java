package vsoc.camps.goalkeeper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vsoc.camps.goalkeeper.GKCampResultColumns;
import vsoc.util.resulttable.ColumnDesc;
import vsoc.util.resulttable.SimpleResultTable;

public class GKCampResultTable extends SimpleResultTable {

  	private static final long serialVersionUID = 1L;

    public GKCampResultTable() {
        super();
        NumberFormat format = createFormat();
        ColumnDesc sdesc = createSerialDesc(format);
        setSerialDesc(sdesc);
        List<ColumnDesc> cdescs = new ArrayList<>();
        Iterator<GKCampResultColumns> iter = GKCampResultColumns.getEnumList().iterator();
        while (iter.hasNext()) {
            GKCampResultColumns enu = iter.next();
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
        sdesc.setId("generations");
        sdesc.setName("Number of generations");
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
