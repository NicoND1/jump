package de.valuga.jump.hotbar.bukkit;
import de.valuga.jump.hotbar.HotbarItem;
import org.bukkit.entity.Player;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Nico_ND1
 */
public class AbstractHotbarInventoryTest {

    @Test
    public void onTest() {
        final AbstractHotbarInventory inventory = new AbstractHotbarInventory(new HashMap<Integer, HotbarItem>() {{
            this.put(0, new HotbarItem(null) {
                @Override
                public void onInteract(Player player) {

                }
            });
        }});

        assertEquals(1, inventory.getItems().size());
        assertTrue(inventory.getItems().keySet().stream().anyMatch(slot -> slot == 0));
    }

}
