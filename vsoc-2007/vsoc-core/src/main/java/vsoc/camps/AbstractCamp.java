package vsoc.camps;

/**
 * Runns a Camp in a seperate Thread
 */
import java.util.*;

import atan.model.*;
import vsoc.behaviour.VectorFunction;
import vsoc.server.*;
import vsoc.util.*;

public abstract class AbstractCamp<M extends Member<?>, N extends VectorFunction> implements Camp<M> {

	private static final long serialVersionUID = 0L;

	private transient Server server = null;

	private int matchCount = 0;

	private int generationsCount = 0;

	private int maxGenerations = 100;

	private int stepsPerMatch = 600;

	private int matchesPerGeneration = 150;

	protected abstract void initPlayersForMatch();

	protected abstract int eastPlayerCount();

	protected abstract int westPlayerCount();

	abstract protected void createNextGeneration();

	protected abstract List<M> getMembers();

	abstract protected void updateMembersAfterMatch();

	public void run() {
		while (true) {
			takeOneStep();
		}
	}

	public void takeOneStep() {
		runMatch();
		this.matchCount++;
		updateMembersAfterMatch();
		if (this.matchCount >= this.matchesPerGeneration) {
			this.matchCount = 0;
			this.generationsCount++;
			createNextGeneration();
		}
	}

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

	public Server getServer() {
		if (this.server == null) {
			this.server = createServer();
		}
		return this.server;
	}

	private Server createServer() {
		return ServerUtil.current().createServer(eastPlayerCount(), westPlayerCount());
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

	public Controller getController(int index) {
		return getMembers().get(index).getController();
	}

}