package vsoc.server;import atan.model.Controller;class VisionPlayerOwn extends Vision {    private int num;    public VisionPlayerOwn(int num) {        this.num = num;    }    public void informControlSystem(Controller c) {        c.infoSeePlayerOwn(this.num, getDistance(), getDirection());    }}