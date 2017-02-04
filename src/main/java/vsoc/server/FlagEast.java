package vsoc.server;


/**
 * A Flag
 */
class FlagEast extends SimObject {
    
	private static final long serialVersionUID = 1L;
	
    private ServerFlag type = null;

    FlagEast(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagOther(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagOwn(FlagConverter.current().forEast(this.type));
    }
}