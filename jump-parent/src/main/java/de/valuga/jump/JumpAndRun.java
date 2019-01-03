package de.valuga.jump;

import de.valuga.jump.reward.JumpAndRunReward;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@Data
public abstract class JumpAndRun {

    private final SerializableLocation spawnLocation;
    private final SerializableLocation[] checkpoints;
    private final String name;
    private final JumpAndRunReward reward;

}
