package vsoc.server;

/**
 * A Flag
 */
class FlagPenaltyWest extends SimObject {

	private static final long serialVersionUID = 1L;
	
    private ServerFlag type;

    FlagPenaltyWest(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagPenaltyOwn(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagPenaltyOther(FlagConverter.current().forEast(this.type));
    }
}