package de.valuga.jump.bukkit.operator;
import de.valuga.jump.JsonJumpAndRunOperator;
import de.valuga.jump.JumpAndRun;
import de.valuga.jump.JumpAndRunSession;
import de.valuga.jump.hotbar.HotbarManager;
import de.valuga.jump.stats.HighscoreStatistic;
import org.bukkit.entity.Player;

import java.util.Optional;

/**
 * @author Nico_ND1
 * @since 0.0.1
 */
public class BukkitJumpAndRunOperator extends JsonJumpAndRunOperator {

    private final HighscoreStatistic highscoreStatistic = new HighscoreStatistic("highscores");

    @Override
    public JumpAndRunSession startJumpAndRun(Player player, JumpAndRun jumpAndRun) {
        final JumpAndRunSession jumpAndRunSession = new JumpAndRunSession(player, jumpAndRun);
        this.sessions.put(player.getUniqueId(), jumpAndRunSession);
        HotbarManager.getInstance().setInventory(player, "jump-and-run");

        return jumpAndRunSession;
    }

    @Override
    public Optional<JumpAndRunSession> getJumpSessionInfo(Player player) {
        return Optional.ofNullable(this.sessions.get(player.getUniqueId()));
    }

    @Override
    public void finishJumpAndRun(JumpAndRunSession jumpAndRunSession) {
        this.sessions.remove(jumpAndRunSession.getPlayer().getUniqueId());
        jumpAndRunSession.getPlayer().sendMessage("Finish");
        HotbarManager.getInstance().setInventory(jumpAndRunSession.getPlayer(), "empty");

        final long diff = System.currentTimeMillis() - jumpAndRunSession.getStartTime();
        this.highscoreStatistic.getPlayerStats(jumpAndRunSession.getPlayer().getUniqueId(), jumpAndRunSession.getJumpAndRun().getName()).thenAccept(playerStats -> {
            if (playerStats.getHighscore() > diff) {
                this.highscoreStatistic.trackPlayer(
                    jumpAndRunSession.getPlayer().getUniqueId(),
                    jumpAndRunSession.getJumpAndRun().getName(),
                    diff,
                    System.currentTimeMillis(),
                    () -> {
                    }
                );
            }
        });
    }
}
