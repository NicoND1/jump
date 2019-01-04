package de.valuga.jump.hotbar;
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

    /**
     * Called the the {@code player} interacts with the {@code itemStack}.
     *
     * @param player The {@link Player} that interacts with the {@code itemStack}
     * @since 0.0.1
     */
    public abstract void onInteract(Player player);

}
