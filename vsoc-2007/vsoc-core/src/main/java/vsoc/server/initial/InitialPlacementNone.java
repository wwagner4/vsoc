package vsoc.server.initial;

import vsoc.server.InitialPlacement;

/**
 * Placement for an empty team (no players)
 */
public class InitialPlacementNone implements InitialPlacement {
    @Override
    public int numberOfPlayers() {
        return 0;
    }

    @Override
    public Values placementValuesWest(int number) {
        throw new IllegalStateException("Sould never be called");
    }
}
