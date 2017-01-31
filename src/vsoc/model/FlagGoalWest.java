package vsoc.model;

/**
 * A Flag
 */

class FlagGoalWest extends SimObject {

    private ServerFlag type;

    FlagGoalWest(ServerFlag type, double x, double y) {
        super(x, y);
    }

    Vision createVisionForWestPlayer() {
        return new VisionGoalOwn(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionGoalOther(FlagConverter.current().forEast(this.type));
    }
}