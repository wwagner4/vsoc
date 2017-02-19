package vsoc.camps.neuroevolution.goalgetter;

import java.util.Comparator;

public class GGMembersComparator implements Comparator<GGMember> {

	private int kickFactor;

	private int ownGoalsFactor;

	private int otherGoalsFactor;

	private int kickOutFactor;

	private int zeroKickPenalty;

	private double epsilon = 0.000001;

	public GGMembersComparator(int goalsFactor, int ownGoalsFactor, int kickFactor, int kickOutFactor,
	    int zeroKickPenalty) {
		super();
		this.kickFactor = kickFactor;
		this.ownGoalsFactor = ownGoalsFactor;
		this.otherGoalsFactor = goalsFactor;
		this.kickOutFactor = kickOutFactor;
		this.zeroKickPenalty = zeroKickPenalty;
	}

	public int compare(GGMember o1, GGMember o2) {
		return fitness(o2).compareTo(fitness(o1));
	}

	public Double fitness(GGMember m) {
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
		return k < epsilon || k > -epsilon;
	}

}
