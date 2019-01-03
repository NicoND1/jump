package de.valuga.jump.setup;
import de.valuga.jump.JumpAndRun;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author Nico_ND1
 */
public interface JumpAndRunSetup {

    /**
     * Starts the setup for the {@code player}.
     *
     * @param player  The {@link Player} which starts the setup
     * @param name    The {@link SetupPlayer#name} (or {@link JumpAndRun#name JumpAndRun.name}) from the {@link JumpAndRun}
     * @param deathAt The {@link SetupPlayer#deathAt} (or @link {@link JumpAndRun#deathAt}) from the {@link JumpAndRun}
     * @return A new instance of {@link SetupPlayer}
     * @since 0.0.1
     */
    SetupPlayer startSetup(Player player, String name, int deathAt);

    /**
     * Stops the setup for the {@code setupPlayer}.
     *
     * @param setupPlayer The {@link SetupPlayer} to stop the setup for
     * @since 0.0.1
     */
    void stopSetup(SetupPlayer setupPlayer);

    /**
     * Gets the {@link SetupPlayer} for the {@code player}
     *
     * @param player The {@link Player}
     * @return A {@link Optional<SetupPlayer>} which may contains the {@link SetupPlayer}
     * @since 0.0.1
     */
    Optional<SetupPlayer> getSetupPlayer(Player player);

    /**
     * Saves a {@link JumpAndRun} from the {@code setupPlayer}.
     *
     * @param setupPlayer The {@link SetupPlayer}
     * @since 0.0.1
     */
    void saveJumpAndRun(SetupPlayer setupPlayer);

    /**
     * Deletes a saved {@link JumpAndRun}.
     *
     * @param name The {@link JumpAndRun#name}
     * @since 0.0.1
     */
    void deleteJumpAndRun(String name);

}
