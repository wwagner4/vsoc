package vsoc.camps.goalgetter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import vsoc.behaviour.Behaviour;
import vsoc.camps.AbstractCamp;
import vsoc.camps.BehaviourNeuroControlSystem;
import vsoc.camps.DefaultBehaviour;
import vsoc.camps.Member;
import vsoc.camps.NetBehaviour;
import vsoc.genetic.CrossableFactory;
import vsoc.genetic.SelectionPolicy;
import vsoc.nn.Net;
import vsoc.server.VsocPlayer;
import vsoc.util.RandomIndexSelector;
import vsoc.util.VsocUtil;

/**
 * Camp for breeding goal getters. Goal for the members is to shoot as many
 * goals as possible.
 * 
 */
public class GGCamp extends AbstractCamp {

    private static Logger log = Logger.getLogger(GGCamp.class);

    private static final long serialVersionUID = 0L;

    private List<Member> members = null;

    private double mutationRate = 0.02;

    private int kickFactor = 1;

    private int kickOutFactor = -5;

    private int ownGoalFactor = -100;

    private int goalFactor = 100;

    private SelectionPolicy<Net> selPoli = null;

    private CrossableFactory<Net> crossableFactory = null;

    private int zeroKickPenalty = -100;

    public GGCamp() {
        super();
    }

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

    protected String preCreateNextGenerationInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("diversity=");
        sb.append(VsocUtil.current().format(diversity(getMembers())));
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

    public CrossableFactory<Net> getCrossableFactory() {
        return this.crossableFactory;
    }

    public void setCrossableFactory(CrossableFactory<Net> crossableFactory) {
        this.crossableFactory = crossableFactory;
    }

    public SelectionPolicy<Net> getSelPoli() {
        return this.selPoli;
    }

    public void setSelPoli(SelectionPolicy<Net> selPoli) {
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

    public List<Member> getMembers() {
        if (this.members == null) {
            List<Member> mems = new ArrayList<>();
            List<Net> nets = this.selPoli.createNewGeneration(this.crossableFactory);
            Iterator<Net> iter = nets.iterator();
            while (iter.hasNext()) {
                Net net = iter.next();
                BehaviourNeuroControlSystem ncs = new BehaviourNeuroControlSystem(
                        createBehaviour(net));
                ncs.setNet(net);
                Member mem = new Member();
                mem.setNeuroControlSystem(ncs);
                mem.reset();
                mems.add(mem);
            }
            this.members = mems;
        }
        return this.members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    protected Behaviour createBehaviour(Net net) {
        NetBehaviour nBehav = new NetBehaviour(net);
        return new DefaultBehaviour(nBehav);
    }

    protected void initPlayersForMatch() {
        RandomIndexSelector sel = createSelector(getMembers().size(),
                getServer().getPlayersCount());
        Iterator<VsocPlayer> players = getServer().getPlayers().iterator();
        while (players.hasNext()) {
            int index = sel.next();
            Member m = getMember(index);
            VsocPlayer p = (VsocPlayer) players.next();
            p.setController(m.getNeuroControlSystem());
            setRandomPosition(p);
        }
    }

    public Member getMember(int index) {
        return (Member) getMembers().get(index);
    }

    protected void createNextGeneration() {
        double diversity = diversity(getMembers());
        double kicks = kicks(getMembers());
        double kickOuts = kickOuts(getMembers());
        double goals = goals(getMembers());
        double ownGoals = ownGoals(getMembers());
        createNextGenerationInfo(diversity, kicks, kickOuts, goals, ownGoals);
        writeResultTable(diversity, kicks, kickOuts, goals, ownGoals);
        Comparator<Member> comp = new GGMembersComparator(this.goalFactor,
                this.ownGoalFactor, this.kickFactor, this.kickOutFactor, this.zeroKickPenalty );
        basicCreateNextGeneration(getMembers(), comp, this.mutationRate,
                this.selPoli, this.crossableFactory);
    }

    private void writeResultTable(double diversity, double kicks,
            double kickOuts, double goals, double ownGoals) {
        if (this.resultTable != null) {
            this.resultTable.addNextSerialValue(new Integer(
                    getGenerationsCount()));
            this.resultTable.setValue(GGCampResultColumns.DIVERSITY.getName(), diversity);
            this.resultTable.setValue(GGCampResultColumns.GOALS.getName(), goals);
            this.resultTable.setValue(GGCampResultColumns.OWNGOALS.getName(), ownGoals);
            this.resultTable.setValue(GGCampResultColumns.KICKS.getName(), kicks);
            this.resultTable.setValue(GGCampResultColumns.KICKOUTS.getName(), kickOuts);
        }
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
        log.info("Select new members. selectionCount:" + getGenerationsCount()
                + " info:<" + sb + ">");
    }

    public int getZeroKickPenalty() {
        return this.zeroKickPenalty;
    }

    public void setZeroKickPenalty(int zeroKickPenalty) {
        this.zeroKickPenalty = zeroKickPenalty;
    }

	@Override
	protected int eastPlayerCount() {
		return 3;
	}

	@Override
	protected int westPlayerCount() {
		return 3;
	}

}