package vsoc.camps.neuroevolution;

import java.util.*;

import vsoc.camps.*;
import vsoc.genetic.*;
import vsoc.server.VsocPlayer;

abstract public class AbstractNeuroevolutionCamp extends AbstractCamp<Member<NetBehaviourController<VectorFunction>>, VectorFunction> {
	
	private static final long serialVersionUID = 1L;

	protected void basicCreateNextGeneration(List<Member<NetBehaviourController<VectorFunction>>> mems, Crosser<VectorFunction> crosser, Comparator<Member<?>> comp, double mutRate,
			SelectionPolicy<VectorFunction> selPoli) {
		List<VectorFunction> pop = sortedNetsFromMembers(mems, comp);
		List<VectorFunction> childNets = selPoli.createNextGeneration(pop, crosser, mutRate);
		addNetsToMembers(mems, childNets);
	}
	
	@Override
	protected void updateMemberFromPlayer(VsocPlayer p, Member<NetBehaviourController<VectorFunction>> m) {
		m.increaseMatchCount();
		m.increaseKickCount(p.getKickCount());
		m.increaseKickOutCount(p.getKickOutCount());
		m.increaseOtherGoalsCount(p.getOtherGoalCount());
		m.increaseOwnGoalsCount(p.getOwnGoalCount());
	}

	private void addNetsToMembers(List<Member<NetBehaviourController<VectorFunction>>> mems, List<VectorFunction> nextPop) {
		Iterator<Member<NetBehaviourController<VectorFunction>>> iter = mems.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Member<NetBehaviourController<VectorFunction>> mem = iter.next();
			mem.reset();
			VectorFunction net = nextPop.get(index);
			mem.getController().setNet(net);
			index++;
		}
	}
	
	private List<VectorFunction> sortedNetsFromMembers(List<Member<NetBehaviourController<VectorFunction>>> mems, Comparator<Member<?>> comp) {
		Collections.sort(mems, comp);
		List<VectorFunction> pop = new ArrayList<>();
		Iterator<Member<NetBehaviourController<VectorFunction>>> iter = mems.iterator();
		while (iter.hasNext()) {
			Member<NetBehaviourController<VectorFunction>> mem = iter.next();
			VectorFunction net = mem.getController().getNet();
			pop.add(net);
		}
		return pop;
	}
	
	protected double diversity(List<Member<NetBehaviourController<VectorFunction>>> mems, Crosser<VectorFunction> crosser) {
		int count = 0;
		double distSum = 0.0;
		Iterator<Member<NetBehaviourController<VectorFunction>>> i = mems.iterator();
		while (i.hasNext()) {
			Member<NetBehaviourController<VectorFunction>> m1 = i.next();
			Member<NetBehaviourController<VectorFunction>> m2 = mems.get(ran.nextInt(mems.size()));
			distSum += crosser.distance(m1.getController().getNet(), m2.getController().getNet());
			count++;
		}
		return distSum / count;
	}



}
