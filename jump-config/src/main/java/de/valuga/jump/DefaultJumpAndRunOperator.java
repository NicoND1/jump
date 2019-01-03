package de.valuga.jump;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author Nico_ND1
 * @since 0.0.2
 */
public class DefaultJumpAndRunOperator implements JumpAndRunOperator {

    private final Gson gson = new GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .setPrettyPrinting()
        .serializeNulls()
        .registerTypeAdapter(JumpAndRun.class, new JumpAndRun())
        .create();
    private final File configDirectory = new File("jump-and-runs");
    @Getter private final Map<UUID, JumpAndRunSession> sessions = new HashMap<>();
    private final List<JumpAndRun> cachedJumpAndRuns = new ArrayList<>();

    @Deprecated
    @Override
    public void createJumpAndRun(JumpAndRun jumpAndRun) {
        if (!this.configDirectory.exists()) this.configDirectory.mkdir();

        try (FileWriter fileWriter = new FileWriter(new File(this.configDirectory, jumpAndRun.getName() + ".json"))) {
            fileWriter.write(this.gson.toJson(jumpAndRun, JumpAndRun.class));
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JumpAndRun> getJumpAndRuns() {
        if (!this.cachedJumpAndRuns.isEmpty()) return this.cachedJumpAndRuns;

        final List<JumpAndRun> jumpAndRuns = new ArrayList<>();

        for (File file : Objects.requireNonNull(this.configDirectory.listFiles())) {
            try {
                jumpAndRuns.add(this.gson.fromJson(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")), JumpAndRun.class));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return jumpAndRuns;
    }

    @Override
    public void loadJumpAndRuns() {
        this.cachedJumpAndRuns.addAll(this.getJumpAndRuns());
    }

    @Override
    public JumpAndRunSession startJumpAndRun(Player player, JumpAndRun jumpAndRun) {
        final JumpAndRunSession jumpAndRunSession = new JumpAndRunSession(player, jumpAndRun);
        this.sessions.put(player.getUniqueId(), jumpAndRunSession);

        player.sendMessage(jumpAndRunSession.getJumpAndRun().getName());
        player.teleport(jumpAndRun.getSpawnLocation().toLocation());
        return jumpAndRunSession;
    }

    @Override
    public JumpAndRunSession getJumpSessionInfo(Player player) {
        return this.sessions.get(player.getUniqueId());
    }

    @Override
    public void finishJumpAndRun(JumpAndRunSession jumpAndRunSession) {
        jumpAndRunSession.getPlayer().sendMessage("Finish");
    }
}
