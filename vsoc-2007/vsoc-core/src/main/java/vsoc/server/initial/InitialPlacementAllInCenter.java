package vsoc.server.initial;

import vsoc.server.InitialPlacement;

/**
 * Places all Players in the center looking in the direction of the enimy goal.
 */
public class InitialPlacementAllInCenter implements InitialPlacement {

    private final int numberOfPlayers;

    public InitialPlacementAllInCenter(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public int numberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public Values placementValuesWest(int number) {
        Values values = new Values(0.0, 0.0, 0.0);
        return values;
    }
}
