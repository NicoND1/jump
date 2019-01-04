package de.valuga.jump.hotbar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author Nico_ND1
 */
public interface HotbarInventory {

    /**
     * All items that should be placed in the {@link Player}s hotbar.
     *
     * @return A {@link Map<Integer, HotbarItem> map} with all items. The {@link Integer} is the slot and the
     * {@link HotbarItem} contains the {@link ItemStack}
     * @since 0.0.1
     */
    Map<Integer, HotbarItem> getItems();

}
