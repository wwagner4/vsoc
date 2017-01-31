/**
 * $Revision: 1.2 $ $Author: wwan $ $Date: 2005/11/25 09:12:07 $ 
 */

package vsoc.camps.goalkeeper;

import java.util.List;

import org.apache.commons.lang.enums.Enum;

/**
 * IDs of the columns of the GKCamp result table.
 */
public class GKCampResultColumns extends Enum {

	private static final long serialVersionUID = 1L;

    private String desc;

    public static final GKCampResultColumns GG_DIVERSITY = new GKCampResultColumns(
            "gg_diversity", "Goalgetter diversity");

    public static final GKCampResultColumns GG_GOALS = new GKCampResultColumns(
            "gg_goals",
            "Average number of goals shot by a goalgetter per match");

    public static final GKCampResultColumns GG_OWNGOALS = new GKCampResultColumns(
            "gg_owngoals",
            "Average number of own goals shot by a goalgetter per match");

    public static final GKCampResultColumns GG_KICKS = new GKCampResultColumns(
            "gg_kicks", "Average number of kicks of a goalgetter per match");

    public static final GKCampResultColumns GG_KICKOUTS = new GKCampResultColumns(
            "gg_kickouts",
            "Average number of kick outs of a goalgetter per match");

    public static final GKCampResultColumns GK_DIVERSITY = new GKCampResultColumns(
            "gk_diversity", "Goalkeepers diversity");

    public static final GKCampResultColumns GK_KICKS = new GKCampResultColumns(
            "gk_kicks", "Average number of kicks of a goalkeeper per match");

    public static final GKCampResultColumns GK_KICKOUTS = new GKCampResultColumns(
            "gk_kickouts",
            "Average number of kickouts of a goalkeeper per match");

    public static final GKCampResultColumns GK_GOALS_RECEIVED = new GKCampResultColumns(
            "gk_received_goals",
            "Average number of goals a goaly received per match");

    public GKCampResultColumns(String name, String desc) {
        super(name);
        this.desc = desc;
    }

    
    @SuppressWarnings("unchecked")
	public static List<GKCampResultColumns> getEnumList() {
        return Enum.getEnumList(GKCampResultColumns.class);
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
