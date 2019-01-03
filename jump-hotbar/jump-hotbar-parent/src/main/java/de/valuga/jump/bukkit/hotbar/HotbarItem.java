package de.valuga.jump.bukkit.hotbar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public abstract class HotbarItem {

    private final ItemStack itemStack;
    private boolean allowLeftClick = false;

    public abstract void onInteract(Player player);

}
