package vsoc.model;

/**
 * A Flag
 */

class FlagPenaltyEast extends SimObject {

    private ServerFlag type;

    FlagPenaltyEast(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagPenaltyOther(FlagConverter.current().forWest(
                this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagPenaltyOwn(FlagConverter.current().forEast(
                this.type));
    }
}