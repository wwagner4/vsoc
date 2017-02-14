package vsoc.camps.neuroevolution.goalkeeper;

import java.util.Comparator;

import org.apache.log4j.Logger;

import vsoc.camps.Member;
import vsoc.util.VsocUtil;

public class GKMembersComparator implements Comparator<Member<?>> {

    private static Logger log = Logger.getLogger(GKMembersComparator.class);

    public GKMembersComparator() {
        super();
    }

    public int compare(Member<?> o1, Member<?> o2) {
        return fitness(o2).compareTo(fitness(o1));
    }

    public Double fitness(Member<?> m) {
        VsocUtil vutil = VsocUtil.current();
        double rg = m.receivedGoalsPerMatch();
        double k = m.kickPerMatch();
        double fit = k * 10;
        if (log.isDebugEnabled()) {
            log.debug("[fitness] fit=" + vutil.format(fit)
                    + "\n\tvalues<rg=" + vutil.format(rg) + " k="
                    + vutil.format(k) + ">");
        }
        return fit;
    }

}
