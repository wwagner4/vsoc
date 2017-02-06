package vsoc.camps.goalkeeper;

import java.util.*;

import atan.model.Controller;
import vsoc.behaviour.*;
import vsoc.camps.*;
import vsoc.camps.neuroevolution.*;
import vsoc.camps.neuroevolution.goalkeeper.GoalkeeperDefaultBehaviour;
import vsoc.server.VsocPlayer;
import vsoc.server.gui.FieldFrame;

public class GoalkeeperDefaultBehaviourTestCamp extends AbstractNeuroevolutionCamp {

	private static final long serialVersionUID = 1L;

    public GoalkeeperDefaultBehaviourTestCamp() {
        super();
    }

    public static void main(String[] args) {
        GoalkeeperDefaultBehaviourTestCamp camp = new GoalkeeperDefaultBehaviourTestCamp();
        FieldFrame.open(camp, "GoalkeeperDefaultBehaviourTestCamp");
    }

    protected void initPlayersForMatch() {
        Iterator<VsocPlayer> players = getServer().getPlayers().iterator();
        while (players.hasNext()) {
            VsocPlayer p = players.next();
            p.setController(createControlSystem());
            setRandomPosition(p);
        }
    }

    private Controller createControlSystem() {
        return new BehaviourController(createBehaviour());
    }

    private Behaviour createBehaviour() {
        GoAheadBehaviour b1 = new GoAheadBehaviour();
        GoalkeeperDefaultBehaviour b2 = new GoalkeeperDefaultBehaviour(b1);
        return new DefaultBehaviour(b2);
    }

    protected void updateMembersAfterMatch() {
        // Nothing to be done.
    }

    protected void createNextGeneration() {
        // Nothing to be done.
        
    }

    @Override
    public void setMaxGenerations(int i) {
        // to be ignored.
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    protected List<Member<NetBehaviourController>> getMembers() {
    	throw new IllegalStateException("Not yet implemented");
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
