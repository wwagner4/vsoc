package vsoc.camps;

/**
 * Runns a Camp in a seperate Thread
 */
import java.util.*;

import org.apache.log4j.Logger;

import atan.model.*;
import vsoc.genetic.Crosser;
import vsoc.nn.Net;
import vsoc.server.*;
import vsoc.util.*;

public abstract class AbstractCamp<M extends Member<?>, N extends Net> implements Camp<M> {

	private static final long serialVersionUID = 0L;

	private static Logger log = Logger.getLogger(AbstractCamp.class);

	protected final Random ran = new Random();
	
	private transient Server server = null;
	
	private int matchCount = 0;

	private int generationsCount = 0;

	protected int maxGenerations;

	private int stepsPerMatch = 600;

	private int matchesPerGeneration = 150;

	public void run() {
		while (true) {
			takeOneStep();
		}
	}
	
	public void takeOneStep() {
		if (log.isDebugEnabled()) {
			log.debug("Started running a new match. matchCount:" + this.matchCount + " generationCount:"
					+ this.generationsCount);
		}
		runMatch();
		this.matchCount++;
		updateMembersAfterMatch();
		if (this.matchCount >= this.matchesPerGeneration) {
			this.matchCount = 0;
			this.generationsCount++;
			createNextGeneration(getCrosser());
		}
	}

	abstract protected Crosser<N> getCrosser();

	abstract protected void createNextGeneration(Crosser<N> crosser);

	public boolean isFinished() {
		return getGenerationsCount() >= this.maxGenerations;
	}

	private void runMatch() {
		getServer().reset();
		initPlayersForMatch();
		getServer().getBall().setPosition(new Vec2D(0, 0));
		for (int i = 0; i < this.stepsPerMatch; i++) {
			getServer().takeStep();
		}
	}

	protected abstract void initPlayersForMatch();

	public Server getServer() {
		if (this.server == null) {
			this.server = createServer();
		}
		return this.server;
	}

	protected abstract int eastPlayerCount();

	protected abstract int westPlayerCount();

	private Server createServer() {
		DefaultServerFactory fac = new DefaultServerFactory();
		fac.setEastPlayerCount(eastPlayerCount());
		fac.setWestPlayerCount(westPlayerCount());
		return fac.createServer();
	}

	public int getStepsPerMatch() {
		return this.stepsPerMatch;
	}

	public void setStepsPerMatch(int stepsPerMatch) {
		this.stepsPerMatch = stepsPerMatch;
	}

	public int getGenerationsCount() {
		return this.generationsCount;
	}

	public Properties getProperties() {
		Properties re = new Properties();
		addProperties(re);
		return re;
	}

	public int getMatchesPerGeneration() {
		return this.matchesPerGeneration;
	}

	public void setMatchesPerGeneration(int matchesPerGeneration) {
		this.matchesPerGeneration = matchesPerGeneration;
	}

	public void setMaxGenerations(int maxGenerations) {
		this.maxGenerations = maxGenerations;
	}

	public void setMaxGenerations(String maxGenerations) {
		this.maxGenerations = Integer.parseInt(maxGenerations);
	}

	public int getMatchCount() {
		return this.matchCount;
	}

	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}

	public int getMaxGenerations() {
		return this.maxGenerations;
	}

	public void setGenerationsCount(int generationsCount) {
		this.generationsCount = generationsCount;
	}

	protected abstract List<M> getMembers();
	
    public M getMember(int index) {
        return getMembers().get(index);
    }

	protected void setRandomPosition(Player p) {
		p.move(ran.nextInt(100) - 50, ran.nextInt(60) - 30);
		p.turn(ran.nextInt(360) - 180);
	}

	private void updateMembersAfterMatch() {
		int eastGoals = 0;
		ArrayList<M> eastMembers = new ArrayList<>();
		int westGoals = 0;
		ArrayList<M> westMembers = new ArrayList<>();
		{
			Iterator<VsocPlayer> it = getServer().getPlayersEast().iterator();
			while (it.hasNext()) {
				VsocPlayer p = it.next();
				eastGoals += p.getOtherGoalCount();
				M m = getMemberByControlSystem(getMembers(), p.getController());
				if (m != null) {
					eastMembers.add(m);
					updateMemberFromPlayer(p, m);
				}
			}
		}
		{
			Iterator<VsocPlayer> it = getServer().getPlayersWest().iterator();
			while (it.hasNext()) {
				VsocPlayer p = it.next();
				westGoals += p.getOtherGoalCount();
				M m = getMemberByControlSystem(getMembers(), p.getController());
				if (m != null) {
					westMembers.add(m);
					updateMemberFromPlayer(p, m);
				}
			}
		}
		{
			Iterator<M> iter = eastMembers.iterator();
			while (iter.hasNext()) {
				M mem = iter.next();
				mem.increaseReceivedGoalsCount(westGoals);
			}
		}
		{
			Iterator<M> iter = westMembers.iterator();
			while (iter.hasNext()) {
				M mem = iter.next();
				mem.increaseReceivedGoalsCount(eastGoals);
			}
		}
	}

	protected abstract void updateMemberFromPlayer(VsocPlayer player, M member);

	protected void addProperties(Properties re) {
		re.setProperty("steps per match", VsocUtil.current().format(this.stepsPerMatch));
		re.setProperty("max generations", "" + this.maxGenerations);
		re.setProperty("matches per generation", VsocUtil.current().format(this.matchesPerGeneration));
	}

	protected RandomIndexSelector createSelector(int membersCount, int playersCount) {
		RandomIndexSelector sel;
		try {
			sel = new RandomIndexSelector(0, membersCount - 1, playersCount);
		} catch (IllegalStateException e) {
			throw new IllegalStateException(
					"Members count (=" + membersCount + ") too small for players count (=" + playersCount + ").", e);
		}
		return sel;
	}

	protected M getMemberByControlSystem(List<M> mems, Controller c) {
		Iterator<M> i = mems.iterator();
		while (i.hasNext()) {
			M m = i.next();
			if (m.getController() == c)
				return m;
		}
		return null;
	}

	protected double kicks(List<M> mems) {
		double kicks = 0.0;
		int count = 0;
		Iterator<M> iter = mems.iterator();
		while (iter.hasNext()) {
			M mem = iter.next();
			kicks += mem.kickPerMatch();
			count++;
		}
		return kicks / count;
	}

	protected double kickOuts(List<M> mems) {
		double kicks = 0.0;
		int count = 0;
		Iterator<M> iter = mems.iterator();
		while (iter.hasNext()) {
			M mem = iter.next();
			kicks += mem.kickOutPerMatch();
			count++;
		}
		return kicks / count;
	}

	protected double goalsReceived(List<M> mems) {
		double re = 0.0;
		int count = 0;
		Iterator<M> iter = mems.iterator();
		while (iter.hasNext()) {
			M mem = iter.next();
			re += mem.receivedGoalsPerMatch();
			count++;
		}
		return re / count;
	}

	protected double goals(List<M> mems) {
		double goals = 0.0;
		int count = 0;
		Iterator<M> iter = mems.iterator();
		while (iter.hasNext()) {
			M mem = iter.next();
			goals += mem.goalsPerMatch();
			count++;
		}
		return goals / count;
	}

	protected double ownGoals(List<M> mems) {
		double goals = 0.0;
		int count = 0;
		Iterator<M> iter = mems.iterator();
		while (iter.hasNext()) {
			M mem = iter.next();
			goals += mem.ownGoalsPerMatch();
			count++;
		}
		return goals / count;
	}

}