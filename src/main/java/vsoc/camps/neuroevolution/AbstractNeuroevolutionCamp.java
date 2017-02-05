package vsoc.camps.neuroevolution;

import java.util.*;

import vsoc.camps.*;
import vsoc.genetic.*;
import vsoc.nn.Net;
import vsoc.server.VsocPlayer;

abstract public class AbstractNeuroevolutionCamp extends AbstractCamp<Member<NetBehaviourController>> {
	
	private static final long serialVersionUID = 1L;

	protected void basicCreateNextGeneration(List<Member<NetBehaviourController>> mems, Comparator<Member<NetBehaviourController>> comp, double mutRate,
			SelectionPolicy<Net> selPoli, CrossableFactory<Net> crossableFactory) {
		List<Net> pop = sortedNetsFromMembers(mems, comp);
		List<Net> childNets = selPoli.createNextGeneration(pop, crossableFactory, mutRate);
		addNetsToMembers(mems, childNets);
	}
	
	@Override
	protected void updateMemberFromPlayer(VsocPlayer p, Member<NetBehaviourController> m) {
		m.increaseMatchCount();
		m.increaseKickCount(p.getKickCount());
		m.increaseKickOutCount(p.getKickOutCount());
		m.increaseOtherGoalsCount(p.getOtherGoalCount());
		m.increaseOwnGoalsCount(p.getOwnGoalCount());
	}

	private void addNetsToMembers(List<Member<NetBehaviourController>> mems, List<Net> nextPop) {
		Iterator<Member<NetBehaviourController>> iter = mems.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Member<NetBehaviourController> mem = iter.next();
			mem.reset();
			Net net = nextPop.get(index);
			mem.getController().setNet(net);
			index++;
		}
	}
	
	private List<Net> sortedNetsFromMembers(List<Member<NetBehaviourController>> mems, Comparator<Member<NetBehaviourController>> comp) {
		Collections.sort(mems, comp);
		List<Net> pop = new ArrayList<>();
		Iterator<Member<NetBehaviourController>> iter = mems.iterator();
		while (iter.hasNext()) {
			Member<NetBehaviourController> mem = iter.next();
			Net net = mem.getController().getNet();
			pop.add(net);
		}
		return pop;
	}
	
	protected double diversity(List<Member<NetBehaviourController>> mems) {
		int count = 0;
		double distSum = 0.0;
		Iterator<Member<NetBehaviourController>> i = mems.iterator();
		while (i.hasNext()) {
			Member<NetBehaviourController> m1 = i.next();
			Member<NetBehaviourController> m2 = mems.get(ran.nextInt(mems.size()));
			distSum += m1.getController().getNet().distance(m2.getController().getNet());
			count++;
		}
		return distSum / count;
	}



}
