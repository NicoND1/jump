package de.valuga.jump.setup;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author Nico_ND1
 */
public interface JumpAndRunSetup {

    SetupPlayer startSetup(Player player, String name, int deathAt);

    void stopSetup(SetupPlayer setupPlayer);

    Optional<SetupPlayer> getSetupPlayer(Player player);

    void saveJumpAndRun(SetupPlayer setupPlayer);

    void deleteJumpAndRun(String name);

}
