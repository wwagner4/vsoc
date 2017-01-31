package vsoc.camps;

/**
 * Runns a Camp in a seperate Thread
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

import vsoc.VsocInvalidConfigurationException;
import vsoc.genetic.CrossableFactory;
import vsoc.genetic.SelectionPolicy;
import vsoc.model.Server;
import vsoc.model.VsocPlayer;
import vsoc.nn.Net;
import vsoc.util.ObjectPairsIterator;
import vsoc.util.RandomIndexSelector;
import vsoc.util.Vec2D;
import vsoc.util.VsocUtil;
import vsoc.util.resulttable.ResultTable;
import atan.model.Controller;
import atan.model.Player;

public abstract class AbstractCamp implements Camp {

    private static final long serialVersionUID = 0L;

    private static Logger log = Logger.getLogger(AbstractCamp.class);

    private transient Server server = null;

    private Random random = null;

    private int matchCount = 0;

    private int generationsCount = 0;

    protected ResultTable resultTable = null;

    protected int maxGenerations;

    private int stepsPerMatch = 600;

    private int matchesPerGeneration = 150;

    public AbstractCamp() {
        super();
    }

    public void run() {
        while (true) {
            takeOneStep();
        }
    }

    public void takeOneStep() {
        if (log.isDebugEnabled()) {
            log.debug("Started running a new match. matchCount:"
                    + this.matchCount + " selectionCount:"
                    + this.generationsCount);
        }
        runMatch();
        this.matchCount++;
        updateMembersAfterMatch();
        if (this.matchCount >= this.matchesPerGeneration) {
            this.matchCount = 0;
            this.generationsCount++;
            createNextGeneration();
        }
    }

    public boolean isFinished() {
        return getGenerationsCount() >= this.maxGenerations;
    }

    private void runMatch() {
        getServer().reset();
        initPlayersForMatch();
        getServer().getBall().setPosition(new Vec2D(0, 0));
        for (int i = 0; (i < this.stepsPerMatch); i++) {
            getServer().takeStep();
        }
    }

    abstract protected void initPlayersForMatch();

    public Server getServer() {
        if (this.server == null) {
            this.server = createServer();
        }
        return this.server;
    }

    protected abstract Server createServer();

    public int getStepsPerMatch() {
        return this.stepsPerMatch;
    }

    public void setStepsPerMatch(int stepsPerMatch) {
        this.stepsPerMatch = stepsPerMatch;
    }

    public int getGenerationsCount() {
        return this.generationsCount;
    }

    public Properties getProperties() {
        Properties re = new Properties();
        addProperties(re);
        return re;
    }

    public int getMatchesPerGeneration() {
        return this.matchesPerGeneration;
    }

    public void setMatchesPerGeneration(int matchesPerGeneration) {
        this.matchesPerGeneration = matchesPerGeneration;
    }

    public ResultTable getResultTable() {
        return this.resultTable;
    }

    public void setResultTable(ResultTable resultTable) {
        this.resultTable = resultTable;
    }

    public void setMaxGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    public void setMaxGenerations(String maxGenerations) {
        this.maxGenerations = Integer.parseInt(maxGenerations);
    }

    public int getMatchCount() {
        return this.matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public int getMaxGenerations() {
        return this.maxGenerations;
    }

    public void setGenerationsCount(int generationsCount) {
        this.generationsCount = generationsCount;
    }

    abstract protected List getMembers();

    protected void setRandomPosition(Player p) {
        p.move(this.getRandom().nextInt(100) - 50,
                this.getRandom().nextInt(60) - 30);
        p.turn(this.getRandom().nextInt(360) - 180);
    }

    private void updateMembersAfterMatch() {
        int eastGoals = 0;
        ArrayList eastMembers = new ArrayList();
        int westGoals = 0;
        ArrayList westMembers = new ArrayList();
        {
            Iterator it = getServer().getPlayersEast().iterator();
            while (it.hasNext()) {
                VsocPlayer p = (VsocPlayer) it.next();
                eastGoals += p.getOtherGoalCount();
                Member m = getMemberByControlSystem(getMembers(), p
                        .getController());
                if (m != null) {
                    eastMembers.add(m);
                    updateMemberFromPlayer(p, m);
                }
            }
        }
        {
            Iterator it = getServer().getPlayersWest().iterator();
            while (it.hasNext()) {
                VsocPlayer p = (VsocPlayer) it.next();
                westGoals += p.getOtherGoalCount();
                Member m = getMemberByControlSystem(getMembers(), p
                        .getController());
                if (m != null) {
                    westMembers.add(m);
                    updateMemberFromPlayer(p, m);
                }
            }
        }
        {
            Iterator iter = eastMembers.iterator();
            while (iter.hasNext()) {
                Member mem = (Member) iter.next();
                mem.increaseReceivedGoalsCount(westGoals);
            }
        }
        {
            Iterator iter = westMembers.iterator();
            while (iter.hasNext()) {
                Member mem = (Member) iter.next();
                mem.increaseReceivedGoalsCount(eastGoals);
            }
        }
    }

    protected void basicCreateNextGeneration(List mems, Comparator comp,
            double mutRate, SelectionPolicy selPoli,
            CrossableFactory crossableFactory) {
        List pop = sortedNetsFromMembers(mems, comp);
        List childNets = selPoli.createNextGeneration(pop, crossableFactory,
                mutRate);
        addNetsToMembers(mems, childNets);
    }

    private void updateMemberFromPlayer(VsocPlayer p, Member m) {
        m.increaseMatchCount();
        m.increaseKickCount(p.getKickCount());
        m.increaseKickOutCount(p.getKickOutCount());
        m.increaseOtherGoalsCount(p.getOtherGoalCount());
        m.increaseOwnGoalsCount(p.getOwnGoalCount());
    }

    private void addNetsToMembers(List mems, List nextPop) {
        int size = nextPop.size();
        Iterator iter = mems.iterator();
        int index = 0;
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            mem.reset();
            Net net = (Net) nextPop.get(index);
            mem.getNeuroControlSystem().setNet(net);
            index++;
        }
    }

    abstract protected void createNextGeneration();

    protected Random getRandom() {
        if (this.random == null) {
            this.random = new Random();
        }
        return this.random;
    }

    protected void addProperties(Properties re) {
        re.setProperty("steps per match", VsocUtil.current().format(
                this.stepsPerMatch));
        re.setProperty("max generations", "" + this.maxGenerations);
        re.setProperty("matches per generation", VsocUtil.current().format(
                this.matchesPerGeneration));
    }

    protected RandomIndexSelector createSelector(int membersCount,
            int playersCount) {
        RandomIndexSelector sel;
        try {
            sel = new RandomIndexSelector(0, membersCount - 1, playersCount);
        } catch (VsocInvalidConfigurationException e) {
            throw new VsocInvalidConfigurationException("Members count (="
                    + membersCount + ") too small for players count (="
                    + playersCount + ").", e);
        }
        return sel;
    }

    protected Member getMemberByControlSystem(List mems, Controller c) {
        Iterator i = mems.iterator();
        while (i.hasNext()) {
            Member m = (Member) i.next();
            if (m.getNeuroControlSystem() == c)
                return m;
        }
        return null;
    }

    protected double diversity(List mems) {
        ObjectPairsIterator i = new ObjectPairsIterator(mems);
        int count = 0;
        double distSum = 0.0;
        while (i.hasNext()) {
            Object[] pair = i.next();
            Member m1 = (Member) pair[0];
            Member m2 = (Member) pair[1];
            distSum += m1.getNeuroControlSystem().getNet().distance(
                    m2.getNeuroControlSystem().getNet());
            count++;
        }
        return distSum / count;
    }

    protected double kicks(List mems) {
        double kicks = 0.0;
        int count = 0;
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            kicks += mem.kickPerMatch();
            count++;
        }
        return kicks / count;
    }

    protected double kickOuts(List mems) {
        double kicks = 0.0;
        int count = 0;
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            kicks += mem.kickOutPerMatch();
            count++;
        }
        return kicks / count;
    }

    protected double goalsReceived(List mems) {
        double re = 0.0;
        int count = 0;
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            re += mem.receivedGoalsPerMatch();
            count++;
        }
        return re / count;
    }

    protected double goals(List mems) {
        double goals = 0.0;
        int count = 0;
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            goals += mem.goalsPerMatch();
            count++;
        }
        return goals / count;
    }

    protected double ownGoals(List mems) {
        double goals = 0.0;
        int count = 0;
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            goals += mem.ownGoalsPerMatch();
            count++;
        }
        return goals / count;
    }

    private List sortedNetsFromMembers(List mems, Comparator comp) {
        Collections.sort(mems, comp);
        List pop = new ArrayList();
        Iterator iter = mems.iterator();
        while (iter.hasNext()) {
            Member mem = (Member) iter.next();
            Net net = mem.getNeuroControlSystem().getNet();
            pop.add(net);
        }
        return pop;
    }

}