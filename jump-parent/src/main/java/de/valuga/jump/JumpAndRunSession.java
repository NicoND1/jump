package de.valuga.jump;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
    private int checkpoint = 0;

    public void reset() {
        if (this.checkpoint == 0)
            this.player.teleport(this.jumpAndRun.getSpawnLocation().toLocation());
        else
            this.player.teleport(this.jumpAndRun.getCheckpoint(this.checkpoint).toLocation());
    }

}
