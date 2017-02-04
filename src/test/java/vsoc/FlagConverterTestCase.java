package vsoc;

import atan.model.Flag;
import junit.framework.TestCase;
import vsoc.server.*;

public class FlagConverterTestCase extends TestCase {
    
    private FlagConverter conv = FlagConverter.current();

    public void testForEast() {
        assertEquals(Flag.FLAG_CENTER, this.conv.forEast(ServerFlag.CENTER));
        
        assertEquals(Flag.FLAG_RIGHT, this.conv.forEast(ServerFlag.NORTH));
        assertEquals(Flag.FLAG_RIGHT_10, this.conv.forEast(ServerFlag.NORTH_10));
        assertEquals(Flag.FLAG_RIGHT_20, this.conv.forEast(ServerFlag.NORTH_20));
        assertEquals(Flag.FLAG_RIGHT_30, this.conv.forEast(ServerFlag.NORTH_30));

        assertEquals(Flag.FLAG_LEFT, this.conv.forEast(ServerFlag.SOUTH));
        assertEquals(Flag.FLAG_LEFT_10, this.conv.forEast(ServerFlag.SOUTH_10));
        assertEquals(Flag.FLAG_LEFT_20, this.conv.forEast(ServerFlag.SOUTH_20));
        assertEquals(Flag.FLAG_LEFT_30, this.conv.forEast(ServerFlag.SOUTH_30));

        assertEquals(Flag.FLAG_OTHER_10, this.conv.forEast(ServerFlag.WEST_10));
        assertEquals(Flag.FLAG_OTHER_20, this.conv.forEast(ServerFlag.WEST_20));
        assertEquals(Flag.FLAG_OTHER_30, this.conv.forEast(ServerFlag.WEST_30));
        assertEquals(Flag.FLAG_OTHER_40, this.conv.forEast(ServerFlag.WEST_40));
        assertEquals(Flag.FLAG_OTHER_50, this.conv.forEast(ServerFlag.WEST_50));
    
        assertEquals(Flag.FLAG_OWN_10, this.conv.forEast(ServerFlag.EAST_10));
        assertEquals(Flag.FLAG_OWN_20, this.conv.forEast(ServerFlag.EAST_20));
        assertEquals(Flag.FLAG_OWN_30, this.conv.forEast(ServerFlag.EAST_30));
        assertEquals(Flag.FLAG_OWN_40, this.conv.forEast(ServerFlag.EAST_40));
        assertEquals(Flag.FLAG_OWN_50, this.conv.forEast(ServerFlag.EAST_50));
    
    }

    public void testForWest() {
        assertEquals(Flag.FLAG_CENTER, this.conv.forWest(ServerFlag.CENTER));
        
        assertEquals(Flag.FLAG_RIGHT, this.conv.forWest(ServerFlag.SOUTH));
        assertEquals(Flag.FLAG_RIGHT_10, this.conv.forWest(ServerFlag.SOUTH_10));
        assertEquals(Flag.FLAG_RIGHT_20, this.conv.forWest(ServerFlag.SOUTH_20));
        assertEquals(Flag.FLAG_RIGHT_30, this.conv.forWest(ServerFlag.SOUTH_30));

        assertEquals(Flag.FLAG_LEFT, this.conv.forWest(ServerFlag.NORTH));
        assertEquals(Flag.FLAG_LEFT_10, this.conv.forWest(ServerFlag.NORTH_10));
        assertEquals(Flag.FLAG_LEFT_20, this.conv.forWest(ServerFlag.NORTH_20));
        assertEquals(Flag.FLAG_LEFT_30, this.conv.forWest(ServerFlag.NORTH_30));

        assertEquals(Flag.FLAG_OTHER_10, this.conv.forWest(ServerFlag.EAST_10));
        assertEquals(Flag.FLAG_OTHER_20, this.conv.forWest(ServerFlag.EAST_20));
        assertEquals(Flag.FLAG_OTHER_30, this.conv.forWest(ServerFlag.EAST_30));
        assertEquals(Flag.FLAG_OTHER_40, this.conv.forWest(ServerFlag.EAST_40));
        assertEquals(Flag.FLAG_OTHER_50, this.conv.forWest(ServerFlag.EAST_50));
    
        assertEquals(Flag.FLAG_OWN_10, this.conv.forWest(ServerFlag.WEST_10));
        assertEquals(Flag.FLAG_OWN_20, this.conv.forWest(ServerFlag.WEST_20));
        assertEquals(Flag.FLAG_OWN_30, this.conv.forWest(ServerFlag.WEST_30));
        assertEquals(Flag.FLAG_OWN_40, this.conv.forWest(ServerFlag.WEST_40));
        assertEquals(Flag.FLAG_OWN_50, this.conv.forWest(ServerFlag.WEST_50));
    
    }

}
