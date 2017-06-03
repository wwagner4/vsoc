package vsoc.server;

import vsoc.util.Vec2D;

/**
 * Defines how many players are placed for a team,
 * where they are placed and in what direction they are looking
 */
public interface InitialPlacement {

    /**
     * @return The number of players for the team
     */
    int numberOfPlayers();

    /**
     * @param number Players number that will be placed. [0 ... numberOfPlayers() - 1]
     * @param east   true ... The east team is placed, false ... The west team is placed
     * @return The placement values for the player
     */
    default Values placementValues(int number, boolean east) {
        Values values = placementValuesWest(number);
        if (east) {
            values = values.flipSide();
        }
        return values;
    }

    /**
     * @param number Players number that will be placed. [0 ... numberOfPlayers() - 1]
     * @return The placement values for a west player
     */
    Values placementValuesWest(int number);


    /**
     * Placement values
     */
    class Values {

        private final double posx;
        private final double posy;
        private final double direction; // Must be in rad


        public Values(double posx, double posy, double direction) {
            this.posx = posx;
            this.posy = posy;
            this.direction = direction;
        }

        public double getPosx() {
            return posx;
        }

        public double getPosy() {
            return posy;
        }

        public double getDirection() {
            return direction;
        }

        public Values flipSide() {
            return new Values(-posx, posy, flipDirection(direction));
        }

        private double flipDirection(double direction) {
            Vec2D v1 = new Vec2D(direction);
            Vec2D v2 = new Vec2D(-v1.getX(), v1.getY());
            return v2.phi();
        }
    }

}
