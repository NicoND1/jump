package de.valuga.jump.setup.bukkit;
import de.valuga.jump.setup.bukkit.commands.JumpSetupCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Nico_ND1
 */
public class JumpSetupPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("jumpsetup").setExecutor(new JumpSetupCommand());
    }
}
