package de.valuga.jump.test;
import de.valuga.jump.JumpAndRun;
import de.valuga.jump.SerializableLocation;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Nico_ND1
 */
public class JumpAndRunTest {

    @Test
    public void testJumpAndRun() {
        final JumpAndRun jumpAndRun = new JumpAndRun(new SerializableLocation("world", 10, 11, 12, 180, 180), Collections.emptyList(), "test", null, 10);
        assertEquals(jumpAndRun.getName(), "test");
        assertEquals(jumpAndRun.getDeathAt(), 10);
    }

}
