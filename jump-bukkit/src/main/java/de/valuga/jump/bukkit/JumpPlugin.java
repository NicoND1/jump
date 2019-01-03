package de.valuga.jump.bukkit;
import de.valuga.jump.DefaultJumpAndRunOperator;
import de.valuga.jump.JumpAndRuns;
import de.valuga.jump.bukkit.listener.JumpAndRunListener;
import de.valuga.jump.bukkit.tasks.TimeRunnable;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Nico_ND1
 */
public class JumpPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        JumpAndRuns.setOperator(new DefaultJumpAndRunOperator());
        JumpAndRuns.getOperator().loadJumpAndRuns();
        Bukkit.getPluginManager().registerEvents(new JumpAndRunListener(), this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new TimeRunnable(), 4, 4);
    }
}
