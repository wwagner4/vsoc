package vsoc.camps.goalkeeper;

import java.util.Comparator;

import org.apache.log4j.Logger;

import vsoc.camps.Member;
import vsoc.util.VsocUtil;

public class GKMembersComparator implements Comparator {

    private static Logger log = Logger.getLogger(GKMembersComparator.class);

    public GKMembersComparator() {
        super();
    }

    public int compare(Object o1, Object o2) {
        Member m1 = (Member) o1;
        Member m2 = (Member) o2;
        return fitness(m2).compareTo(fitness(m1));
    }

    public Double fitness(Member m) {
        VsocUtil vutil = VsocUtil.current();
        double rg = m.receivedGoalsPerMatch();
        double k = m.kickPerMatch();
        double fit = 0;
//        fit = rg * (-1) + k * 10;
        fit = k * 10;
        if (log.isDebugEnabled()) {
            log.debug("[fitness] fit=" + vutil.format(fit)
                    + "\n\tvalues<rg=" + vutil.format(rg) + " k="
                    + vutil.format(k) + ">");
        }
        return new Double(fit);
    }

}
