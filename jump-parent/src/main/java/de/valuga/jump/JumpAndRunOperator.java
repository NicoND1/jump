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
     */
    void createJumpAndRun(JumpAndRun jumpAndRun);

    /**
     * Gets all {@link JumpAndRun jump and runs}.
     *
     * @return A {@link List<JumpAndRun> list} with all {@link JumpAndRun}
     * @since 0.0.1
     */
    List<JumpAndRun> getJumpAndRuns();

    JumpAndRunSession startJumpAndRun(Player player, JumpAndRun jumpAndRun);

    JumpAndRunSession getJumpSessionInfo(Player player);

    void finishJumpAndRun(JumpAndRunSession jumpAndRunSession);

}
