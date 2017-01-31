package vsoc.model;


/**
 * A Flag
 */

@SuppressWarnings("serial")
class FlagEast extends SimObject {
    
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