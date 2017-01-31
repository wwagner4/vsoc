/**
 * $Revision: 1.2 $ $Author: wwan $ $Date: 2005/11/05 13:46:13 $ 
 */

package vsoc.util.resulttable;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * Describes a column of a result table.
 */
public class ColumnDesc implements Serializable {

    private static final long serialVersionUID = 0L;

    private String name = null;

    private String id = null;

    private NumberFormat format = null;

    public ColumnDesc() {
        super();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        String re = "name of " + this.id;
        if (this.name != null) {
            re = this.name;
        }
        return re;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NumberFormat getFormat() {
        return this.format;
    }

    public void setFormat(NumberFormat format) {
        this.format = format;
    }

}
