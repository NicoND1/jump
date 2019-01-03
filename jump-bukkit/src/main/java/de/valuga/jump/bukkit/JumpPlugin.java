package de.valuga.jump.bukkit;
import de.valuga.jump.DefaultJumpAndRunOperator;
import de.valuga.jump.JumpAndRuns;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Nico_ND1
 */
public class JumpPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        JumpAndRuns.setOperator(new DefaultJumpAndRunOperator());
    }
}
