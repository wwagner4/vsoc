package vsoc.server;import atan.model.Controller;import atan.model.Flag;class VisionFlagOther extends Vision {        private Flag type;    public VisionFlagOther(Flag type) {        this.type = type;    }    public void informControlSystem(Controller c) {        c.infoSeeFlagOther(this.type, this.getDistance(), this.getDirection());    }}