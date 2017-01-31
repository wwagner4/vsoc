package vsoc.model;

import java.util.HashMap;
import java.util.Map;

import atan.model.Flag;

public class FlagConverter {
    
    private static FlagConverter current = null;
    
    private Map forEast = initForEastMap();

    private Map forWest = initForWestMap();

    private FlagConverter() {
        super();
    }
    
    private Map initForWestMap() {
        Map map = new HashMap();
        map.put(ServerFlag.CENTER, Flag.FLAG_CENTER);
        map.put(ServerFlag.NORTH, Flag.FLAG_LEFT);
        map.put(ServerFlag.SOUTH, Flag.FLAG_RIGHT);
        map.put(ServerFlag.NORTH_10, Flag.FLAG_LEFT_10);
        map.put(ServerFlag.NORTH_20, Flag.FLAG_LEFT_20);
        map.put(ServerFlag.NORTH_30, Flag.FLAG_LEFT_30);
        map.put(ServerFlag.SOUTH_10, Flag.FLAG_RIGHT_10);
        map.put(ServerFlag.SOUTH_20, Flag.FLAG_RIGHT_20);
        map.put(ServerFlag.SOUTH_30, Flag.FLAG_RIGHT_30);

        map.put(ServerFlag.EAST_10, Flag.FLAG_OTHER_10);
        map.put(ServerFlag.EAST_20, Flag.FLAG_OTHER_20);
        map.put(ServerFlag.EAST_30, Flag.FLAG_OTHER_30);
        map.put(ServerFlag.EAST_40, Flag.FLAG_OTHER_40);
        map.put(ServerFlag.EAST_50, Flag.FLAG_OTHER_50);

        map.put(ServerFlag.WEST_10, Flag.FLAG_OWN_10);
        map.put(ServerFlag.WEST_20, Flag.FLAG_OWN_20);
        map.put(ServerFlag.WEST_30, Flag.FLAG_OWN_30);
        map.put(ServerFlag.WEST_40, Flag.FLAG_OWN_40);
        map.put(ServerFlag.WEST_50, Flag.FLAG_OWN_50);

        return map;
    }

    private Map initForEastMap() {
        Map map = new HashMap();
        map.put(ServerFlag.CENTER, Flag.FLAG_CENTER);
        map.put(ServerFlag.NORTH, Flag.FLAG_RIGHT);
        map.put(ServerFlag.SOUTH, Flag.FLAG_LEFT);
        
        map.put(ServerFlag.NORTH_10, Flag.FLAG_RIGHT_10);
        map.put(ServerFlag.NORTH_20, Flag.FLAG_RIGHT_20);
        map.put(ServerFlag.NORTH_30, Flag.FLAG_RIGHT_30);
        map.put(ServerFlag.SOUTH_10, Flag.FLAG_LEFT_10);
        map.put(ServerFlag.SOUTH_20, Flag.FLAG_LEFT_20);
        map.put(ServerFlag.SOUTH_30, Flag.FLAG_LEFT_30);

        map.put(ServerFlag.EAST_10, Flag.FLAG_OWN_10);
        map.put(ServerFlag.EAST_20, Flag.FLAG_OWN_20);
        map.put(ServerFlag.EAST_30, Flag.FLAG_OWN_30);
        map.put(ServerFlag.EAST_40, Flag.FLAG_OWN_40);
        map.put(ServerFlag.EAST_50, Flag.FLAG_OWN_50);

        map.put(ServerFlag.WEST_10, Flag.FLAG_OTHER_10);
        map.put(ServerFlag.WEST_20, Flag.FLAG_OTHER_20);
        map.put(ServerFlag.WEST_30, Flag.FLAG_OTHER_30);
        map.put(ServerFlag.WEST_40, Flag.FLAG_OTHER_40);
        map.put(ServerFlag.WEST_50, Flag.FLAG_OTHER_50);

        return map;
    }

    public static FlagConverter current() {
        if (current == null) {
            current = new FlagConverter();
        }
        return current;
    }
    
    public Flag forEast(ServerFlag serverFlag) {
        return (Flag) this.forEast.get(serverFlag);
    }

    public Flag forWest(ServerFlag serverFlag) {
        return (Flag) this.forWest.get(serverFlag);
    }

}
