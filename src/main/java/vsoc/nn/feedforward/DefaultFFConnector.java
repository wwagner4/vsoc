package vsoc.nn.feedforward;

import vsoc.nn.base.TransferManager;
import vsoc.nn.base.Weighter;
import vsoc.util.IntVector;

/**
 * A net with no intermediate layer. <table>
 * <tr>
 * <td>seed</td>
 * <td>432985</td>
 * </tr>
 * </table> <table>
 * <tr>
 * <td>layers</td>
 * <td>32</td>
 * <td>18</td>
 * </tr>
 * </table> <table>
 * <tr>
 * <td rowspan="3">connection <br>
 * probabillity</td>
 * <td></td>
 * <td>0</td>
 * <td>1</td>
 * </tr>
 * <tr>
 * <td>0</td>
 * <td>-</td>
 * <td>-</td>
 * </tr>
 * <tr>
 * <td>1</td>
 * <td>100 %</td>
 * <td>-</td>
 * </tr>
 * </table>
 */

public class DefaultFFConnector extends AbstractFFNetConnector {

    private static final long serialVersionUID = 1L;
    private static Weighter weighter = new Weighter(0, 1);

    public int seed() {
        return 432985;
    }

    public TransferManager transferManager() {
        return new TransferManager();
    }

    public Weighter weighter() {
        return weighter;
    }

    public IntVector nodesPerLayer() {
        IntVector npl = new IntVector();
        npl.addElement(32);
        npl.addElement(18);
        return npl;
    }

    public IntVector connProbMatrix() {
        IntVector cpm = new IntVector();
        IntVector cpl;
        cpl = new IntVector();
        cpm.addElement(cpl);
        cpl = new IntVector();
        cpl.addElement(100);
        cpm.addElement(cpl);
        return cpm;
    }
}
