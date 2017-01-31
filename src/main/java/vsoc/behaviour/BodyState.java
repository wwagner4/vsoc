package vsoc.behaviour;

import atan.model.ViewAngle;
import atan.model.ViewQuality;

public class BodyState {

    private ViewQuality viewQuality;

    private ViewAngle viewAngle;

    private double stamina;

    private double speed;

    private double headAngle;

    private int kickCount;

    private int dashCount;

    private int turnCount;

    private int sayCount;

    private int turnNeckCount;

    public BodyState() {
        super();
    }

    public int getDashCount() {
        return this.dashCount;
    }

    public void setDashCount(int dashCount) {
        this.dashCount = dashCount;
    }

    public double getHeadAngle() {
        return this.headAngle;
    }

    public void setHeadAngle(double headAngle) {
        this.headAngle = headAngle;
    }

    public int getKickCount() {
        return this.kickCount;
    }

    public void setKickCount(int kickCount) {
        this.kickCount = kickCount;
    }

    public int getSayCount() {
        return this.sayCount;
    }

    public void setSayCount(int sayCount) {
        this.sayCount = sayCount;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getStamina() {
        return this.stamina;
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public int getTurnCount() {
        return this.turnCount;
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
    }

    public int getTurnNeckCount() {
        return this.turnNeckCount;
    }

    public void setTurnNeckCount(int turnNeckCount) {
        this.turnNeckCount = turnNeckCount;
    }

    public ViewAngle getViewAngle() {
        return this.viewAngle;
    }

    public void setViewAngle(ViewAngle viewAngle) {
        this.viewAngle = viewAngle;
    }

    public ViewQuality getViewQuality() {
        return this.viewQuality;
    }

    public void setViewQuality(ViewQuality viewQuality) {
        this.viewQuality = viewQuality;
    }

}
