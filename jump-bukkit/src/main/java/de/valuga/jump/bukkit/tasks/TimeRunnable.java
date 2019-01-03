package de.valuga.jump.bukkit.tasks;
import de.valuga.jump.DefaultJumpAndRunOperator;
import de.valuga.jump.JumpAndRuns;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * @author Nico_ND1
 */
public class TimeRunnable implements Runnable {
    @Override
    public void run() {
        final DefaultJumpAndRunOperator operator = (DefaultJumpAndRunOperator) JumpAndRuns.getOperator();
        operator.getSessions().values().forEach(session -> {
            final Player player = session.getPlayer();
            final PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a(
                String.format("{\"text\":\"%,8d\"}", System.currentTimeMillis() - session.getStartTime())
            ));
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        });
    }
}
