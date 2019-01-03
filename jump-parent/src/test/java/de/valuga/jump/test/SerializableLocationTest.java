package de.valuga.jump.test;
import de.valuga.jump.SerializableLocation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Nico_ND1
 */
public class SerializableLocationTest {

    @Test
    public void testSerializableLocation() {
        final SerializableLocation spawnLocation = new SerializableLocation("world", 10, 11, 12, 180, 180);
        assertEquals(10D, spawnLocation.getX(), 0);
        assertEquals(11D, spawnLocation.getY(), 0);
        assertEquals(12D, spawnLocation.getZ(), 0);
        assertEquals(180F, spawnLocation.getYaw(), 0);
    }

}
