package vsoc.server.initial;

import vsoc.server.InitialPlacement;

/**
 * Places all Players in the center looking in the direction of the enemy goal.
 */
public class InitialPlacementOnePlayerFull implements InitialPlacement {

    private double x;
    private double y;
    private double dir;

    public InitialPlacementOnePlayerFull(double x, double y, double dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    @Override
    public int numberOfPlayers() {
        return 1;
    }

    @Override
    public Values placementValuesWest(int number) {
        return new Values(x, y, dir);
    }
}
