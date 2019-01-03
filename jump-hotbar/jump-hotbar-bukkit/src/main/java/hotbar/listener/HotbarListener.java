package hotbar.listener;
import de.valuga.jump.bukkit.hotbar.HotbarManager;
import hotbar.DefaultHotbarManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Nico_ND1
 */
public class HotbarListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        HotbarManager.getInstance().getInventory(player).ifPresent(hotbarInventory -> {
            if (hotbarInventory.getItems().values().stream().anyMatch(hotbarItem -> hotbarItem.getItemStack().equals(event.getItemDrop().getItemStack())))
                event.setCancelled(true);
        });
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        HotbarManager.getInstance().getInventory(player).ifPresent(hotbarInventory -> {
            if (hotbarInventory.getItems().values().stream().anyMatch(hotbarItem -> hotbarItem.getItemStack().equals(event.getCurrentItem())))
                event.setCancelled(true);
        });
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasItem()) return;
        final Player player = event.getPlayer();
        HotbarManager.getInstance().getInventory(player).ifPresent(hotbarInventory -> {
            hotbarInventory.getItems().values().stream()
                .filter(hotbarItem -> hotbarItem.getItemStack().equals(event.getItem()))
                .forEach(hotbarItem -> {
                    if ((event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) && !hotbarItem.isAllowLeftClick())
                        return;
                    if (event.getAction() == Action.PHYSICAL) return;

                    hotbarItem.onInteract(player);
                });
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        ((DefaultHotbarManager) HotbarManager.getInstance()).getPlayers().remove(player.getUniqueId());
    }
}
