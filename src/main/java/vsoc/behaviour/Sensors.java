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

    private Map<Flag, DistDirVision> flagsRight = null;

    private Map<Flag, DistDirVision> flagsLeft = null;

    private Map<Flag, DistDirVision> flagsOwn = null;

    private Map<Flag, DistDirVision> flagsOther = null;

    private Map<Flag, DistDirVision> flagsCenter = null;

    private Map<Flag, DistDirVision> flagsCornerOwn = null;

    private Map<Flag, DistDirVision> flagsCornerOther = null;

    private Map<Flag, DistDirVision> flagsPenaltyOwn = null;

    private Map<Flag, DistDirVision> flagsPenaltyOther = null;

    private Map<Flag, DistDirVision> flagsGoalOwn = null;

    private Map<Flag, DistDirVision> flagsGoalOther = null;

    private Map<Integer, DistDirVision> playersOwn = null;

    private Map<Integer, DistDirVision> playersOther = null;

    private Map<Line, DistDirVision> lines = null;

    private DistDirVision ball = null;

    private RefereeMessage refereeMessage = null;

    private PlayMode playMode = null;

    private List<Message> messages = null;

    private BodyState bodyState = null;

    private boolean sawAnything = false;

    private boolean beforeKickOff = false;

    public Sensors() {
        super();
    }

    public DistDirVision getBall() {
        return this.ball;
    }

    public void setBall(DistDirVision ball) {
        this.ball = ball;
    }

    public BodyState getBodyState() {
        return this.bodyState;
    }

    public void setBodyState(BodyState bodyState) {
        this.bodyState = bodyState;
    }

    public Map<Flag, DistDirVision> getFlagsCenter() {
        if (this.flagsCenter == null) {
            this.flagsCenter = new HashMap<>();
        }
        return this.flagsCenter;
    }

    public void setFlagsCenter(Map<Flag, DistDirVision> flagsCenter) {
        this.flagsCenter = flagsCenter;
    }

    public Map<Flag, DistDirVision> getFlagsCornerOther() {
        if (this.flagsCornerOther == null) {
            this.flagsCornerOther = new HashMap<>();
        }
        return this.flagsCornerOther;
    }

    public void setFlagsCornerOther(Map<Flag, DistDirVision> flagsCornerOther) {
        this.flagsCornerOther = flagsCornerOther;
    }

    public Map<Flag, DistDirVision> getFlagsCornerOwn() {
        if (this.flagsCornerOwn == null) {
            this.flagsCornerOwn = new HashMap<>();
        }
        return this.flagsCornerOwn;
    }

    public void setFlagsCornerOwn(Map<Flag, DistDirVision> flagsCornerOwn) {
        this.flagsCornerOwn = flagsCornerOwn;
    }

    public Map<Flag, DistDirVision> getFlagsGoalOther() {
        if (this.flagsGoalOther == null) {
            this.flagsGoalOther = new HashMap<>();
        }
        return this.flagsGoalOther;
    }

    public void setFlagsGoalOther(Map<Flag, DistDirVision> flagsGoalOther) {
        this.flagsGoalOther = flagsGoalOther;
    }

    public Map<Flag, DistDirVision> getFlagsGoalOwn() {
        if (this.flagsGoalOwn == null) {
            this.flagsGoalOwn = new HashMap<>();
        }
        return this.flagsGoalOwn;
    }

    public void setFlagsGoalOwn(Map<Flag, DistDirVision> flagsGoalOwn) {
        this.flagsGoalOwn = flagsGoalOwn;
    }

    public Map<Flag, DistDirVision> getFlagsLeft() {
        if (this.flagsLeft == null) {
            this.flagsLeft = new HashMap<>();
        }
        return this.flagsLeft;
    }

    public void setFlagsLeft(Map<Flag, DistDirVision> flagsLeft) {
        this.flagsLeft = flagsLeft;
    }

    public Map<Flag, DistDirVision> getFlagsOther() {
        if (this.flagsOther == null) {
            this.flagsOther = new HashMap<>();
        }
        return this.flagsOther;
    }

    public void setFlagsOther(Map<Flag, DistDirVision> flagsOther) {
        this.flagsOther = flagsOther;
    }

    public Map<Flag, DistDirVision> getFlagsOwn() {
        if (this.flagsOwn == null) {
            this.flagsOwn = new HashMap<>();
        }
        return this.flagsOwn;
    }

    public void setFlagsOwn(Map<Flag, DistDirVision> flagsOwn) {
        this.flagsOwn = flagsOwn;
    }

    public Map<Flag, DistDirVision> getFlagsPenaltyOther() {
        if (this.flagsPenaltyOther == null) {
            this.flagsPenaltyOther = new HashMap<>();
        }
        return this.flagsPenaltyOther;
    }

    public void setFlagsPenaltyOther(Map<Flag, DistDirVision> flagsPenaltyOther) {
        this.flagsPenaltyOther = flagsPenaltyOther;
    }

    public Map<Flag, DistDirVision> getFlagsPenaltyOwn() {
        if (this.flagsPenaltyOwn == null) {
            this.flagsPenaltyOwn = new HashMap<>();
        }
        return this.flagsPenaltyOwn;
    }

    public void setFlagsPenaltyOwn(Map<Flag, DistDirVision> flagsPenaltyOwn) {
        this.flagsPenaltyOwn = flagsPenaltyOwn;
    }

    public Map<Flag, DistDirVision> getFlagsRight() {
        if (this.flagsRight == null) {
            this.flagsRight = new HashMap<>();
        }
        return this.flagsRight;
    }

    public void setFlagsRight(Map<Flag, DistDirVision> flagsRight) {
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

    public Map<Integer, DistDirVision> getPlayersOther() {
        if (this.playersOther == null) {
            this.playersOther = new HashMap<>();
        }
        return this.playersOther;
    }

    public void setPlayersOther(Map<Integer, DistDirVision> playersOther) {
        this.playersOther = playersOther;
    }

    public Map<Integer, DistDirVision> getPlayersOwn() {
        if (this.playersOwn == null) {
            this.playersOwn = new HashMap<>();
        }
        return this.playersOwn;
    }

    public void setPlayersOwn(Map<Integer, DistDirVision> playersOwn) {
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

    public Map<Line, DistDirVision> getLines() {
        if (this.lines == null) {
            this.lines = new HashMap<>();
        }
        return this.lines;
    }

    public void setLines(Map<Line, DistDirVision> lines) {
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
