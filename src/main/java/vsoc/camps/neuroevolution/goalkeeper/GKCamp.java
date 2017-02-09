package vsoc.camps.neuroevolution.goalkeeper;

import java.io.*;
import java.net.URL;
import java.util.*;

import org.apache.log4j.Logger;

import vsoc.behaviour.*;
import vsoc.camps.*;
import vsoc.camps.neuroevolution.*;
import vsoc.camps.neuroevolution.goalgetter.*;
import vsoc.genetic.*;
import vsoc.nn.Net;
import vsoc.server.*;
import vsoc.util.*;

/**
 * Camp for breeding goal keepers.
 */
public class GKCamp extends AbstractNeuroevolutionCamp {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(GKCamp.class);

	private List<Member<NetBehaviourController<Net>>> goalgetters = null;

	private List<Member<NetBehaviourController<Net>>> goalkeepers = null;

	private CrossableFactory<Net> crossableFactory;

	private SelectionPolicy<Net> gkSelPoli;

	private double gkMutationRate = 0.01;

	private double ggMutationRate = 0.01;

	private int ggKickFactor = 1;

	private int ggKickOutFactor = -5;

	private int ggOwnGoalFactor = -100;

	private int ggGoalFactor = 100;

	private SelectionPolicy<Net> ggSelPoli;

	private String ggCampResourceName = "ggcamp.ser";

	private int ggZeroKickPenalty = -100;

	public int getGgKickFactor() {
		return this.ggKickFactor;
	}

	public void setGgKickFactor(int ggKickFactor) {
		this.ggKickFactor = ggKickFactor;
	}

	public int getGgKickOutFactor() {
		return this.ggKickOutFactor;
	}

	public void setGgKickOutFactor(int ggKickOutFactor) {
		this.ggKickOutFactor = ggKickOutFactor;
	}

	public int getGgGoalFactor() {
		return this.ggGoalFactor;
	}

	public void setGgGoalFactor(int ggOtherGoalFactor) {
		this.ggGoalFactor = ggOtherGoalFactor;
	}

	public int getGgOwnGoalFactor() {
		return this.ggOwnGoalFactor;
	}

	public void setGgOwnGoalFactor(int ggOwnGoalFactor) {
		this.ggOwnGoalFactor = ggOwnGoalFactor;
	}

	public SelectionPolicy<Net> getGgSelPoli() {
		return this.ggSelPoli;
	}

	public void setGgSelPoli(SelectionPolicy<Net> ggSelPoli) {
		this.ggSelPoli = ggSelPoli;
	}

	protected String preCreateNextGenerationInfo() {
		return "";
	}

	protected void initPlayersForMatch() {
		initGoalgetterPlayersForMatch();
		initGoalkeeperPlayersForMatch();
	}

	private void initGoalgetterPlayersForMatch() {
		RandomIndexSelector sel = createSelector(getGoalgetters().size(), getServer().getPlayersCount());
		Iterator<VsocPlayer> iter = getServer().getPlayersWest().iterator();
		while (iter.hasNext()) {
			int index = sel.next();
			Member<NetBehaviourController<Net>> m = getGoalgetters().get(index);
			VsocPlayer p = (VsocPlayer) iter.next();
			p.setController(m.getController());
			setRandomPosition(p);
		}
	}

	private void initGoalkeeperPlayersForMatch() {
		RandomIndexSelector sel = createSelector(getGoalkeepers().size(), getServer().getPlayersCount());
		Iterator<VsocPlayer> iter = getServer().getPlayersEast().iterator();
		while (iter.hasNext()) {
			int index = sel.next();
			Member<NetBehaviourController<Net>> m = getGoalkeepers().get(index);
			VsocPlayer p = (VsocPlayer) iter.next();
			p.setController(m.getController());
			p.move(-50, 0);
		}
	}

	protected void createNextGeneration(Crosser<Net> crosser) {
		Comparator<Member<?>> comp = new GGMembersComparator(this.ggGoalFactor, this.ggOwnGoalFactor, this.ggKickFactor,
		    this.ggKickOutFactor, this.ggZeroKickPenalty);
		basicCreateNextGeneration(getGoalgetters(), crosser, comp, this.ggMutationRate, this.ggSelPoli, this.crossableFactory);

		Comparator<Member<?>> gkComp = new GKMembersComparator();
		basicCreateNextGeneration(getGoalkeepers(), crosser, gkComp, this.gkMutationRate, this.gkSelPoli, this.crossableFactory);
	}

	public List<Member<NetBehaviourController<Net>>> getGoalgetters() {
		if (this.goalgetters == null) {
			log.info("[getGoalgetter ] not yet initialized -> loading from " + this.ggCampResourceName + ".");
			try {
				this.goalgetters = loadGoalgetters(this.ggCampResourceName);
			} catch (IOException e) {
				throw new IllegalStateException("Could not load goalgetters. " + e.getMessage(), e);
			}
		}
		return this.goalgetters;
	}

	private List<Member<NetBehaviourController<Net>>> loadGoalgetters(String resName) throws IOException {
		URL res = getClass().getClassLoader().getResource(resName);
		if (res == null) {
			throw new IOException("Could not find resource '" + resName + "' in classpath."
					+ "\n - Run vsoc.camps.neuroevolution.goalkeeper.GoalgetterGenerator "
					+ "\n - Copy the resultiong .ser file into the classpath ('src/main/resources')." 
					+ "\n - Rename the .ser file to '" + resName + "'.");
		} else {
			log.info("found resource " + res + " to load GKCamp");
		}
		GGCamp camp = (GGCamp) Serializer.current().deserialize(res.openStream());
		List<Member<NetBehaviourController<Net>>> members = camp.getMembers();
		if (this.ggSelPoli.getPopulationSize() != members.size()) {
			String a = resName;
			int x = members.size();
			int y = this.ggSelPoli.getPopulationSize();
			throw new IllegalStateException("The number of members from the serialized gg camp '" + a + "' is "
			    + x + ". It must be the same as the populaton size of the gg selection policy which is " + y + ".");
		}
		return members;
	}

	public void setGoalgetters(List<Member<NetBehaviourController<Net>>> goalgetter) {
		this.goalgetters = goalgetter;
	}

	public List<Member<NetBehaviourController<Net>>> getGoalkeepers() {
		if (this.goalkeepers == null) {
			this.goalkeepers = createGoalkeepers();
		}
		return this.goalkeepers;
	}

	private List<Member<NetBehaviourController<Net>>> createGoalkeepers() {
		List<Member<NetBehaviourController<Net>>> mems = new ArrayList<>();
		List<Net> nets = this.gkSelPoli.createNewGeneration(this.crossableFactory);
		Iterator<Net> iter = nets.iterator();
		while (iter.hasNext()) {
			Net net = iter.next();
			NetBehaviourController<Net> ncs = new NetBehaviourController<>(createGkBehaviour(net));
			ncs.setNet(net);
			Member<NetBehaviourController<Net>> mem = new Member<>();
			mem.setController(ncs);
			mem.reset();
			mems.add(mem);
		}
		return mems;
	}

	private Behaviour createGkBehaviour(Net net) {
		NetBehaviour<Net> b1 = new NetBehaviour<>(net);
		GoalkeeperDefaultBehaviour b2 = new GoalkeeperDefaultBehaviour(b1);
		return new DefaultBehaviour(b2);
	}

	public void setGoalkeepers(List<Member<NetBehaviourController<Net>>> goalkeeper) {
		this.goalkeepers = goalkeeper;
	}

	protected Server createServer() {
		DefaultServerFactory fac = new DefaultServerFactory();
		fac.setEastPlayerCount(1);
		fac.setWestPlayerCount(5);
		return fac.createServer();
	}

	@Override
	protected int eastPlayerCount() {
		return 1;
	}

	@Override
	protected int westPlayerCount() {
		return 5;
	}

	public CrossableFactory<Net> getCrossableFactory() {
		return this.crossableFactory;
	}

	public void setCrossableFactory(CrossableFactory<Net> gkCrossableFactory) {
		this.crossableFactory = gkCrossableFactory;
	}

	public SelectionPolicy<Net> getGkSelPoli() {
		return this.gkSelPoli;
	}

	public void setGkSelPoli(SelectionPolicy<Net> gkSelPoli) {
		this.gkSelPoli = gkSelPoli;
	}

	protected List<Member<NetBehaviourController<Net>>> getMembers() {
		ArrayList<Member<NetBehaviourController<Net>>> re = new ArrayList<>();
		re.addAll(getGoalgetters());
		re.addAll(getGoalkeepers());
		return re;
	}

	public double getGkMutationRate() {
		return this.gkMutationRate;
	}

	public void setGkMutationRate(double gkMutationRate) {
		this.gkMutationRate = gkMutationRate;
	}

	public void setGkMutationRate(String gkMutationRate) {
		this.gkMutationRate = Double.parseDouble(gkMutationRate);
	}

	public double getGgMutationRate() {
		return this.ggMutationRate;
	}

	public void setGgMutationRate(double ggMutationRate) {
		this.ggMutationRate = ggMutationRate;
	}

	public String getGgCampResourceName() {
		return this.ggCampResourceName;
	}

	public void setGgCampResourceName(String ggCampResourceName) {
		this.ggCampResourceName = ggCampResourceName;
	}

	public int getGgZeroKickPenalty() {
		return this.ggZeroKickPenalty;
	}

	public void setGgZeroKickPenalty(int ggZeroKickPenalty) {
		this.ggZeroKickPenalty = ggZeroKickPenalty;
	}

	protected void addProperties(Properties re) {
		super.addProperties(re);
		VsocUtil u = VsocUtil.current();
		re.setProperty("GK selection policy", this.gkSelPoli.getClass().getName());
		re.setProperty("GK mutation rate", u.format(this.gkMutationRate));
		re.setProperty("GG mutation rate", u.format(this.ggMutationRate));
		re.setProperty("GG camp resource name", this.ggCampResourceName);
		re.setProperty("GG kick factor", u.format(this.ggKickFactor));
		re.setProperty("GG kick out factor", u.format(this.ggKickOutFactor));
		re.setProperty("GG goal factor", u.format(this.ggGoalFactor));
		re.setProperty("GG own goal factor", u.format(this.ggOwnGoalFactor));
		re.setProperty("GG zero kick penalty", u.format(this.ggZeroKickPenalty));
		re.setProperty("GG selection policy", this.ggSelPoli.getClass().getName());
		re.setProperty("Crossable factory", this.crossableFactory.getClass().getName());
	}

}
