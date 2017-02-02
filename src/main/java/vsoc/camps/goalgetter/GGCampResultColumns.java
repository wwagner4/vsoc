/**
 * $Revision: 1.1 $ $Author: wwan $ $Date: 2005/11/24 14:15:24 $ 
 */

package vsoc.camps.goalgetter;

import java.util.Arrays;
import java.util.List;

/**
 * IDs of the columns of the GGCamp result table.
 */
public enum GGCampResultColumns {
    
    DIVERSITY("diversity", "Diversity"),
    GOALS("goals", "Average number of golas shot by a goalgetter per match"),
    OWNGOALS("owngoals", "Average number of own golas shot by a goalgetter per match"),
    KICKS("kicks", "Average number of ball kicks of a goalgetter per match"),
    KICKOUTS("kickouts", "Average number of ball kick outs of a goalgetter per match");

	private String name;
	private String desc;
	
    private GGCampResultColumns(String name, String desc) {
    	this.name = name;
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }

    public String getName() {
        return this.name;
    }

	public static List<GGCampResultColumns> getEnumList() {
		return Arrays.asList(GGCampResultColumns.values());
	}

}
