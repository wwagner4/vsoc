/**
 * $Revision: 1.1 $ $Author: wwan $ $Date: 2005/11/24 14:15:24 $ 
 */

package vsoc.camps.goalgetter;

import java.util.List;

import org.apache.commons.lang.enums.Enum;

/**
 * IDs of the columns of the GGCamp result table.
 */
@SuppressWarnings("serial")
public class GGCampResultColumns extends Enum {
    
    private String desc;
    
    public static final GGCampResultColumns DIVERSITY = new GGCampResultColumns("diversity", "Diversity");
    public static final GGCampResultColumns GOALS = new GGCampResultColumns("goals", "Average number of golas shot by a goalgetter per match");
    public static final GGCampResultColumns OWNGOALS = new GGCampResultColumns("owngoals", "Average number of own golas shot by a goalgetter per match");
    public static final GGCampResultColumns KICKS = new GGCampResultColumns("kicks", "Average number of ball kicks of a goalgetter per match");
    public static final GGCampResultColumns KICKOUTS = new GGCampResultColumns("kickouts", "Average number of ball kick outs of a goalgetter per match");

    public GGCampResultColumns(String name, String desc) {
        super(name);
        this.desc = desc;
    }
    
    @SuppressWarnings("unchecked")
	public static List<GGCampResultColumns> getEnumList() {
        return Enum.getEnumList(GGCampResultColumns.class);
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
