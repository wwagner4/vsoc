package vsoc.camps.goalkeeper.test;

import java.util.Iterator;
import java.util.List;

import atan.model.Controller;
import vsoc.VsocNotYetImplementedException;
import vsoc.behaviour.Behaviour;
import vsoc.behaviour.BehaviourController;
import vsoc.behaviour.GoAheadBehaviour;
import vsoc.camps.AbstractCamp;
import vsoc.camps.DefaultBehaviour;
import vsoc.camps.Member;
import vsoc.camps.goalkeeper.GoalkeeperDefaultBehaviour;
import vsoc.model.VsocPlayer;
import vsoc.util.resulttable.ResultTable;
import vsoc.view.FieldFrame;

@SuppressWarnings("serial")
public class GoalkeeperDefaultBehaviourTestCamp extends AbstractCamp {

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

    public Member getMember(int index) {
        return null;
    }

    public void setMaxGenerations(int i) {
        // to be ignored.
    }

    public boolean isFinished() {
        return false;
    }

    public ResultTable getResultTable() {
        return null;
    }

    protected List<Member> getMembers() {
        throw new VsocNotYetImplementedException();
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
