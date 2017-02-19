package vsoc.camps.neuroevolution;

import java.util.*;
import java.util.stream.Collectors;

import atan.model.Player;
import vsoc.behaviour.*;
import vsoc.camps.*;
import vsoc.genetic.*;
import vsoc.server.VsocPlayer;
import vsoc.util.*;

abstract public class AbstractNeuroevolutionCamp<M extends Member<VectorFunctionBehaviourController<VectorFunction>>, V extends VectorFunction>
    extends AbstractCamp<M, V> {

	private final Random ran = new Random();

	private static final long serialVersionUID = 1L;

	private List<M> members;

	public Properties getProperties() {
		Properties props = new Properties();
		this.addProperties(props);
		return props;
	}

	protected void addProperties(Properties props) {
		props.setProperty("steps per match", VsocUtil.current().format(getStepsPerMatch()));
		props.setProperty("max generations", "" + getMaxGenerations());
		props.setProperty("matches per generation", VsocUtil.current().format(getMatchesPerGeneration()));
	}

	public List<M> getMembers() {
		if (this.members == null) {
			List<M> mems = new ArrayList<>();
			List<VectorFunction> vfs = getSelPoli().createNewGeneration(getCrosser());
			for (VectorFunction vf : vfs) {
				Behaviour behav = createBehaviour(vf);
				VectorFunctionBehaviourController<VectorFunction> ncs = new VectorFunctionBehaviourController<>(behav);
				ncs.setVectorFunction(vf);
				M mem = createMember();
				mem.setController(ncs);
				mem.reset();
				mems.add(mem);
			}
			this.members = mems;
		}
		return this.members;
	}

	protected void initPlayersForMatch() {
		RandomIndexSelector sel = createSelector(getMembers().size(), getServer().getPlayersCount());
		for (VsocPlayer p : getServer().getPlayers()) {
			int index = sel.next();
			Member<VectorFunctionBehaviourController<VectorFunction>> m = getMembers().get(index);
			p.setController(m.getController());
			setRandomPosition(p);
		}
	}
	
	abstract protected Comparator<M> createMemberComparator();


	protected void createNextGeneration() {
		List<VectorFunction> pop = sortedVectorFunctions();
		List<VectorFunction> childNets = getSelPoli().createNextGeneration(pop, getCrosser(), getMutationRate());
		setVectorfunctionsToMembers(childNets);
	}

	protected abstract double getMutationRate();

	private List<VectorFunction> sortedVectorFunctions() {
		return getMembers().stream() //
		    .sorted(createMemberComparator()) //
		    .map(m -> m.getController().getVectorFunction())//
		    .collect(Collectors.toList()); //
	}
	
	private void setVectorfunctionsToMembers(List<VectorFunction> nextPop) {
		int index = 0;
		for (M mem : getMembers()) {
			mem.reset();
			VectorFunction vf = nextPop.get(index);
			mem.getController().setVectorFunction(vf);
			index++;
		}
	}
	
	protected double diversity() {
		int count = 0;
		double distSum = 0.0;
		List<M> mems = getMembers();
		for (Member<VectorFunctionBehaviourController<VectorFunction>> m1 : mems) {
			Member<VectorFunctionBehaviourController<VectorFunction>> m2 = mems.get(ran.nextInt(mems.size()));
			distSum += getCrosser().distance(m1.getController().getVectorFunction(), m2.getController().getVectorFunction());
			count++;
		}
		return distSum / count;
	}

	private RandomIndexSelector createSelector(int membersCount, int playersCount) {
		RandomIndexSelector sel;
		try {
			sel = new RandomIndexSelector(0, membersCount - 1, playersCount);
		} catch (IllegalStateException e) {
			throw new IllegalStateException(
			    "Members count (=" + membersCount + ") too small for players count (=" + playersCount + ").", e);
		}
		return sel;
	}

	private void setRandomPosition(Player p) {
		p.move(ran.nextInt(100) - 50, ran.nextInt(60) - 30);
		p.turn(ran.nextInt(360) - 180);
	}

	abstract protected SelectionPolicy<VectorFunction> getSelPoli();

	abstract protected Crosser<VectorFunction> getCrosser();

	abstract protected Behaviour createBehaviour(VectorFunction vf);

	abstract protected M createMember();

}
