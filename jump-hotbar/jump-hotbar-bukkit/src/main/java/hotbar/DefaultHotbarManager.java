package hotbar;
import de.valuga.jump.bukkit.hotbar.HotbarInventory;
import de.valuga.jump.bukkit.hotbar.HotbarManager;
import hotbar.listener.HotbarListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Nico_ND1
 */
public class DefaultHotbarManager extends HotbarManager {

    private final Map<String, HotbarInventory> inventorys = new HashMap<>();
    private final Map<UUID, HotbarInventory> players = new HashMap<>();

    public DefaultHotbarManager(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new HotbarListener(), plugin);
    }

    @Override
    public void setInventory(Player player, HotbarInventory hotbarInventory) {
        this.players.put(player.getUniqueId(), hotbarInventory);
        hotbarInventory.getItems().forEach((slot, hotbarItem) -> player.getInventory().setItem(slot, hotbarItem.getItemStack()));
    }

    @Override
    public Optional<HotbarInventory> getInventory(Player player) {
        return Optional.ofNullable(this.players.get(player.getUniqueId()));
    }

    @Override
    public void registerInventory(String name, HotbarInventory hotbarInventory) {
        this.inventorys.put(name, hotbarInventory);
    }

    @Override
    public Optional<HotbarInventory> getInventory(String name) {
        return Optional.ofNullable(this.inventorys.get(name));
    }

}
