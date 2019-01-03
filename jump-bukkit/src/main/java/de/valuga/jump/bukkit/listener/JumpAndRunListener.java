package de.valuga.jump.bukkit.listener;
import de.valuga.jump.JumpAndRunSession;
import de.valuga.jump.JumpAndRuns;
import de.valuga.jump.bukkit.operator.BukkitJumpAndRunOperator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Nico_ND1
 */
public class JumpAndRunListener implements Listener {

    private final Map<Player, Location> lastLocation = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGH)
    public void onMoveStart(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.playerNotMovedBlock(player, event.getTo())) return;
        this.lastLocation.put(player, event.getTo());

        JumpAndRuns.getOperator().getJumpAndRuns().stream().filter(jumpAndRun -> {
            final Location spawnLocation = jumpAndRun.getSpawnLocation().toLocation();

            return spawnLocation.getBlockX() == event.getTo().getBlockX()
                && spawnLocation.getBlockZ() == event.getTo().getBlockZ()
                && (event.getTo().getBlockY() - spawnLocation.getBlockY() <= 1.25 && event.getTo().getBlockY() - spawnLocation.getBlockY() >= -0.1);
        }).filter(jumpAndRun -> {
            final Optional<JumpAndRunSession> session = JumpAndRuns.getOperator().getJumpSessionInfo(player);
            return !session.isPresent() || !session.get().getJumpAndRun().equals(jumpAndRun);
        }).findFirst().ifPresent(jumpAndRun -> JumpAndRuns.getOperator().startJumpAndRun(player, jumpAndRun));
    }

    @EventHandler
    public void onMoveDeath(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.playerNotMovedBlock(player, event.getTo())) return;

        JumpAndRuns.getOperator().getJumpSessionInfo(player).ifPresent(session -> {
            if (event.getTo().getBlockY() <= session.getJumpAndRun().getDeathAt()) session.reset();
        });
    }

    @EventHandler
    public void onMoveCheckpoint(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        if (this.playerNotMovedBlock(player, event.getTo())) return;

        JumpAndRuns.getOperator().getJumpSessionInfo(player).ifPresent(session -> {
            session.getJumpAndRun().getCheckpoints().stream().filter(checkpointLocation -> {
                final Location spawnLocation = checkpointLocation.toLocation();

                return session.getCheckpoint() != session.getJumpAndRun().getIndex(checkpointLocation)
                    && (spawnLocation.getBlockX() == event.getTo().getBlockX()
                    && spawnLocation.getBlockZ() == event.getTo().getBlockZ()
                    && (event.getTo().getBlockY() - spawnLocation.getBlockY() <= 1.25 && event.getTo().getBlockY() - spawnLocation.getBlockY() >= -0.1));
            }).findFirst().ifPresent(serializableLocation -> session.achieveCheckpoint(session.getJumpAndRun().getIndex(serializableLocation)));
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.lastLocation.remove(event.getPlayer());
        ((BukkitJumpAndRunOperator) JumpAndRuns.getOperator()).getSessions().remove(event.getPlayer().getUniqueId());
    }

    private boolean playerNotMovedBlock(Player player, Location to) {
        if (!this.lastLocation.containsKey(player)) return false;

        final Location lastLocation = this.lastLocation.get(player);

        return lastLocation.getBlockX() == to.getBlockX() && lastLocation.getBlockY() == to.getBlockY() && lastLocation.getBlockZ() == to.getBlockZ();
    }

}
