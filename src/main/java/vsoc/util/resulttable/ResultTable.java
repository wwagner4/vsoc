/**
 * $Revision: 1.4 $ $Author: wwan $ $Date: 2005/11/24 14:15:24 $ 
 */

package vsoc.util.resulttable;

import java.io.Serializable;
import java.util.List;

/**
 * Holds any results in a table like structure.
 */
public interface ResultTable extends Serializable {

    void addNextSerialValue(Number integer);

    void setValue(String id, Number val);

    List<ResultTableRow> getRows();

    List<ColumnDesc> getColumnDescs();

    ColumnDesc getSerialDesc();

    /**
     * @param id The id of the column. 
     * @return A list of Number.
     */
    List<Number> getColumn(String id);

    /**
     * @param id The id of the column. 
     * @return A list of Number.
     */
    List<Number> getSerial();
    
    String currentRowAsNameValuePairs();

}
