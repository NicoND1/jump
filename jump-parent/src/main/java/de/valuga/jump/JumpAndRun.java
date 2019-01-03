package de.valuga.jump;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import de.valuga.jump.reward.JumpAndRunReward;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class JumpAndRun implements JsonDeserializer<JumpAndRun> {

    @Expose private final SerializableLocation spawnLocation;
    @Expose private final List<SerializableLocation> checkpoints;
    @Expose private final String name;
    @Expose private final JumpAndRunReward reward;
    @Expose private final int deathAt;

    @Override
    public JumpAndRun deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final SerializableLocation spawnLocation = context.deserialize(jsonObject.get("spawnLocation"), SerializableLocation.class);
        final List<SerializableLocation> checkpoints = StreamSupport.stream(jsonObject.getAsJsonArray("checkpoints").spliterator(), false)
            .map((Function<JsonElement, SerializableLocation>) jsonElement -> context.deserialize(jsonElement, SerializableLocation.class))
            .collect(Collectors.toList());
        final String name = jsonObject.get("name").getAsString();
        final JumpAndRunReward reward = null; // TODO: Implement
        final int deathAt = jsonObject.get("deathAt").getAsInt();

        return new JumpAndRun(spawnLocation, checkpoints, name, reward, deathAt);
    }
}
