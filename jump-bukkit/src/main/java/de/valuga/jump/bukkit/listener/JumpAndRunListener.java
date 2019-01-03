package de.valuga.jump.bukkit.listener;
import de.valuga.jump.JumpAndRunSession;
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
public class JumpAndRunListener implements Listener {

    private final Map<Player, Location> lastLocation = new HashMap<>();

    @EventHandler
    public void onMoveStart(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.playerNotMovedBlock(player, event.getTo())) return;
        this.lastLocation.put(player, event.getTo());

        JumpAndRuns.getOperator().getJumpAndRuns().stream().filter(jumpAndRun -> {
            final Location spawnLocation = jumpAndRun.getSpawnLocation().toLocation();

            return spawnLocation.getBlockX() == event.getTo().getBlockX()
                && spawnLocation.getBlockZ() == event.getTo().getBlockZ()
                && (event.getTo().getBlockY() - spawnLocation.getBlockY() <= 1.25 && event.getTo().getBlockY() - spawnLocation.getBlockY() >= -0.1);
        }).findFirst().ifPresent(jumpAndRun -> JumpAndRuns.getOperator().startJumpAndRun(player, jumpAndRun));
    }

    @EventHandler
    public void onMoveDeath(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.playerNotMovedBlock(player, event.getTo())) return;
        this.lastLocation.put(player, event.getTo());

        final JumpAndRunSession session = JumpAndRuns.getOperator().getJumpSessionInfo(player);
        if (session == null) return;

        if (event.getTo().getBlockY() < session.getJumpAndRun().getDeathAt())
            session.reset();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.lastLocation.remove(event.getPlayer());
    }

    private boolean playerNotMovedBlock(Player player, Location to) {
        if (!this.lastLocation.containsKey(player)) return true;

        final Location lastLocation = this.lastLocation.get(player);

        return lastLocation.getBlockX() == to.getBlockX() && lastLocation.getBlockY() == to.getBlockY() && lastLocation.getBlockZ() == to.getBlockZ();
    }

}
