package de.valuga.jump;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * @author Nico_ND1
 */
@RequiredArgsConstructor
@Data
public class JumpAndRunSession {

    private final Player player;
    private final long startTime = System.currentTimeMillis();
    private final JumpAndRun jumpAndRun;
    private int checkpoint = -1;

    public void reset() {
        if (this.checkpoint == -1)
            this.player.teleport(this.applyHeadRotation(this.jumpAndRun.getSpawnLocation().toLocation().clone()));
        else
            this.player.teleport(this.applyHeadRotation(this.jumpAndRun.getCheckpoint(this.checkpoint).toLocation().clone().add(0.5, 1, 0.5)));
    }

    private Location applyHeadRotation(Location location) {
        location.setYaw(this.player.getLocation().getYaw());
        location.setPitch(this.player.getLocation().getPitch());
        return location;
    }

    public void achieveCheckpoint(int checkpoint) {
        if (this.checkpoint >= checkpoint) return;

        if (checkpoint == this.jumpAndRun.getCheckpoints().size() - 1) {
            // Finish because the last checkpoint is the goal
            JumpAndRuns.getOperator().finishJumpAndRun(this);
            return;
        }

        this.checkpoint = checkpoint;
    }

}
