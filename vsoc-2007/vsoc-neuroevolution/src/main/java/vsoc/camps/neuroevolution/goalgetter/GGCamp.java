package vsoc.camps.neuroevolution.goalgetter;

import java.util.*;
import java.util.function.Function;

import org.apache.log4j.Logger;

import atan.model.Controller;
import vsoc.behaviour.*;
import vsoc.camps.neuroevolution.AbstractNeuroevolutionCamp;
import vsoc.genetic.*;
import vsoc.server.VsocPlayer;
import vsoc.util.VsocUtil;

/**
 * Camp for breeding goal getters. Goal for the members is to shoot as many
 * goals as possible.
 * 
 */
public class GGCamp extends AbstractNeuroevolutionCamp<GGMember, VectorFunction> {

	private static Logger log = Logger.getLogger(GGCamp.class);

	private static final long serialVersionUID = 0L;

	private double mutationRate = 0.02;

	private int kickFactor = 1;

	private int kickOutFactor = -5;

	private int ownGoalFactor = -100;

	private int goalFactor = 100;

	private SelectionPolicy<VectorFunction> selPoli;

	private Crosser<VectorFunction> crosser;

	private int zeroKickPenalty = -100;

	public void setKickFactor(int kickFactor) {
		this.kickFactor = kickFactor;
	}

	public int getKickFactor() {
		return this.kickFactor;
	}

	public void setKickOutFactor(int kickOutFactor) {
		this.kickOutFactor = kickOutFactor;
	}

	public int getKickOutFactor() {
		return this.kickOutFactor;
	}

	public void setMutationRate(double mutationRate) {
		this.mutationRate = mutationRate;
	}

	public double getMutationRate() {
		return this.mutationRate;
	}

	public void setGoalFactor(int otherGoalFactor) {
		this.goalFactor = otherGoalFactor;
	}

	public int getGoalFactor() {
		return this.goalFactor;
	}

	public void setOwnGoalFactor(int ownGoalFactor) {
		this.ownGoalFactor = ownGoalFactor;
	}

	public int getOwnGoalFactor() {
		return this.ownGoalFactor;
	}

	public void setZeroKickPenalty(int zeroKickPenalty) {
		this.zeroKickPenalty = zeroKickPenalty;
	}

	public int getZeroKickPenalty() {
		return this.zeroKickPenalty;
	}

	public int getMemberCount() {
		return getMembers().size();
	}

	public SelectionPolicy<VectorFunction> getSelPoli() {
		return this.selPoli;
	}

	public void setSelPoli(SelectionPolicy<VectorFunction> selPoli) {
		this.selPoli = selPoli;
	}

	public void setCrosser(Crosser<VectorFunction> crosser) {
		this.crosser = crosser;
	}

	public Crosser<VectorFunction> getCrosser() {
		return crosser;
	}

	protected int eastPlayerCount() {
		return 3;
	}

	protected int westPlayerCount() {
		return 3;
	}

	protected GGMember createMember() {
		return new GGMember();
	}

	protected Behaviour createBehaviour(VectorFunction vf) {
		VectorFunctionBehaviour<VectorFunction> behav = new VectorFunctionBehaviour<VectorFunction>(vf,
		    new RetinaSensorsToVector());
		return new GGBeforeKickoffBehaviour(behav);
	}

	protected void createNextGeneration() {
		logGenerationInfo();
		super.createNextGeneration();
	}

	protected Comparator<GGMember> createMemberComparator() {
		return new GGMembersComparator(this.goalFactor, this.ownGoalFactor, this.kickFactor, this.kickOutFactor,
		    this.zeroKickPenalty);
	}

	protected void updateMembersAfterMatch() {
		int eastGoals = 0;
		int westGoals = 0;
		List<GGMember> eastMembers = new ArrayList<>();
		List<GGMember> westMembers = new ArrayList<>();
		for (VsocPlayer p : getServer().getPlayersEast()) {
			eastGoals += p.getOtherGoalCount();
			getMemberByControlSystem(p.getController()).ifPresent(m -> {
				eastMembers.add(m);
				updateMemberFromPlayer(p, m);
			});
		}
		for (VsocPlayer p : getServer().getPlayersWest()) {
			westGoals += p.getOtherGoalCount();
			getMemberByControlSystem(p.getController()).ifPresent(m -> {
				westMembers.add(m);
				updateMemberFromPlayer(p, m);
			});
		}
		for (GGMember mem : eastMembers) {
			mem.increaseReceivedGoalsCount(westGoals);
		}
		for (GGMember mem : westMembers) {
			mem.increaseReceivedGoalsCount(eastGoals);
		}
	}

	protected void addProperties(Properties props) {
		super.addProperties(props);
		props.setProperty("mutation rate", VsocUtil.current().format(this.mutationRate));
		props.setProperty("kick factor", VsocUtil.current().format(this.kickFactor));
		props.setProperty("kick out factor", VsocUtil.current().format(this.kickOutFactor));
		props.setProperty("goal factor", VsocUtil.current().format(this.goalFactor));
		props.setProperty("own goal factor", VsocUtil.current().format(this.ownGoalFactor));
		props.setProperty("zero kick penalty", VsocUtil.current().format(this.zeroKickPenalty));

	}

	private void logGenerationInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append("diversity=");
		sb.append(VsocUtil.current().format(diversity()));
		sb.append(" kickCount=");
		sb.append(VsocUtil.current().format(mean(GGMember::kickPerMatch)));
		sb.append(" kickOutCount=");
		sb.append(VsocUtil.current().format(mean(GGMember::kickOutPerMatch)));
		sb.append(" goalCount=");
		sb.append(VsocUtil.current().format(mean(GGMember::goalsPerMatch)));
		sb.append(" ownGoalCount=");
		sb.append(VsocUtil.current().format(mean(GGMember::ownGoalsPerMatch)));
		log.info("New generation. generationCount:" + getGenerationsCount() + " info:<" + sb + ">");
	}

	private Optional<GGMember> getMemberByControlSystem(Controller c) {
		for (GGMember m : getMembers()) {
			if (m.getController() == c)
				return Optional.of(m);
		}
		return Optional.empty();
	}

	private double mean(Function<GGMember, Double> f) {
		List<GGMember> mems = getMembers();
		assert (!mems.isEmpty());
		double goals = 0.0;
		int count = 0;
		for (GGMember mem : mems) {
			goals += f.apply(mem);
			count++;
		}
		return goals / count;
	}

	private void updateMemberFromPlayer(VsocPlayer p, GGMember m) {
		m.increaseMatchCount();
		m.increaseKickCount(p.getKickCount());
		m.increaseKickOutCount(p.getKickOutCount());
		m.increaseOtherGoalsCount(p.getOtherGoalCount());
		m.increaseOwnGoalsCount(p.getOwnGoalCount());
	}

}