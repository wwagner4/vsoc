package vsoc.behaviour;

import java.io.Serializable;

import atan.model.Controller;
import atan.model.Flag;
import atan.model.Line;
import atan.model.PlayMode;
import atan.model.Player;
import atan.model.RefereeMessage;
import atan.model.ViewAngle;
import atan.model.ViewQuality;

public class BehaviourController implements Controller, Serializable {
    
    private static final long serialVersionUID = 0L;

    private Behaviour behaviour = null;

    private transient Sensors sensors = null;

    private transient Player player;

    public BehaviourController() {
        super();
    }
    
    public BehaviourController(Behaviour behaviour) {
        super();
        this.behaviour = behaviour;
    }

    public void preInfo() {
        this.sensors = new Sensors();
    }

    public void postInfo() {
        behave(this.behaviour);
    }

    private void behave(Behaviour behaviour) {
        if (behaviour != null) {
            if (behaviour.shouldBeApplied(this.sensors)) {
                behaviour.apply(this.sensors, this.player);
            } else {
                behave(behaviour.getChild());
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void infoSeeFlagRight(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsRight().put(flag, new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagLeft(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsLeft().put(flag, new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagOwn(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsOwn().put(flag, new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagOther(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsOther().put(flag, new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagCenter(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsCenter()
                .put(flag, new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagCornerOwn(Flag flag, double distance,
            double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsCornerOwn().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagCornerOther(Flag flag, double distance,
            double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsCornerOther().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagPenaltyOwn(Flag flag, double distance,
            double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsPenaltyOwn().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagPenaltyOther(Flag flag, double distance,
            double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsPenaltyOther().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagGoalOwn(Flag flag, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsGoalOwn().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeFlagGoalOther(Flag flag, double distance,
            double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getFlagsGoalOther().put(flag,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeLine(Line line, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getLines().put(line, new BehaviourVision(distance, direction));
    }

    public void infoSeePlayerOther(int number, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getPlayersOther().put(number,
                new BehaviourVision(distance, direction));
    }

    public void infoSeePlayerOwn(int number, double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.getPlayersOwn().put(number,
                new BehaviourVision(distance, direction));
    }

    public void infoSeeBall(double distance, double direction) {
        this.sensors.setSawAnything(true);
        this.sensors.setBall(new BehaviourVision(distance, direction));
    }

    public void infoHearReferee(RefereeMessage refereeMessage) {
        this.sensors.setRefereeMessage(refereeMessage);
    }

    public void infoHearPlayMode(PlayMode playMode) {
        if (playMode == PlayMode.BEFORE_KICK_OFF) {
            this.sensors.setBeforeKickOff(true);
        }
        this.sensors.setPlayMode(playMode);
    }

    public void infoHear(double direction, String message) {
        this.sensors.getMessages().add(new Message(message, direction));
    }

    public void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle,
            double stamina, double speed, double headAngle, int kickCount,
            int dashCount, int turnCount, int sayCount, int turnNeckCount) {
        BodyState state = new BodyState();
        state.setViewQuality(viewQuality);
        state.setViewAngle(viewAngle);
        state.setDashCount(dashCount);
        state.setHeadAngle(headAngle);
        state.setKickCount(kickCount);
        state.setSayCount(sayCount);
        state.setSpeed(speed);
        state.setStamina(stamina);
        state.setTurnCount(turnCount);
        state.setTurnNeckCount(turnNeckCount);
        this.sensors.setBodyState(state);
    }

}
