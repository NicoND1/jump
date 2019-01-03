package hotbar;
import de.valuga.jump.bukkit.hotbar.HotbarInventory;
import de.valuga.jump.bukkit.hotbar.HotbarItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@Data
public class AbstractHotbarInventory implements HotbarInventory {

    private final Map<Integer, HotbarItem> items;

}
