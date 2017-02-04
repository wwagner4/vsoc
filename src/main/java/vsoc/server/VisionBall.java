package vsoc.server;

import atan.model.Controller;

class VisionBall extends Vision {

    public void informControlSystem(Controller c) {
        c.infoSeeBall(this.getDistance(), this.getDirection());
    }
}
