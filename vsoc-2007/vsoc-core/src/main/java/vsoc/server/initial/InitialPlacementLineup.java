package vsoc.server.initial;

import vsoc.server.InitialPlacement;

import java.util.ArrayList;
import java.util.List;

/**
 * Places all players in a vertical line inside the own half
 * looking in the direction of the enimy goal
 */
public class InitialPlacementLineup implements InitialPlacement {

    private final int numberOfPlayers;

    private final List<Double> posList = createPosList();

    public InitialPlacementLineup(int numberOfPlayers) {
        if (numberOfPlayers > posList.size()) {
            throw new IllegalArgumentException(String.format("Can place a maximum of %d players", posList.size()));
        }
        this.numberOfPlayers = numberOfPlayers;
    }

    @Override
    public int numberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public Values placementValues(int number, boolean east) {
        if (number < 0) {
            throw new IllegalArgumentException(String.format("Number must be positive. %d", number));
        }
        if (number > posList.size() - 1) {
            throw new IllegalArgumentException(String.format("Can place a maximum of %d players", posList.size()));
        }
        return new Values(-20, posList.get(number), 0.0);
    }

    private List<Double> createPosList() {
        List<Double> l = new ArrayList<>();
        l.add(0.0);
        l.add(10.0);
        l.add(-10.0);
        l.add(20.0);
        l.add(-20.0);
        l.add(25.0);
        l.add(-25.0);
        l.add(5.0);
        l.add(-5.0);
        l.add(15.0);
        l.add(-15.0);
        return l;
    }

}
