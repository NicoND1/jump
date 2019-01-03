package de.valuga.jump.setup.bukkit.setup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.valuga.jump.JumpAndRun;
import de.valuga.jump.SerializableLocation;
import de.valuga.jump.setup.JumpAndRunSetup;
import de.valuga.jump.setup.SetupPlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Nico_ND1
 */
public class DefaultJumpAndRunSetup implements JumpAndRunSetup {

    private final List<SetupPlayer> setupPlayers = new ArrayList<>();
    private final File configDirectory = new File("jump-and-runs");
    private final Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .serializeNulls()
        .registerTypeAdapter(JumpAndRun.class, new JumpAndRun())
        .create();

    @Override
    public SetupPlayer startSetup(Player player, String name, int deathAt) {
        if (this.getSetupPlayer(player).isPresent()) throw new RuntimeException("Player is already in setup");

        final SetupPlayer setupPlayer = new SetupPlayer(player, name, deathAt, new SerializableLocation(player.getLocation()), new ArrayList<>());
        this.setupPlayers.add(setupPlayer);

        return setupPlayer;
    }

    @Override
    public void stopSetup(SetupPlayer setupPlayer) {
        setupPlayer.getPlayer().sendMessage("stopSetup");
    }

    @Override
    public Optional<SetupPlayer> getSetupPlayer(Player player) {
        return this.setupPlayers.stream()
            .filter(setupPlayer -> setupPlayer.getPlayer().equals(player))
            .findAny();
    }

    @Override
    public void saveJumpAndRun(SetupPlayer setupPlayer) {
        if (!this.configDirectory.exists()) this.configDirectory.mkdir();

        final JumpAndRun jumpAndRun = new JumpAndRun(
            setupPlayer.getSpawnLocation(),
            setupPlayer.getCheckpoints(),
            setupPlayer.getName(),
            null,
            setupPlayer.getDeathAt()
        );

        this.stopSetup(setupPlayer);

        try (FileWriter fileWriter = new FileWriter(new File(this.configDirectory, jumpAndRun.getName() + ".json"))) {
            fileWriter.write(this.gson.toJson(jumpAndRun, JumpAndRun.class));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteJumpAndRun(String name) {
        final File file = new File(this.configDirectory, name + ".json");

        if (file.exists()) file.delete();
    }
}
