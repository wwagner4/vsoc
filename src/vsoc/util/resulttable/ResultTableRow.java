package vsoc.util.resulttable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import vsoc.VsocInvalidConfigurationException;

public class ResultTableRow implements Serializable {

    private static final long serialVersionUID = 0L;

    private Number serialValue;

    private transient Map<String, Number> resultValues = new HashMap<>();

    public ResultTableRow() {
        super();
    }

    public Map<String, Number> getResultValues() {
        return this.resultValues;
    }

    public Number getResultValue(String id) {
        return this.resultValues.get(id);
    }

    public void setResultValues(Map<String, Number> resultValues) {
        this.resultValues = resultValues;
    }

    public void setResultValue(String id, Number value) {
        if (!this.resultValues.containsKey(id)) {
            throw new VsocInvalidConfigurationException("No column with id '"
                    + id + "' defined.");
        }
        this.resultValues.put(id, value);
    }

    public Number getSerialValue() {
        return this.serialValue;
    }

    public void setSerialValue(Number serialValue) {
        this.serialValue = serialValue;
    }

}
