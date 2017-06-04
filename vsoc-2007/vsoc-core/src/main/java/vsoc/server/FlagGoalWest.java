package vsoc.server;

/**
 * A Flag
 */
class FlagGoalWest extends SimObject {

	private static final long serialVersionUID = 1L;
	
    private ServerFlag type;

    FlagGoalWest(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionGoalOwn(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionGoalOther(FlagConverter.current().forEast(this.type));
    }
}