package de.valuga.jump.setup.bukkit.commands;
import de.valuga.jump.SerializableLocation;
import de.valuga.jump.setup.SetupPlayer;
import de.valuga.jump.setup.bukkit.setup.DefaultJumpAndRunSetup;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

/**
 * @author Nico_ND1
 */
public class JumpSetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        final Player player = (Player) sender;

        if (args.length == 0) {
            this.sendHelpMessage(player, label);
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "start": {
                if (args.length != 3) {
                    this.sendHelpMessage(player, label);
                    return false;
                }

                final String name = args[1];
                final int deathAt;
                try {
                    deathAt = Integer.valueOf(args[2]);
                } catch (NumberFormatException ignored) {
                    player.sendMessage("§cSchreibe eine Zahl.");
                    return false;
                }

                DefaultJumpAndRunSetup.getInstance().startSetup(player, name, deathAt);
                player.sendMessage(String.format("§aBenutze §9/%s addcheckpoint §aum einen Checkpoint hinzuzufügen.\n" +
                        "§aMit §9/%s stop §akannst du das Setup beenden.\n" +
                        "§aMit §9/%s save §akannst du das JumpAndRun speichern und bist nicht mehr im Setup.",
                    label, label, label));
            }
            break;
            case "addcheckpoint": {
                final Optional<SetupPlayer> optionalSetupPlayer = DefaultJumpAndRunSetup.getInstance().getSetupPlayer(player);

                optionalSetupPlayer.ifPresent(setupPlayer -> {
                    final Block block = player.getTargetBlock((Set<Material>) null, 20);
                    final SerializableLocation checkpointLocation = new SerializableLocation(block.getLocation());
                    setupPlayer.getCheckpoints().add(checkpointLocation);

                    final ComponentBuilder componentBuilder = new ComponentBuilder("Du hast einen Checkpoint hinzugefügt.")
                        .color(ChatColor.GREEN)
                        .append(" ")
                        .append("[Teleportieren]")
                        .color(ChatColor.BLUE)
                        .event(new ClickEvent(Action.RUN_COMMAND, "/jumpsetup teleportcheckpoint " + setupPlayer.getCheckpoints().indexOf(checkpointLocation)))
                        .append(" ")
                        .append("[Löschen]")
                        .color(ChatColor.BLUE)
                        .event(new ClickEvent(Action.RUN_COMMAND, "/jumpsetup removecheckpoint " + setupPlayer.getCheckpoints().indexOf(checkpointLocation)));

                    player.spigot().sendMessage(componentBuilder.create());
                });
                if (!optionalSetupPlayer.isPresent())
                    player.sendMessage("§cDu bist nicht im Setup.");
            }
            break;
            case "removecheckpoint": {
                if (args.length != 2) {
                    this.sendHelpMessage(player, label);
                    return false;
                }
                final int index;
                try {
                    index = Integer.valueOf(args[1]);
                } catch (NumberFormatException ignored) {
                    player.sendMessage("§cSchreibe eine Zahl.");
                    return false;
                }
                final Optional<SetupPlayer> optionalSetupPlayer = DefaultJumpAndRunSetup.getInstance().getSetupPlayer(player);

                optionalSetupPlayer.ifPresent(setupPlayer -> {
                    if (setupPlayer.getCheckpoints().size() - 1 > index) {
                        player.sendMessage("§cDiesen Checkpoint kann es nicht geben.");
                        return;
                    }
                    setupPlayer.getCheckpoints().remove(index);

                    player.sendMessage("§7Du hast den Checkpoint gelöscht.");
                });
                if (!optionalSetupPlayer.isPresent())
                    player.sendMessage("§cDu bist nicht im Setup.");
            }
            break;
            case "teleportcheckpoint": {
                if (args.length != 2) {
                    this.sendHelpMessage(player, label);
                    return false;
                }
                final int index;
                try {
                    index = Integer.valueOf(args[1]);
                } catch (NumberFormatException ignored) {
                    player.sendMessage("§cSchreibe eine Zahl.");
                    return false;
                }
                final Optional<SetupPlayer> optionalSetupPlayer = DefaultJumpAndRunSetup.getInstance().getSetupPlayer(player);

                optionalSetupPlayer.ifPresent(setupPlayer -> {
                    if (setupPlayer.getCheckpoints().size() - 1 > index) {
                        player.sendMessage("§cDiesen Checkpoint kann es nicht geben.");
                        return;
                    }
                    player.teleport(setupPlayer.getCheckpoints().get(index).toLocation());
                });
                if (!optionalSetupPlayer.isPresent())
                    player.sendMessage("§cDu bist nicht im Setup.");
            }
            break;
            case "stop": {
                final Optional<SetupPlayer> optionalSetupPlayer = DefaultJumpAndRunSetup.getInstance().getSetupPlayer(player);
                optionalSetupPlayer.ifPresent(setupPlayer -> DefaultJumpAndRunSetup.getInstance().stopSetup(setupPlayer));
                if (!optionalSetupPlayer.isPresent())
                    player.sendMessage("§cDu bist nicht im Setup.");
            }
            break;
            case "save": {
                final Optional<SetupPlayer> optionalSetupPlayer = DefaultJumpAndRunSetup.getInstance().getSetupPlayer(player);
                optionalSetupPlayer.ifPresent(setupPlayer -> DefaultJumpAndRunSetup.getInstance().saveJumpAndRun(setupPlayer));
                if (!optionalSetupPlayer.isPresent())
                    player.sendMessage("§cDu bist nicht im Setup.");
            }
            break;
            default:
                this.sendHelpMessage(player, label);
                break;
        }

        return false;
    }

    private void sendHelpMessage(Player player, String label) {
        player.sendMessage(new String[]{
            String.format("/%s start <Name> <DeathAt>", label),
            String.format("/%s addcheckpoint", label),
            String.format("/%s removecheckpoint <Index>", label),
            String.format("/%s teleportcheckpoint <Index>", label),
            String.format("/%s stop", label),
            String.format("/%s save", label)
        });
    }
}
