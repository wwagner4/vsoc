package vsoc.model;

import org.apache.commons.lang.enums.Enum;

public class ServerFlag extends Enum {

    public static final ServerFlag NORTH = new ServerFlag("north");

    public static final ServerFlag CENTER = new ServerFlag("center");

    public static final ServerFlag SOUTH = new ServerFlag("south");

    public static final ServerFlag EAST_10 = new ServerFlag("east10");

    public static final ServerFlag EAST_20 = new ServerFlag("east20");

    public static final ServerFlag EAST_30 = new ServerFlag("east30");

    public static final ServerFlag EAST_40 = new ServerFlag("east40");

    public static final ServerFlag EAST_50 = new ServerFlag("east50");

    public static final ServerFlag WEST_10 = new ServerFlag("west10");

    public static final ServerFlag WEST_20 = new ServerFlag("west20");

    public static final ServerFlag WEST_30 = new ServerFlag("west30");

    public static final ServerFlag WEST_40 = new ServerFlag("west40");

    public static final ServerFlag WEST_50 = new ServerFlag("west50");

    public static final ServerFlag NORTH_10 = new ServerFlag("north10");

    public static final ServerFlag NORTH_20 = new ServerFlag("north20");

    public static final ServerFlag NORTH_30 = new ServerFlag("north30");

    public static final ServerFlag SOUTH_10 = new ServerFlag("south10");

    public static final ServerFlag SOUTH_20 = new ServerFlag("south20");

    public static final ServerFlag SOUTH_30 = new ServerFlag("south30");

    public ServerFlag(String name) {
        super(name);
    }

}
