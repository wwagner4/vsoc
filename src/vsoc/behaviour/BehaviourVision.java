package vsoc.behaviour;

import java.text.NumberFormat;

public class BehaviourVision {

    private double distance;

    private double direction;

    private static NumberFormat format = initNumberFormat();

    public BehaviourVision() {
        super();
    }

    public BehaviourVision(double distance, double direction) {
        super();
        this.distance = distance;
        this.direction = direction;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        String dist = format.format(this.distance);
        String dir = format.format(this.direction);
        StringBuilder msg = new StringBuilder("BehaviourVision[").append(dist)
                .append("|").append(dir).append("]");
        return msg.toString();
    }

    private static NumberFormat initNumberFormat() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(1);
        nf.setGroupingUsed(false);
        return nf;
    }

}
