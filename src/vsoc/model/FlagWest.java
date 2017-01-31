package vsoc.model;


/**
 * A Flag
 */

@SuppressWarnings("serial")
class FlagWest extends SimObject {

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