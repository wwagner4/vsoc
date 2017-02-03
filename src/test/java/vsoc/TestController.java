package vsoc;import vsoc.model.VsocPlayer;import vsoc.util.Vec2D;import atan.model.Controller;import atan.model.Flag;import atan.model.Line;import atan.model.PlayMode;import atan.model.Player;import atan.model.RefereeMessage;import atan.model.ViewAngle;import atan.model.ViewQuality;class TestController implements Controller {    Vec2D[] ownFlags;    Vec2D[] otherFlags;    Vec2D[] rightFlags;    Vec2D[] leftFlags;    Vec2D[] ownPlayer;    Vec2D[] otherPlayer;    Vec2D ball;    Vec2D ownGoal;    Vec2D otherGoal;    private int flagOwnIndex = 0;    private int flagOtherIndex = 0;    private int flagRightIndex = 0;    private int flagLeftIndex = 0;    private int playerOwnIndex = 0;    private int playerOtherIndex = 0;    private VsocPlayer player;    TestController() {        init();    }    void init() {        this.ownFlags = new Vec2D[20];        this.otherFlags = new Vec2D[20];        this.rightFlags = new Vec2D[20];        this.leftFlags = new Vec2D[20];        this.ownPlayer = new Vec2D[20];        this.otherPlayer = new Vec2D[20];        this.ball = null;        this.ownGoal = null;        this.otherGoal = null;        this.flagOwnIndex = 0;        this.flagOtherIndex = 0;        this.flagRightIndex = 0;        this.flagLeftIndex = 0;        this.playerOwnIndex = 0;        this.playerOtherIndex = 0;    }    public void preInfo() {        // Nothing to be done    }    public void postInfo() {        // Nothing to be done    }    public void setPlayer(Player p) {        this.player = (VsocPlayer) p;    }    public Player getPlayer() {        return this.player;    }    public void infoSeeBall(double distance, double direction) {        this.ball = createVec2D(distance, direction);    }    public void infoSeeFlagGoalOwn(Flag id, double distance, double direction) {        if (id == Flag.FLAG_CENTER)            this.ownGoal = createVec2D(distance, direction);    }    public void infoSeeFlagGoalOther(Flag id, double distance, double direction) {        if (id == Flag.FLAG_CENTER)            this.otherGoal = createVec2D(distance, direction);    }    public void infoSeeFlagLeft(Flag id, double distance, double direction) {        this.leftFlags[this.flagLeftIndex++] = createVec2D(distance, direction);    }    public void infoSeeFlagOther(Flag id, double distance, double direction) {        this.otherFlags[this.flagOtherIndex++] = createVec2D(distance, direction);    }    public void infoSeeFlagRight(Flag id, double distance, double direction) {        this.rightFlags[this.flagRightIndex++] = createVec2D(distance, direction);    }    public void infoSeeFlagOwn(Flag id, double distance, double direction) {        this.ownFlags[this.flagOwnIndex++] = createVec2D(distance, direction);    }    public void infoSeePlayerOwn(int id, double distance, double direction) {        this.ownPlayer[this.playerOwnIndex++] = createVec2D(distance, direction);    }    public void infoSeePlayerOther(int id, double distance, double direction) {        this.otherPlayer[this.playerOtherIndex++] = createVec2D(distance, direction);    }    public void infoSeeFlagPenaltyOther(Flag id, double distance,            double direction) {        // Nothing to be done    }    public void infoSeeFlagPenaltyOwn(Flag id, double distance, double direction) {        // Nothing to be done    }    private Vec2D createVec2D(double dist, double dir) {        Vec2D v = new Vec2D(-dir * 0.017453293);        return v.mul(dist);    }    public void infoHearReferee(RefereeMessage refereeMessage) {        // Nothing to be done    }    public void infoHearPlayMode(PlayMode playMode) {        // Nothing to be done    }    public void infoHear(double direction, String message) {        // Nothing to be done    }    public void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle, double stamina,            double speed, double headAngle, int kickCount, int dashCount,            int turnCount, int sayCount, int turnNeckCount) {        // Nothing to be done    }    public void infoSeeLine(Line id, double distance, double direction) {        // Nothing to be done    }    public void infoSeeFlagCenter(Flag id, double distance, double direction) {        // Nothing to be done    }    public void infoSeeFlagCornerOwn(Flag id, double distance, double direction) {        // Nothing to be done    }    public void infoSeeFlagCornerOther(Flag id, double distance, double direction) {        // Nothing to be done    }}