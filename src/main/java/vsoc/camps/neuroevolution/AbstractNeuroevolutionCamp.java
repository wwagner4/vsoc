package vsoc.camps.neuroevolution;

import java.util.*;

import vsoc.camps.*;
import vsoc.genetic.*;
import vsoc.server.VsocPlayer;

abstract public class AbstractNeuroevolutionCamp extends AbstractCamp<Member<VectorFunctionBehaviourController<VectorFunction>>, VectorFunction> {
	
	private static final long serialVersionUID = 1L;

	protected void createNextGeneration(List<Member<VectorFunctionBehaviourController<VectorFunction>>> mems, Crosser<VectorFunction> crosser, Comparator<Member<?>> comp, double mutRate,
			SelectionPolicy<VectorFunction> selPoli) {
		List<VectorFunction> pop = sortedNetsFromMembers(mems, comp);
		List<VectorFunction> childNets = selPoli.createNextGeneration(pop, crosser, mutRate);
		addNetsToMembers(mems, childNets);
	}
	
	@Override
	protected void updateMemberFromPlayer(VsocPlayer p, Member<VectorFunctionBehaviourController<VectorFunction>> m) {
		m.increaseMatchCount();
		m.increaseKickCount(p.getKickCount());
		m.increaseKickOutCount(p.getKickOutCount());
		m.increaseOtherGoalsCount(p.getOtherGoalCount());
		m.increaseOwnGoalsCount(p.getOwnGoalCount());
	}

	private void addNetsToMembers(List<Member<VectorFunctionBehaviourController<VectorFunction>>> mems, List<VectorFunction> nextPop) {
		Iterator<Member<VectorFunctionBehaviourController<VectorFunction>>> iter = mems.iterator();
		int index = 0;
		while (iter.hasNext()) {
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = iter.next();
			mem.reset();
			VectorFunction vf = nextPop.get(index);
			mem.getController().setVectorFunction(vf);
			index++;
		}
	}
	
	private List<VectorFunction> sortedNetsFromMembers(List<Member<VectorFunctionBehaviourController<VectorFunction>>> mems, Comparator<Member<?>> comp) {
		Collections.sort(mems, comp);
		List<VectorFunction> pop = new ArrayList<>();
		Iterator<Member<VectorFunctionBehaviourController<VectorFunction>>> iter = mems.iterator();
		while (iter.hasNext()) {
			Member<VectorFunctionBehaviourController<VectorFunction>> mem = iter.next();
			VectorFunction vf = mem.getController().getVectorFunction();
			pop.add(vf);
		}
		return pop;
	}
	
	protected double diversity(List<Member<VectorFunctionBehaviourController<VectorFunction>>> mems, Crosser<VectorFunction> crosser) {
		int count = 0;
		double distSum = 0.0;
		Iterator<Member<VectorFunctionBehaviourController<VectorFunction>>> i = mems.iterator();
		while (i.hasNext()) {
			Member<VectorFunctionBehaviourController<VectorFunction>> m1 = i.next();
			Member<VectorFunctionBehaviourController<VectorFunction>> m2 = mems.get(ran.nextInt(mems.size()));
			distSum += crosser.distance(m1.getController().getVectorFunction(), m2.getController().getVectorFunction());
			count++;
		}
		return distSum / count;
	}



}
