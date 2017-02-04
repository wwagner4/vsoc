package vsoc.server;


/**
 * A Flag
 */
class FlagWest extends SimObject {

	private static final long serialVersionUID = 1L;
	
    private ServerFlag type;

    FlagWest(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagOwn(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagOther(FlagConverter.current().forEast(this.type));
    }
}