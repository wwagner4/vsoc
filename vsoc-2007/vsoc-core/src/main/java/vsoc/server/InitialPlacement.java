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

        private final double posx; // Position inside the own half of the field is
        private final double posy;
        private final double direction; // Must be in deg (0 to 360)

        /**
         * Defines the position of a player at the field.
         * Size of the field is 100 x 50.
         * (0, 0) is the coordinate of the center of the field.
         * x-values smaller than 0 place the player inside the own field
         * y-values smaller than 0 place the player on the left side of the field looking in the direction of the opponent goal
         * directions of 0 let the player look at the direction of the opponent door
         * directions greater than 0 turn the player to the right
         *
         * @param posx [-50, 50]
         * @param posy [-30, 30]
         * @param direction in Degree.
         */
        public Values(double posx, double posy, double direction) {
            this.posx = posx;
            this.posy = - posy;
            this.direction = direction * -1.0;
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
            Vec2D v1 = new Vec2D(Vec2D.degToRad(direction));
            Vec2D v2 = new Vec2D(-v1.getX(), v1.getY());
            return Vec2D.radToDeg(v2.phi());
        }
    }

}
