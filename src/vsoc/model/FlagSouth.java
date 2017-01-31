package vsoc.model;

/**
 * A Flag
 */

class FlagSouth extends SimObject {

    private ServerFlag type;
    
    FlagSouth(ServerFlag type, double x, double y) {
        super(x, y);
        this.type = type;
    }

    Vision createVisionForWestPlayer() {
        return new VisionFlagRight(FlagConverter.current().forWest(this.type));
    }

    Vision createVisionForEastPlayer() {
        return new VisionFlagLeft(FlagConverter.current().forWest(this.type));
    }
}