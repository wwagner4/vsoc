/**
 * $Revision: 1.2 $ $Author: wwan $ $Date: 2005/11/25 09:12:07 $ 
 */

package vsoc.camps.goalkeeper;

import java.util.*;

/**
 * IDs of the columns of the GKCamp result table.
 */
public enum GKCampResultColumns {

    GG_DIVERSITY(
            "gg_diversity", "Goalgetter diversity"),

    GG_GOALS(
            "gg_goals",
            "Average number of goals shot by a goalgetter per match"),

    GG_OWNGOALS(
            "gg_owngoals",
            "Average number of own goals shot by a goalgetter per match"),

    GG_KICKS(
            "gg_kicks", "Average number of kicks of a goalgetter per match"),

    GG_KICKOUTS(
            "gg_kickouts",
            "Average number of kick outs of a goalgetter per match"),

    GK_DIVERSITY(
            "gk_diversity", "Goalkeepers diversity"),

    GK_KICKS(
            "gk_kicks", "Average number of kicks of a goalkeeper per match"),

    GK_KICKOUTS(
            "gk_kickouts",
            "Average number of kickouts of a goalkeeper per match"),

    GK_GOALS_RECEIVED(
            "gk_received_goals",
            "Average number of goals a goaly received per match");

    private String desc;
    private String name;

    private GKCampResultColumns(String name, String desc) {
    	this.name = name;
        this.desc = desc;
    }

    
	public static List<GKCampResultColumns> getEnumList() {
        return Arrays.asList(GKCampResultColumns.values());
    }

    public String getDesc() {
        return this.desc;
    }


	public String getName() {
		return name;
	}


}
