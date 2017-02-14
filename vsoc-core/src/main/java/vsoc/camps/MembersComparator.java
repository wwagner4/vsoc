package vsoc.camps;

import java.util.Comparator;

public class MembersComparator implements Comparator<Member<?>> {

    private int kickFactor;

    private int ownGoalsFactor;

    private int otherGoalsFactor;

    private int kickOutFactor;

    private int zeroKickPenalty;

	private double epsilon = 0.000001;

    public MembersComparator(int goalsFactor, int ownGoalsFactor,
            int kickFactor, int kickOutFactor, int zeroKickPenalty) {
        super();
        this.kickFactor = kickFactor;
        this.ownGoalsFactor = ownGoalsFactor;
        this.otherGoalsFactor = goalsFactor;
        this.kickOutFactor = kickOutFactor;
        this.zeroKickPenalty = zeroKickPenalty;
    }

    public int compare(Member<?> o1, Member<?> o2) {
        return fitness(o2).compareTo(fitness(o1));
    }

    public Double fitness(Member<?> m) {
        double k = m.kickPerMatch();
        double og = m.ownGoalsPerMatch();
        double g = m.goalsPerMatch();
        double ko = m.kickOutPerMatch();
        int gf = this.otherGoalsFactor;
        int ogf = this.ownGoalsFactor;
        int kf = this.kickFactor;
        int kof = this.kickOutFactor;
        int zkp = 0;
        if (isZero(k)) {
            zkp = this.zeroKickPenalty;
        }
        double fit = kf * k + gf * g + ogf * og + kof * ko + zkp;
        return fit;
    }

	private boolean isZero(double k) {
		return k < epsilon || k > -epsilon ;
	}

}
