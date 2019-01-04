package de.valuga.jump.hotbar;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author Nico_ND1
 */
public abstract class HotbarManager {

    @Getter private static HotbarManager instance;

    public static void setInstance(HotbarManager instance) {
        if (HotbarManager.instance != null) throw new UnsupportedOperationException("Can't change singleton");
        HotbarManager.instance = instance;
    }

    /**
     * Set the {@link HotbarInventory} for the {@code player}.
     *
     * @param player          The {@link Player} to set the {@code hotbarInventory}
     * @param hotbarInventory The {@link HotbarInventory} with all items that should be placed in the {@code player}s
     *                        hotbar
     * @since 0.0.1
     */
    public abstract void setInventory(Player player, HotbarInventory hotbarInventory);

    /**
     * Set the {@link HotbarInventory} for the {@code player} by just the of the {@link HotbarInventory}.
     *
     * @param player The {@link Player} to set the {@link HotbarInventory}
     * @param name   The name of the {@link HotbarInventory}
     * @since 0.0.1
     */
    public abstract void setInventory(Player player, String name);

    /**
     * Gets the {@link HotbarInventory} from the {@code player}.
     *
     * @param player The {@link Player}
     * @return A {@link Optional<HotbarInventory>} which may contain the {@link HotbarInventory} from the {@code player}
     * @since 0.0.1
     */
    public abstract Optional<HotbarInventory> getInventory(Player player);

    /**
     * Registers a new {@link HotbarInventory}.
     *
     * @param name            The name of the {@code hotbarInventory}
     * @param hotbarInventory The {@link HotbarInventory}
     * @since 0.0.1
     */
    public abstract void registerInventory(String name, HotbarInventory hotbarInventory);

    /**
     * Gets the {@link HotbarInventory} from the {@code name}.
     *
     * @param name The name of the {@link HotbarInventory}
     * @return A {@link Optional<HotbarInventory>} which may contain the {@link HotbarInventory}
     * @since 0.0.1
     */
    public abstract Optional<HotbarInventory> getInventory(String name);
}
