package vsoc.camps.neuroevolution;

import java.util.*;

import vsoc.camps.*;
import vsoc.genetic.*;
import vsoc.nn.Net;
import vsoc.server.VsocPlayer;

abstract public class AbstractNeuroevolutionCamp extends AbstractCamp<Member<NetBehaviourController<Net>>, Net> {
	
	private static final long serialVersionUID = 1L;

	protected void basicCreateNextGeneration(List<Member<NetBehaviourController<Net>>> mems, Crosser<Net> crosser, Comparator<Member<?>> comp, double mutRate,
			SelectionPolicy<Net> selPoli, CrossableFactory<Net> crossableFactory) {
		List<Net> pop = sortedNetsFromMembers(mems, comp);
		List<Net> childNets = selPoli.createNextGeneration(pop, crosser, crossableFactory, mutRate);
		addNetsToMembers(mems, childNets);
	}
	
	@Override
	protected void updateMemberFromPlayer(VsocPlayer p, Member<NetBehaviourController<Net>> m) {
		m.increaseMatchCount();
		m.increaseKickCount(p.getKickCount());
		m.increaseKickOutCount(p.getKickOutCount());
		m.increaseOtherGoalsCount(p.getOtherGoalCount());
		m.increaseOwnGoalsCount(p.getOwnGoalCount());
	}

	private void addNetsToMembers(List<Member<NetBehaviourController<Net>>> mems, List<Net> nextPop) {
		Iterator<Member<NetBehaviourController<Net>>> iter = mems.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Member<NetBehaviourController<Net>> mem = iter.next();
			mem.reset();
			Net net = nextPop.get(index);
			mem.getController().setNet(net);
			index++;
		}
	}
	
	private List<Net> sortedNetsFromMembers(List<Member<NetBehaviourController<Net>>> mems, Comparator<Member<?>> comp) {
		Collections.sort(mems, comp);
		List<Net> pop = new ArrayList<>();
		Iterator<Member<NetBehaviourController<Net>>> iter = mems.iterator();
		while (iter.hasNext()) {
			Member<NetBehaviourController<Net>> mem = iter.next();
			Net net = mem.getController().getNet();
			pop.add(net);
		}
		return pop;
	}
	
	protected double diversity(List<Member<NetBehaviourController<Net>>> mems, Crosser<Net> crosser) {
		int count = 0;
		double distSum = 0.0;
		Iterator<Member<NetBehaviourController<Net>>> i = mems.iterator();
		while (i.hasNext()) {
			Member<NetBehaviourController<Net>> m1 = i.next();
			Member<NetBehaviourController<Net>> m2 = mems.get(ran.nextInt(mems.size()));
			distSum += crosser.distance(m1.getController().getNet(), m2.getController().getNet());
			count++;
		}
		return distSum / count;
	}



}
