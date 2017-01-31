package vsoc.camps.goalkeeper.test;

import java.util.Iterator;
import java.util.List;

import vsoc.VsocNotYetImplementedException;
import vsoc.behaviour.Behaviour;
import vsoc.behaviour.BehaviourController;
import vsoc.behaviour.GoAheadBehaviour;
import vsoc.camps.AbstractCamp;
import vsoc.camps.DefaultBehaviour;
import vsoc.camps.Member;
import vsoc.camps.goalkeeper.GoalkeeperDefaultBehaviour;
import vsoc.model.DefaultServerFactory;
import vsoc.model.Server;
import vsoc.model.VsocPlayer;
import vsoc.util.resulttable.ResultTable;
import vsoc.view.FieldFrame;
import atan.model.Controller;

public class GoalkeeperDefaultBehaviourTestCamp extends AbstractCamp {

    public GoalkeeperDefaultBehaviourTestCamp() {
        super();
    }

    public static void main(String[] args) {
        GoalkeeperDefaultBehaviourTestCamp camp = new GoalkeeperDefaultBehaviourTestCamp();
        DefaultServerFactory sfac = new DefaultServerFactory();
        FieldFrame.open(camp, "GoalkeeperDefaultBehaviourTestCamp");
    }

    protected void initPlayersForMatch() {
        Iterator players = getServer().getPlayers().iterator();
        while (players.hasNext()) {
            VsocPlayer p = (VsocPlayer) players.next();
            p.setController(createControlSystem());
            setRandomPosition(p);
        }
    }

    private Controller createControlSystem() {
        BehaviourController cont = new BehaviourController(createBehaviour());
        return cont;
    }

    private Behaviour createBehaviour() {
        GoAheadBehaviour b1 = new GoAheadBehaviour();
        GoalkeeperDefaultBehaviour b2 = new GoalkeeperDefaultBehaviour(b1);
        DefaultBehaviour b3 = new DefaultBehaviour(b2);
        return b3;
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

    protected List getMembers() {
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
