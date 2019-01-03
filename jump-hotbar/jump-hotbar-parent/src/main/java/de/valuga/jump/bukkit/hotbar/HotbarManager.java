package de.valuga.jump.bukkit.hotbar;
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

    public abstract void setInventory(Player player, HotbarInventory hotbarInventory);

    public abstract void setInventory(Player player, String name);

    public abstract Optional<HotbarInventory> getInventory(Player player);

    public abstract void registerInventory(String name, HotbarInventory hotbarInventory);

    public abstract Optional<HotbarInventory> getInventory(String name);
}
