package vsoc.server.initial;

/**
 * Defines how many players are placed for a team,
 * where they are placed and in what direction they are looking
 */
public interface InitialPlacement {

    /**
     *
     * @return The number of players for the team
     */
    int numberOfPlayers();

    /**
     *
     * @param number Players number that will be placed. [0 ... numberOfPlayers() - 1]
     * @param east true ... The east team is placed, false ... The west team is placed
     * @return The placement values for the player
     */
    Values placementValues(int number, boolean east);

    /**
     * Placement values
     */
    class Values {

        private final double posx;
        private final double posy;
        private final double direction;


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
    }

}
