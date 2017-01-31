package vsoc.behaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import atan.model.Flag;
import atan.model.Line;
import atan.model.PlayMode;
import atan.model.RefereeMessage;

public class Sensors {

    private Map<Flag, BehaviourVision> flagsRight = null;

    private Map<Flag, BehaviourVision> flagsLeft = null;

    private Map<Flag, BehaviourVision> flagsOwn = null;

    private Map<Flag, BehaviourVision> flagsOther = null;

    private Map<Flag, BehaviourVision> flagsCenter = null;

    private Map<Flag, BehaviourVision> flagsCornerOwn = null;

    private Map<Flag, BehaviourVision> flagsCornerOther = null;

    private Map<Flag, BehaviourVision> flagsPenaltyOwn = null;

    private Map<Flag, BehaviourVision> flagsPenaltyOther = null;

    private Map<Flag, BehaviourVision> flagsGoalOwn = null;

    private Map<Flag, BehaviourVision> flagsGoalOther = null;

    private Map<Integer, BehaviourVision> playersOwn = null;

    private Map<Integer, BehaviourVision> playersOther = null;

    private Map<Line, BehaviourVision> lines = null;

    private BehaviourVision ball = null;

    private RefereeMessage refereeMessage = null;

    private PlayMode playMode = null;

    private List<Message> messages = null;

    private BodyState bodyState = null;

    private boolean sawAnything = false;

    private boolean beforeKickOff = false;

    public Sensors() {
        super();
    }

    public BehaviourVision getBall() {
        return this.ball;
    }

    public void setBall(BehaviourVision ball) {
        this.ball = ball;
    }

    public BodyState getBodyState() {
        return this.bodyState;
    }

    public void setBodyState(BodyState bodyState) {
        this.bodyState = bodyState;
    }

    public Map<Flag, BehaviourVision> getFlagsCenter() {
        if (this.flagsCenter == null) {
            this.flagsCenter = new HashMap<>();
        }
        return this.flagsCenter;
    }

    public void setFlagsCenter(Map<Flag, BehaviourVision> flagsCenter) {
        this.flagsCenter = flagsCenter;
    }

    public Map<Flag, BehaviourVision> getFlagsCornerOther() {
        if (this.flagsCornerOther == null) {
            this.flagsCornerOther = new HashMap<>();
        }
        return this.flagsCornerOther;
    }

    public void setFlagsCornerOther(Map<Flag, BehaviourVision> flagsCornerOther) {
        this.flagsCornerOther = flagsCornerOther;
    }

    public Map<Flag, BehaviourVision> getFlagsCornerOwn() {
        if (this.flagsCornerOwn == null) {
            this.flagsCornerOwn = new HashMap<>();
        }
        return this.flagsCornerOwn;
    }

    public void setFlagsCornerOwn(Map<Flag, BehaviourVision> flagsCornerOwn) {
        this.flagsCornerOwn = flagsCornerOwn;
    }

    public Map<Flag, BehaviourVision> getFlagsGoalOther() {
        if (this.flagsGoalOther == null) {
            this.flagsGoalOther = new HashMap<>();
        }
        return this.flagsGoalOther;
    }

    public void setFlagsGoalOther(Map<Flag, BehaviourVision> flagsGoalOther) {
        this.flagsGoalOther = flagsGoalOther;
    }

    public Map<Flag, BehaviourVision> getFlagsGoalOwn() {
        if (this.flagsGoalOwn == null) {
            this.flagsGoalOwn = new HashMap<>();
        }
        return this.flagsGoalOwn;
    }

    public void setFlagsGoalOwn(Map<Flag, BehaviourVision> flagsGoalOwn) {
        this.flagsGoalOwn = flagsGoalOwn;
    }

    public Map<Flag, BehaviourVision> getFlagsLeft() {
        if (this.flagsLeft == null) {
            this.flagsLeft = new HashMap<>();
        }
        return this.flagsLeft;
    }

    public void setFlagsLeft(Map<Flag, BehaviourVision> flagsLeft) {
        this.flagsLeft = flagsLeft;
    }

    public Map<Flag, BehaviourVision> getFlagsOther() {
        if (this.flagsOther == null) {
            this.flagsOther = new HashMap<>();
        }
        return this.flagsOther;
    }

    public void setFlagsOther(Map<Flag, BehaviourVision> flagsOther) {
        this.flagsOther = flagsOther;
    }

    public Map<Flag, BehaviourVision> getFlagsOwn() {
        if (this.flagsOwn == null) {
            this.flagsOwn = new HashMap<>();
        }
        return this.flagsOwn;
    }

    public void setFlagsOwn(Map<Flag, BehaviourVision> flagsOwn) {
        this.flagsOwn = flagsOwn;
    }

    public Map<Flag, BehaviourVision> getFlagsPenaltyOther() {
        if (this.flagsPenaltyOther == null) {
            this.flagsPenaltyOther = new HashMap<>();
        }
        return this.flagsPenaltyOther;
    }

    public void setFlagsPenaltyOther(Map<Flag, BehaviourVision> flagsPenaltyOther) {
        this.flagsPenaltyOther = flagsPenaltyOther;
    }

    public Map<Flag, BehaviourVision> getFlagsPenaltyOwn() {
        if (this.flagsPenaltyOwn == null) {
            this.flagsPenaltyOwn = new HashMap<>();
        }
        return this.flagsPenaltyOwn;
    }

    public void setFlagsPenaltyOwn(Map<Flag, BehaviourVision> flagsPenaltyOwn) {
        this.flagsPenaltyOwn = flagsPenaltyOwn;
    }

    public Map<Flag, BehaviourVision> getFlagsRight() {
        if (this.flagsRight == null) {
            this.flagsRight = new HashMap<>();
        }
        return this.flagsRight;
    }

    public void setFlagsRight(Map<Flag, BehaviourVision> flagsRight) {
        this.flagsRight = flagsRight;
    }

    public List<Message> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<>();
        }
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Map<Integer, BehaviourVision> getPlayersOther() {
        if (this.playersOther == null) {
            this.playersOther = new HashMap<>();
        }
        return this.playersOther;
    }

    public void setPlayersOther(Map<Integer, BehaviourVision> playersOther) {
        this.playersOther = playersOther;
    }

    public Map<Integer, BehaviourVision> getPlayersOwn() {
        if (this.playersOwn == null) {
            this.playersOwn = new HashMap<>();
        }
        return this.playersOwn;
    }

    public void setPlayersOwn(Map<Integer, BehaviourVision> playersOwn) {
        this.playersOwn = playersOwn;
    }

    public PlayMode getPlayMode() {
        return this.playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public RefereeMessage getRefereeMessage() {
        return this.refereeMessage;
    }

    public void setRefereeMessage(RefereeMessage refereeMessage) {
        this.refereeMessage = refereeMessage;
    }

    public Map<Line, BehaviourVision> getLines() {
        if (this.lines == null) {
            this.lines = new HashMap<>();
        }
        return this.lines;
    }

    public void setLines(Map<Line, BehaviourVision> lines) {
        this.lines = lines;
    }

    public boolean sawAnything() {
        return this.sawAnything;
    }

    public boolean isBeforeKickOff() {
        return this.beforeKickOff;
    }

    public void setBeforeKickOff(boolean beforeKickOff) {
        this.beforeKickOff = beforeKickOff;
    }

    public void setSawAnything(boolean sawAnything) {
        this.sawAnything = sawAnything;
    }

    public boolean sawFlagCenter() {
        return this.flagsCenter != null;
    }

    public boolean sawFlagCornerOther() {
        return this.flagsCornerOther != null;
    }

    public boolean sawFlagCornerOwn() {
        return this.flagsCornerOwn != null;
    }

    public boolean sawFlagGoalOther() {
        return this.flagsGoalOther != null;
    }

    public boolean sawFlagGoalOwn() {
        return this.flagsGoalOwn != null;
    }

    public boolean sawFlagLeft() {
        return this.flagsLeft != null;
    }

    public boolean sawFlagOther() {
        return this.flagsOther != null;
    }

    public boolean sawFlagOwn() {
        return this.flagsOwn != null;
    }

    public boolean sawFlagPenaltyOther() {
        return this.flagsPenaltyOther != null;
    }

    public boolean sawFlagPenaltyOwn() {
        return this.flagsPenaltyOwn != null;
    }

    public boolean sawFlagRight() {
        return this.flagsRight != null;
    }

    public boolean sawLines() {
        return this.lines != null;
    }

    public boolean isHearedMessage() {
        return this.messages != null;
    }

    public boolean sawPlayerOther() {
        return this.playersOther != null;
    }

    public boolean sawPlayerOwn() {
        return this.playersOwn != null;
    }

}
