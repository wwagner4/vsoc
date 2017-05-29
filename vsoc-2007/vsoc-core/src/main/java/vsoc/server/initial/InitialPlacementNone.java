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
    public Values placementValues(int number, boolean east) {
        throw new IllegalStateException("Sould never be called");
    }
}
