package vsoc.camps.neuroevolution.goalgetter;

import java.util.*;

import org.apache.log4j.Logger;

import vsoc.behaviour.*;
import vsoc.camps.*;
import vsoc.camps.neuroevolution.*;
import vsoc.genetic.*;
import vsoc.server.VsocPlayer;
import vsoc.util.*;

/**
 * Camp for breeding goal getters. Goal for the members is to shoot as many
 * goals as possible.
 * 
 */
public class GGCamp extends AbstractNeuroevolutionCamp {

	private static Logger log = Logger.getLogger(GGCamp.class);

    private static final long serialVersionUID = 0L;

    private List<Member<VectorFunctionBehaviourController<VectorFunction>>> members;

    private double mutationRate = 0.02;

    private int kickFactor = 1;

    private int kickOutFactor = -5;

    private int ownGoalFactor = -100;

    private int goalFactor = 100;

    private SelectionPolicy<VectorFunction> selPoli;

    private Crosser<VectorFunction> crosser;
    
	private int zeroKickPenalty = -100;

    public int getKickFactor() {
        return this.kickFactor;
    }

    public void setKickFactor(int kickFactor) {
        this.kickFactor = kickFactor;
    }

    public int getKickOutFactor() {
        return this.kickOutFactor;
    }

    public void setKickOutFactor(int kickOutFactor) {
        this.kickOutFactor = kickOutFactor;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public int getGoalFactor() {
        return this.goalFactor;
    }

    public void setGoalFactor(int otherGoalFactor) {
        this.goalFactor = otherGoalFactor;
    }

    public int getOwnGoalFactor() {
        return this.ownGoalFactor;
    }

    public void setOwnGoalFactor(int ownGoalFactor) {
        this.ownGoalFactor = ownGoalFactor;
    }

    public int getMemberCount() {
        return getMembers().size();
    }

    protected String preCreateNextGenerationInfo(Crosser<VectorFunction> crosser) {
        StringBuilder sb = new StringBuilder();
        sb.append("diversity=");
        sb.append(VsocUtil.current().format(diversity(getMembers(), crosser)));
        sb.append(" kickCount=");
        sb.append(VsocUtil.current().format(kicks(getMembers())));
        sb.append(" kickOutCount=");
        sb.append(VsocUtil.current().format(kickOuts(getMembers())));
        sb.append(" goalCount=");
        sb.append(VsocUtil.current().format(goals(getMembers())));
        sb.append(" ownGoalCount=");
        sb.append(VsocUtil.current().format(ownGoals(getMembers())));
        return sb.toString();
    }

    public SelectionPolicy<VectorFunction> getSelPoli() {
        return this.selPoli;
    }

    public void setSelPoli(SelectionPolicy<VectorFunction> selPoli) {
        this.selPoli = selPoli;
    }

    public int getMaxGenerations() {
        return this.maxGenerations;
    }

    protected void addProperties(Properties re) {

        super.addProperties(re);
        re.setProperty("mutation rate", VsocUtil.current().format(this.mutationRate));

        re.setProperty("kick factor", VsocUtil.current().format(this.kickFactor));
        re.setProperty("kick out factor", VsocUtil.current().format(this.kickOutFactor));

        re.setProperty("goal factor", VsocUtil.current().format(this.goalFactor));
        re.setProperty("own goal factor", VsocUtil.current().format(this.ownGoalFactor));
        re.setProperty("zero kick penalty", VsocUtil.current().format(this.zeroKickPenalty));

    }

    public List<Member<VectorFunctionBehaviourController<VectorFunction>>> getMembers() {
        if (this.members == null) {
            List<Member<VectorFunctionBehaviourController<VectorFunction>>> mems = new ArrayList<>();
            List<VectorFunction> vfs = this.selPoli.createNewGeneration(this.crosser);
            Iterator<VectorFunction> iter = vfs.iterator();
            while (iter.hasNext()) {
                VectorFunction vf = iter.next();
                VectorFunctionBehaviourController<VectorFunction> ncs = new VectorFunctionBehaviourController<>(
                        createBehaviour(vf));
                ncs.setVectorFunction(vf);
                Member<VectorFunctionBehaviourController<VectorFunction>> mem = new Member<>();
                mem.setController(ncs);
                mem.reset();
                mems.add(mem);
            }
            this.members = mems;
        }
        return this.members;
    }

    public void setMembers(List<Member<VectorFunctionBehaviourController<VectorFunction>>> members) {
        this.members = members;
    }

    protected Behaviour createBehaviour(VectorFunction vf) {
        VectorFunctionRetinaBehaviour<VectorFunction> behav = new VectorFunctionRetinaBehaviour<VectorFunction>(vf);
        return new GGBeforeKickoffBehaviour(behav);
    }

    protected void initPlayersForMatch() {
        RandomIndexSelector sel = createSelector(getMembers().size(),
                getServer().getPlayersCount());
        Iterator<VsocPlayer> players = getServer().getPlayers().iterator();
        while (players.hasNext()) {
            int index = sel.next();
            Member<VectorFunctionBehaviourController<VectorFunction>> m = getMember(index);
            VsocPlayer p = (VsocPlayer) players.next();
            p.setController(m.getController());
            setRandomPosition(p);
        }
    }

    protected void createNextGeneration() {
        double diversity = diversity(getMembers(), crosser);
        double kicks = kicks(getMembers());
        double kickOuts = kickOuts(getMembers());
        double goals = goals(getMembers());
        double ownGoals = ownGoals(getMembers());
        createNextGenerationInfo(diversity, kicks, kickOuts, goals, ownGoals);
        Comparator<Member<?>> comp = new GGMembersComparator(this.goalFactor,
                this.ownGoalFactor, this.kickFactor, this.kickOutFactor, this.zeroKickPenalty );
        basicCreateNextGeneration(getMembers(), crosser, comp, this.mutationRate,
                this.selPoli);
    }

    private void createNextGenerationInfo(double diversity, double kicks,
            double kickOuts, double goals, double ownGoals) {
        StringBuilder sb = new StringBuilder();
        sb.append("diversity=");
        sb.append(VsocUtil.current().format(diversity));
        sb.append(" kickCount=");
        sb.append(VsocUtil.current().format(kicks));
        sb.append(" kickOutCount=");
        sb.append(VsocUtil.current().format(kickOuts));
        sb.append(" goalCount=");
        sb.append(VsocUtil.current().format(goals));
        sb.append(" ownGoalCount=");
        sb.append(VsocUtil.current().format(ownGoals));
        log.info("New generation. generationCount:" + getGenerationsCount()
                + " info:<" + sb + ">");
    }

    public int getZeroKickPenalty() {
        return this.zeroKickPenalty;
    }

    public void setZeroKickPenalty(int zeroKickPenalty) {
        this.zeroKickPenalty = zeroKickPenalty;
    }

	protected int eastPlayerCount() {
		return 3;
	}

	protected int westPlayerCount() {
		return 3;
	}

	protected Crosser<VectorFunction> getCrosser() {
		return this.crosser;
	}

    public void setCrosser(Crosser<VectorFunction> crosser) {
		this.crosser = crosser;
	}

}