package vsoc.util.resulttable.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMap {

    private Map<Integer, List<Object>> map = new HashMap<>();

    private static List<Object> emptyList = new ArrayList<>();

    public ListMap() {
        super();
    }

    public void put(int id, Object obj) {
        Integer intid = id;
        List<Object> values = this.map.get(intid);
        if (values == null) {
            values = new ArrayList<>();
            this.map.put(intid, values);
        }
        values.add(obj);
    }

    public List<Object> get(int id) {
        Integer intid = id;
        List<Object> re = this.map.get(intid);
        if (re == null) {
            re = emptyList;
        }
        return re;
    }

}
