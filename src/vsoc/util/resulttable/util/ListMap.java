package vsoc.util.resulttable.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMap {

    private Map map = new HashMap();

    private static List emptyList = new ArrayList();

    public ListMap() {
        super();
    }

    public void put(int id, Object obj) {
        Integer intid = new Integer(id);
        List values = (List) this.map.get(intid);
        if (values == null) {
            values = new ArrayList();
            this.map.put(intid, values);
        }
        values.add(obj);
    }

    public List get(int id) {
        Integer intid = new Integer(id);
        List re = (List) this.map.get(intid);
        if (re == null) {
            re = emptyList;
        }
        return re;
    }

}
