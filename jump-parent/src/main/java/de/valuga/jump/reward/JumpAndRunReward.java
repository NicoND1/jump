package de.valuga.jump.reward;
import de.valuga.jump.JumpAndRun;
import org.bukkit.entity.Player;

/**
 * @author Nico_ND1
 */
public interface JumpAndRunReward {

    /**
     * Called when a {@link Player} finishes a {@link JumpAndRun}.
     *
     * @param player     The {@link Player} which finished the {@link JumpAndRun}
     * @param jumpAndRun The {@link JumpAndRun} which was finished by the {@link Player}
     * @param extras     An array with all informations a.e. how much coins
     * @since 0.0.1
     */
    void apply(Player player, JumpAndRun jumpAndRun, Object... extras);

}
