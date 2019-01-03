package de.valuga.jump.setup;
import de.valuga.jump.SerializableLocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class SetupPlayer {

    private final Player player;
    private final String name;
    private final int deathAt;
    private final SerializableLocation spawnLocation;
    private final List<SerializableLocation> checkpoints;

}
