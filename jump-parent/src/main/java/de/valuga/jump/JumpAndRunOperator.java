package de.valuga.jump;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

/**
 * @author Nico_ND1
 */
public interface JumpAndRunOperator {

    /**
     * Creates a new {@link JumpAndRun} and saves it to a json {@link File}.
     *
     * @param jumpAndRun The {@link JumpAndRun} to save
     * @since 0.0.1
     * @deprecated Moved to setup
     */
    void createJumpAndRun(JumpAndRun jumpAndRun);

    /**
     * Gets all {@link JumpAndRun jump and runs}.
     *
     * @return A {@link List<JumpAndRun> list} with all {@link JumpAndRun}
     * @since 0.0.1
     */
    List<JumpAndRun> getJumpAndRuns();

    /**
     * Loads all {@link JumpAndRun jump and runs}
     *
     * @since 0.0.10
     */
    void loadJumpAndRuns();

    /**
     * Starts a {@link JumpAndRun} for the {@code player}.
     *
     * @param player     The {@link Player} to start the {@link JumpAndRun} for
     * @param jumpAndRun The {@link JumpAndRun} to start
     * @return A new {@link JumpAndRunSession} with the {@link Player}s jump information
     * @since 0.0.3
     */
    JumpAndRunSession startJumpAndRun(Player player, JumpAndRun jumpAndRun);

    /**
     * Gets a {@link JumpAndRunSession} for the {@code player}.
     *
     * @param player The {@link Player} to search for
     * @return A {@link JumpAndRunSession} from the {@code player}
     * @since 0.0.3
     */
    JumpAndRunSession getJumpSessionInfo(Player player);

    /**
     * Called when a {@link Player} finishes a {@link JumpAndRun}.
     *
     * @param jumpAndRunSession The {@link JumpAndRun} from the {@link Player}
     * @since 0.0.3
     */
    void finishJumpAndRun(JumpAndRunSession jumpAndRunSession);

}
