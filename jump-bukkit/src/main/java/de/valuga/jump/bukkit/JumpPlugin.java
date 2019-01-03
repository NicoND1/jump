package de.valuga.jump.bukkit;
import de.valuga.jump.JumpAndRunSession;
import de.valuga.jump.JumpAndRuns;
import de.valuga.jump.bukkit.hotbar.HotbarItem;
import de.valuga.jump.bukkit.hotbar.HotbarManager;
import de.valuga.jump.bukkit.listener.JumpAndRunListener;
import de.valuga.jump.bukkit.operator.BukkitJumpAndRunOperator;
import de.valuga.jump.bukkit.tasks.TimeRunnable;
import hotbar.AbstractHotbarInventory;
import hotbar.DefaultHotbarManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;

/**
 * @author Nico_ND1
 */
public class JumpPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        JumpAndRuns.setOperator(new BukkitJumpAndRunOperator());
        JumpAndRuns.getOperator().loadJumpAndRuns();
        Bukkit.getPluginManager().registerEvents(new JumpAndRunListener(), this);
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new TimeRunnable(), 2, 2);

        HotbarManager.setInstance(new DefaultHotbarManager(this));
        this.registerInventory();
    }

    private void registerInventory() {
        final AbstractHotbarInventory hotbarInventory = new AbstractHotbarInventory(new HashMap<Integer, HotbarItem>() {{
            this.put(4, new HotbarItem(new ItemStack(Material.DIAMOND)) {
                @Override
                public void onInteract(Player player) {
                    JumpAndRuns.getOperator().getJumpSessionInfo(player).ifPresent(JumpAndRunSession::reset);
                }
            });
        }});
        HotbarManager.getInstance().registerInventory("jump-and-run", hotbarInventory);
        HotbarManager.getInstance().registerInventory("empty", new AbstractHotbarInventory(Collections.emptyMap()));
    }
}
