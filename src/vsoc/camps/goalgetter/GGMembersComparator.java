package vsoc.camps.goalgetter;

import java.util.Comparator;

import org.apache.log4j.Logger;

import vsoc.camps.Member;
import vsoc.util.VsocUtil;

public class GGMembersComparator implements Comparator<Member> {

    private static Logger log = Logger.getLogger(GGMembersComparator.class);

    private int kickFactor;

    private int ownGoalsFactor;

    private int otherGoalsFactor;

    private int kickOutFactor;

    private int zeroKickPenalty;

	private double epsilon = 0.000001;

    public GGMembersComparator(int goalsFactor, int ownGoalsFactor,
            int kickFactor, int kickOutFactor, int zeroKickPenalty) {
        super();
        this.kickFactor = kickFactor;
        this.ownGoalsFactor = ownGoalsFactor;
        this.otherGoalsFactor = goalsFactor;
        this.kickOutFactor = kickOutFactor;
        this.zeroKickPenalty = zeroKickPenalty;
    }

    public int compare(Member o1, Member o2) {
        return fitness(o2).compareTo(fitness(o1));
    }

    public Double fitness(Member m) {
        VsocUtil u = VsocUtil.current();
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
        if (log.isDebugEnabled()) {
            log.debug("[fitness] fit=" + u.format(fit) + "\n\tvalues<k="
                    + u.format(k) + " g=" + u.format(og) + " og=" + u.format(g)
                    + " ko=" + u.format(ko) + " zkp=" + zkp
                    + ">\n\tfactores<k=" + kf + " g=" + gf + " og=" + ogf
                    + " ko=" + kof + ">");
        }
        return fit;
    }

	private boolean isZero(double k) {
		return k < epsilon || k > -epsilon ;
	}

}
