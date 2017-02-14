package vsoc.server;

/**
 * A Flag
 */
class FlagNorth extends SimObject {

	private static final long serialVersionUID = 1L;
	
    private ServerFlag type;

    FlagNorth(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagLeft(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagRight(FlagConverter.current().forEast(this.type));
    }
}