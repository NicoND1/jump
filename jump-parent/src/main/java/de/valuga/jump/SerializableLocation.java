package de.valuga.jump;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.Type;

/**
 * @author Nico_ND1
 */
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class SerializableLocation implements JsonDeserializer<SerializableLocation> {

    @Expose private final String world;
    @Expose private final double x;
    @Expose private final double y;
    @Expose private final double z;
    @Expose private final float yaw;
    @Expose private final float pitch;
    private Location location;

    public SerializableLocation(Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    /**
     * Gets a instance of {@link Location} with the parameters from this class.
     *
     * @return A instance of {@link Location}
     * @since 0.0.5
     */
    public Location toLocation() {
        if (this.location != null) return this.location;

        this.location = new Location(
            Bukkit.getWorld(this.world),
            this.x,
            this.y,
            this.z,
            this.yaw,
            this.pitch
        );

        return this.location;
    }

    @Override
    public SerializableLocation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();
        final String world = jsonObject.get("world").getAsString();
        final double x = jsonObject.get("x").getAsDouble();
        final double y = jsonObject.get("y").getAsDouble();
        final double z = jsonObject.get("z").getAsDouble();
        final float yaw = jsonObject.get("yaw").getAsFloat();
        final float pitch = jsonObject.get("pitch").getAsFloat();

        return new SerializableLocation(world, x, y, z, yaw, pitch);
    }
}
