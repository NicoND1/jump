package de.valuga.jump.bukkit.listener;
import de.valuga.jump.JumpAndRuns;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nico_ND1
 */
public class StartStopListener implements Listener {

    private final Map<Player, Location> lastLocation = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (!this.playerMovedBlock(player, event.getTo())) return;
        this.lastLocation.put(player, event.getTo());

        JumpAndRuns.getOperator().getJumpAndRuns().stream().filter(jumpAndRun -> {
            final Location spawnLocation = jumpAndRun.getSpawnLocation().toLocation();


        }).forEach(jumpAndRun -> {

        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.lastLocation.remove(event.getPlayer());
    }

    private boolean playerMovedBlock(Player player, Location to) {
        if (!this.lastLocation.containsKey(player)) return true;

        final Location lastLocation = this.lastLocation.get(player);

        return !(lastLocation.getBlockX() == to.getBlockX() && lastLocation.getBlockY() == to.getBlockY() && lastLocation.getBlockZ() == to.getBlockZ());
    }

}
