package vsoc.camps.goalkeeper;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import vsoc.VsocInvalidConfigurationException;
import vsoc.VsocInvalidDataException;
import vsoc.behaviour.Behaviour;
import vsoc.camps.AbstractCamp;
import vsoc.camps.BehaviourNeuroControlSystem;
import vsoc.camps.DefaultBehaviour;
import vsoc.camps.Member;
import vsoc.camps.NetBehaviour;
import vsoc.camps.goalgetter.GGCamp;
import vsoc.camps.goalgetter.GGMembersComparator;
import vsoc.genetic.CrossableFactory;
import vsoc.genetic.SelectionPolicy;
import vsoc.model.DefaultServerFactory;
import vsoc.model.Server;
import vsoc.model.VsocPlayer;
import vsoc.nn.Net;
import vsoc.util.RandomIndexSelector;
import vsoc.util.Serializer;
import vsoc.util.VsocUtil;
import vsoc.util.resulttable.CSVOutputter;

/**
 * Camp for breeding goal keepers.
 */
public class GKCamp extends AbstractCamp {

    private static Logger log = Logger.getLogger(GKCamp.class);

    private List goalgetters = null;

    private List goalkeepers = null;

    private CrossableFactory crossableFactory;

    private SelectionPolicy gkSelPoli;

    private double gkMutationRate = 0.01;

    private double ggMutationRate = 0.01;

    private int ggKickFactor = 1;

    private int ggKickOutFactor = -5;

    private int ggOwnGoalFactor = -100;

    private int ggGoalFactor = 100;

    private SelectionPolicy ggSelPoli;

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

    public SelectionPolicy getGgSelPoli() {
        return this.ggSelPoli;
    }

    public void setGgSelPoli(SelectionPolicy ggSelPoli) {
        this.ggSelPoli = ggSelPoli;
    }

    public GKCamp() {
        super();
    }

    protected String preCreateNextGenerationInfo() {
        return "";
    }

    protected void initPlayersForMatch() {
        initGoalgetterPlayersForMatch();
        initGoalkeeperPlayersForMatch();
    }

    private void initGoalgetterPlayersForMatch() {
        RandomIndexSelector sel = createSelector(getGoalgetters().size(),
                getServer().getPlayersCount());
        Iterator iter = getServer().getPlayersWest().iterator();
        while (iter.hasNext()) {
            int index = sel.next();
            Member m = (Member) getGoalgetters().get(index);
            VsocPlayer p = (VsocPlayer) iter.next();
            p.setController(m.getNeuroControlSystem());
            setRandomPosition(p);
        }
    }

    private void initGoalkeeperPlayersForMatch() {
        RandomIndexSelector sel = createSelector(getGoalkeepers().size(),
                getServer().getPlayersCount());
        Iterator iter = getServer().getPlayersEast().iterator();
        while (iter.hasNext()) {
            int index = sel.next();
            Member m = (Member) getGoalkeepers().get(index);
            VsocPlayer p = (VsocPlayer) iter.next();
            p.setController(m.getNeuroControlSystem());
            p.move(-50, 0);
        }
    }

    protected void createNextGeneration() {
        saveResultValues();
        debug();

        Comparator comp = new GGMembersComparator(this.ggGoalFactor,
                this.ggOwnGoalFactor, this.ggKickFactor,
                this.ggKickOutFactor, this.ggZeroKickPenalty);
        basicCreateNextGeneration(getGoalgetters(), comp, this.ggMutationRate,
                this.ggSelPoli, this.crossableFactory);

        Comparator gkComp = new GKMembersComparator();
        basicCreateNextGeneration(getGoalkeepers(), gkComp,
                this.gkMutationRate, this.gkSelPoli, this.crossableFactory);
    }

    private void saveResultValues() {
        if (this.resultTable != null) {
            this.resultTable.addNextSerialValue(new Integer(
                    getGenerationsCount()));
            this.resultTable.setValue(GKCampResultColumns.GG_DIVERSITY
                    .getName(), new Double(diversity(getGoalgetters())));
            this.resultTable.setValue(GKCampResultColumns.GG_GOALS.getName(),
                    new Double(goals(getGoalgetters())));
            this.resultTable.setValue(
                    GKCampResultColumns.GG_KICKOUTS.getName(), new Double(
                            kickOuts(getGoalgetters())));
            this.resultTable.setValue(GKCampResultColumns.GG_KICKS.getName(),
                    new Double(kicks(getGoalgetters())));
            this.resultTable.setValue(
                    GKCampResultColumns.GG_OWNGOALS.getName(), new Double(
                            ownGoals(getGoalgetters())));
            this.resultTable.setValue(GKCampResultColumns.GK_DIVERSITY
                    .getName(), new Double(diversity(getGoalkeepers())));
            this.resultTable.setValue(
                    GKCampResultColumns.GK_KICKOUTS.getName(), new Double(
                            kickOuts(getGoalkeepers())));
            this.resultTable.setValue(GKCampResultColumns.GK_KICKS.getName(),
                    new Double(kicks(getGoalkeepers())));
            this.resultTable.setValue(GKCampResultColumns.GK_GOALS_RECEIVED
                    .getName(), new Double(goalsReceived(getGoalkeepers())));
        }
    }

    private void debug() {
        if (this.resultTable != null) {
            if (log.isDebugEnabled()) {
                String tab = createTableString();
                log.debug("Creating a new generation. "
                        + this.resultTable.currentRowAsNameValuePairs() + "\n"
                        + tab);
            } else {
                log.info("Creating a new generation. "
                        + this.resultTable.currentRowAsNameValuePairs());
            }
        } else {
            log.info("Creating a new generation. " + this.getGenerationsCount()
                    + " No result table defined.");
        }
    }

    private String createTableString() {
        String re = "";
        try {
            CSVOutputter op = new CSVOutputter();
            op.setSeparator('\t');
            op.setTable(getResultTable());
            StringWriter w = new StringWriter();
            op.output(w);
            w.close();
            re = w.getBuffer().toString();
        } catch (IOException e) {
            String msg = "[createTableString] Could not create because: "
                    + e.getMessage();
            log.error(msg, e);
            re = msg;
        }
        return re;
    }

    public List getGoalgetters() {
        if (this.goalgetters == null) {
            log.info("[getGoalgetter ] not yet initialized -> loading from "
                    + this.ggCampResourceName + ".");
            try {
                this.goalgetters = loadGoalgetters(this.ggCampResourceName);
            } catch (IOException e) {
                throw new VsocInvalidDataException(
                        "Could not load goalgetters. " + e.getMessage(), e);
            }
        }
        return this.goalgetters;
    }

    private List loadGoalgetters(String resName) throws IOException {
        URL res = getClass().getClassLoader().getResource(resName);
        if (res == null) {
            throw new IOException("Could not find resource '" + resName
                    + "' in classpath.");
        }
        GGCamp camp = (GGCamp) Serializer.current().deserialize(
                res.openStream());
        List members = camp.getMembers();
        if (this.ggSelPoli.getPopulationSize() != members.size()) {
            String a = resName;
            int x = members.size();
            int y = this.ggSelPoli.getPopulationSize();
            throw new VsocInvalidConfigurationException(
                    "The number of members from the serialized gg camp '"
                            + a
                            + "' is "
                            + x
                            + ". It must be the same as the populaton size of the gg selection policy which is "
                            + y + ".");
        }
        return members;
    }

    public void setGoalgetters(List goalgetter) {
        this.goalgetters = goalgetter;
    }

    public List getGoalkeepers() {
        if (this.goalkeepers == null) {
            this.goalkeepers = createGoalkeepers();
        }
        return this.goalkeepers;
    }

    private List createGoalkeepers() {
        List mems = new ArrayList();
        List nets = this.gkSelPoli.createNewGeneration(this.crossableFactory);
        Iterator iter = nets.iterator();
        while (iter.hasNext()) {
            Net net = (Net) iter.next();
            BehaviourNeuroControlSystem ncs = new BehaviourNeuroControlSystem(
                    createGkBehaviour(net));
            ncs.setNet(net);
            Member mem = new Member();
            mem.setNeuroControlSystem(ncs);
            mem.reset();
            mems.add(mem);
        }
        return mems;
    }

    private Behaviour createGkBehaviour(Net net) {
        NetBehaviour b1 = new NetBehaviour(net);
        GoalkeeperDefaultBehaviour b2 = new GoalkeeperDefaultBehaviour(b1);
        DefaultBehaviour b3 = new DefaultBehaviour(b2);
        return b3;
    }

    public void setGoalkeepers(List goalkeeper) {
        this.goalkeepers = goalkeeper;
    }

    protected Server createServer() {
        DefaultServerFactory fac = new DefaultServerFactory();
        fac.setEastPlayerCount(1);
        fac.setWestPlayerCount(5);
        return fac.createServer();
    }

    public CrossableFactory getCrossableFactory() {
        return this.crossableFactory;
    }

    public void setCrossableFactory(CrossableFactory gkCrossableFactory) {
        this.crossableFactory = gkCrossableFactory;
    }

    public SelectionPolicy getGkSelPoli() {
        return this.gkSelPoli;
    }

    public void setGkSelPoli(SelectionPolicy gkSelPoli) {
        this.gkSelPoli = gkSelPoli;
    }

    protected List getMembers() {
        ArrayList re = new ArrayList();
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
