package vsoc.behaviour;

import vsoc.util.Retina;

public class SensorsOld {

    private boolean hasSeenBall = false;

    private double distBall = 1000000.0;

    private boolean hasSeen = false;

    private transient Retina retinaFlagLeft = new Retina();

    private transient Retina retinaFlagRight =  new Retina();

    private transient Retina retinaFlagOwn =  new Retina();

    private transient Retina retinaFlagPenaltyOther =  new Retina();

    private transient Retina retinaGoalOther =  new Retina();

    private transient Retina retinaPlayerOther =  new Retina();

    private transient Retina retinaPlayerOwn =  new Retina();

    private transient Retina retinaBall =  new Retina();

    public SensorsOld() {
        super();
    }

    public double getDistBall() {
        return this.distBall;
    }

    public void setDistBall(double distBall) {
        this.distBall = distBall;
    }

    public boolean isHasSeen() {
        return this.hasSeen;
    }

    public void setHasSeen(boolean hasSeen) {
        this.hasSeen = hasSeen;
    }

    public boolean isHasSeenBall() {
        return this.hasSeenBall;
    }

    public void setHasSeenBall(boolean hasSeenBall) {
        this.hasSeenBall = hasSeenBall;
    }

    public Retina getRetinaBall() {
        return this.retinaBall;
    }

    public void setRetinaBall(Retina retinaBall) {
        this.retinaBall = retinaBall;
    }

    public Retina getRetinaFlagLeft() {
        return this.retinaFlagLeft;
    }

    public void setRetinaFlagLeft(Retina retinaFlagLeft) {
        this.retinaFlagLeft = retinaFlagLeft;
    }

    public Retina getRetinaFlagOwn() {
        return this.retinaFlagOwn;
    }

    public void setRetinaFlagOwn(Retina retinaFlagOwn) {
        this.retinaFlagOwn = retinaFlagOwn;
    }

    public Retina getRetinaFlagPenaltyOther() {
        return this.retinaFlagPenaltyOther;
    }

    public void setRetinaFlagPenaltyOther(Retina retinaFlagPenaltyOther) {
        this.retinaFlagPenaltyOther = retinaFlagPenaltyOther;
    }

    public Retina getRetinaFlagRight() {
        return this.retinaFlagRight;
    }

    public void setRetinaFlagRight(Retina retinaFlagRight) {
        this.retinaFlagRight = retinaFlagRight;
    }

    public Retina getRetinaGoalOther() {
        return this.retinaGoalOther;
    }

    public void setRetinaGoalOther(Retina retinaGoalOther) {
        this.retinaGoalOther = retinaGoalOther;
    }

    public Retina getRetinaPlayerOther() {
        return this.retinaPlayerOther;
    }

    public void setRetinaPlayerOther(Retina retinaPlayerOther) {
        this.retinaPlayerOther = retinaPlayerOther;
    }

    public Retina getRetinaPlayerOwn() {
        return this.retinaPlayerOwn;
    }

    public void setRetinaPlayerOwn(Retina retinaPlayerOwn) {
        this.retinaPlayerOwn = retinaPlayerOwn;
    }

}
